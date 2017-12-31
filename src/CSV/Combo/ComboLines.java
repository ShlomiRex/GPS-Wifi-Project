package CSV.Combo;

import CSV.Data.AP_WifiData;
import CSV.Wigle.Data.WigleCSVData;
import CSV.Wigle.Data.WigleWifiData;
import CSV.Wigle.WigleCSV;
import au.com.bytecode.opencsv.CSVWriter;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComboLines extends ArrayList<ComboLine> {

    private ArrayList<WigleCSV> wigleCSVArrayList;

    /**
     * Init empty array list.
     */
    public ComboLines() {
        super();
    }

//    public ComboLines(List<WigleCSV> wigleCSVList) {
//        wigleCSVArrayList = (ArrayList<WigleCSV>) wigleCSVList;
//
//    }

    public ComboLines(WigleCSV wigleCSV) {
        wigleCSVArrayList = new ArrayList<>();
        wigleCSVArrayList.add(wigleCSV);
        addAll(getComboLines(wigleCSV));
    }

    /**
     * @since MATALA 0 QUESTION 2
     * @return Subarrays of same combo lines (same Time, ID, ect...)
     * 			Each subarray contains elements of Record which are in the CSV file.<br>
     * 			EACH CSV: MODEL, DEVICE<br>
     * 			EACH COMBO: TIME, ID, LAT, LON, ALT<br>
     *
     */
    private static ComboLines getComboLines(WigleCSV wigleCSV) {
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
            //add to first combo data untill not same line.
            while (i < csvData.size() - 1 && isSameLine(data1, data2)) {
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
     * Takes all combos with size exceeds 10 and filters all of the combo datas by RSSI.
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
}
