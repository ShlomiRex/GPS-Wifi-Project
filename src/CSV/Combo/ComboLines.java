package CSV.Combo;

import CSV.Data.AP_WifiData;
import CSV.Wigle.Data.WigleCSVData;
import CSV.Wigle.Data.WigleWifiData;
import CSV.Wigle.WigleCSV;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.util.*;

public class ComboLines extends ArrayList<ComboLine> {

    /**
     * Init empty array list.
     */
    public ComboLines() {
        super();
    }

    public ComboLines(WigleCSV wigleCSV) {
        addAll(getComboLines(wigleCSV));
    }

    /**
     * Takes combo file and converts it's lines to objects.
     * @param lines
     */
    public ComboLines(List<String[]> lines) throws IOException {
        //wigleCSVArrayList = new ArrayList<>();
        super();
        addAll(getComboLines(lines));
    }

    /**
     * Filters all lines and takes only valid combo lines.
     * @since MATALA 0 QUESTION 2
     * @return Subarrays of same combo lines (same Time, ID, ect...)
     * 			Each subarray contains elements of Record which are in the CSV file.<br>
     * 			EACH CSV: MODEL, DEVICE<br>
     * 			EACH COMBO: TIME, ID, LAT, LON, ALT<br>
     *
     */
    private ComboLines getComboLines(List<String[]> lines) throws IOException {
        ComboLines comboLines = new ComboLines();
        ComboLine tmpLine;
        int lineNumber = 1;
        int numOfErrors = 0;
        for(String[] line : lines) {
            if(numOfErrors == 3)
                throw new IOException("Detected 3 Errors: This is not a valid combo file.");
            try {
                tmpLine = new ComboLine(line);
                comboLines.add(tmpLine);
            } catch (Exception e) {
                //e.initCause(new Throwable("Line [" + lineNumber +  "] : " + Arrays.toString(line)));
                //e.printStackTrace();
                numOfErrors ++;
            }
            lineNumber++;
        }

        return comboLines;
    }

    /**
     * @since MATALA 0 QUESTION 2
     * @return Subarrays of same combo lines (same Time, ID, ect...)
     * 			Each subarray contains elements of Record which are in the CSV file.<br>
     * 			EACH CSV: MODEL, DEVICE<br>
     * 			EACH COMBO: TIME, ID, LAT, LON, ALT<br>
     *
     */
    private static ComboLines getComboLines(WigleCSV wigleCSV)  {
        //SORT BY:
        //TIME, ID, LAT, LON, ALT

        //TIME is already sorted
        //ID is the same for file
        //LAT , LON should be same for next line. If not, then don't add.
        if (wigleCSV.csvData.size() <= 4) {
            System.out.print("ERROR: CSV Data must be at least 4 lines!");
            return null;
        }
        ComboLines comboLines = new ComboLines();
        ComboLine comboLine;
        ArrayList<WigleWifiData> temp;
        WigleCSVData csvData = wigleCSV.csvData;
        WigleWifiData data1, data2;
        String tempModel, tempDevice;
        tempModel = wigleCSV.headers.wigleHeader.getModel();
        tempDevice = wigleCSV.headers.wigleHeader.getDevice();

        for (int i = 0; i < csvData.size() - 1; i++) {
            data1 = csvData.get(i);
            data2 = csvData.get(i + 1);

            //temp combo first wifi data
            temp = new ArrayList<>();
            temp.add(data1);
            //add to first combo data untill not same line. (or untill reached end.
            while (i < csvData.size() - 2 && isSameLine(data1, data2)) {
                temp.add(data2);
                i++;
                data2 = csvData.get(i + 1);
            }
            comboLine = new ComboLine(temp, tempModel, tempDevice);
            comboLines.add(comboLine);
        }

        //Last Line
        lastLine:
        {
            WigleWifiData lastData = csvData.get(csvData.size() - 1);
            WigleWifiData beforeLastData = csvData.get(csvData.size() - 2);
            ComboLine lastComboLine = comboLines.get(comboLines.size() - 1);

            if (isSameLine(beforeLastData, lastData)) {
                lastComboLine.add(lastData);
            } else {
                ArrayList<WigleWifiData> theLastComboLineOnlyData = new ArrayList<>();
                theLastComboLineOnlyData.add(lastData);
                comboLines.add(new ComboLine(theLastComboLineOnlyData, tempModel, tempDevice));
            }
        }

        return comboLines;
    }


    /**
     * @since Matala 0 Question 2
     * @return True if data1 and data2 should be in the same line as described
     * in Matala 0 Question 2 (Same TIME, ID, Lat, Lon, Alt)
     */
    private static boolean isSameLine(WigleWifiData data1, WigleWifiData data2) {
        //TIME is already sorted
        //ID is the same for file
        //LAT , LON should be same for next line. If not, then don't add.
        //System.out.println("Comparing:");
        //r1.print();
        //r2.print();

        if(data1.firstSeen.equals(data2.firstSeen) == false) {
            return false;
        }

        if(data1.location.equals(data2.location) == false) {
            return false;
        }
        return true;
    }

    public void write(ComboCSV comboCSV) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter((File)comboCSV));
        for(ComboLine line : this) {
            line.appendToWriter(writer);
        }
        writer.close();
    }

    /**
     * Takes all combos with size exceeds 10 and filters all of the combo data by RSSI.
     */
    public void filterStrongestRSSI() {
        for(ComboLine l : this) {
            if(l.size() > 10) {
                l.sort(new Comparator<AP_WifiData>() {
                    @Override
                    public int compare(AP_WifiData ap_wifiData1, AP_WifiData ap_wifiData2) {
                        return Double.compare(ap_wifiData1.wifiSpectrum.rssi, ap_wifiData2.wifiSpectrum.rssi);
                    }
                });
                int size = l.size();
                for(int i = size-1; i >= 10; i--) {
                    l.remove(i);
                }
            }
        }
    }

    /**
     * Filters lines and returns lines containing ap_wifidata which has any of the input mac address
     * O(n*m) - n lines, m is max combo size
     * @param macs
     * @return
     */
    public ComboLines filterOR_by_macs(String[] macs) {
        ComboLines comboLines = new ComboLines();
        boolean founded = false;
        for(ComboLine comboLine : comboLines) {
            for(AP_WifiData ap : comboLine) {
                ap.print();
                for(String mac : macs) {
                    if(ap.mac.equals(mac)) {
                        comboLines.add(comboLine);
                        founded = true;
                        break;
                    }
                }
                if(founded)
                    break;
            }
        }
        return comboLines;
    }

    public void print() {
        int lineindex = 1;
        for (ComboLine comboLine : this) {
            try {
                comboLine.print();
                lineindex++;
            }         catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

    }

    public void filterBy_FirstSeen(Date firstSeenFrom, Date firstSeenTo) {

    }

}
