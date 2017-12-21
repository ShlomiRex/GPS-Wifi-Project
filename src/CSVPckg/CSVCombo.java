package CSVPckg;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @since MATALA 0 QUESTION 2, MATALA 2
 */
public class CSVCombo extends CSV {


    public ArrayList<ArrayList<Record>> combos;

    public CSVCombo(File wigleCSVFile) throws Throwable {
        super(wigleCSVFile);
        combos = getSubarraysComboRecords(this);
    }

    /**
     * @since MATALA 0 QUESTION 2
     * @return Subarrays of same combo lines (same Time, ID, ect...)
     * 			Each subarray contains elements of Record which are in the CSV file.<br>
     * 			EACH CSV: MODEL, DEVICE<br>
     * 			EACH COMBO: TIME, ID, LAT, LON, ALT<br>
     *
     */
    private static ArrayList<ArrayList<Record>> getSubarraysComboRecords(CSV wigleCSV) {
        //SORT BY:
        //TIME, ID, LAT, LON, ALT

        //TIME is already sorted
        //ID is the same for file
        //LAT , LON should be same for next line. If not, then don't add.

        Records records = wigleCSV.records;

        ArrayList<Record> sameLineRecords = new ArrayList<>();
        ArrayList<ArrayList<Record>> result = new ArrayList<>();

        Record r1, r2;
        int iterations = records.size()-1;
        for(int i = 0; i < iterations; i++) {

            r1 = records.get(i);
            r2 = records.get(i+1);

            sameLineRecords.add(r1);

            while(isSameLine(r1,r2) == true && i < iterations) {
                sameLineRecords.add(r2);
                i++;
                r2 = records.get(i+1);
            }
            result.add(sameLineRecords);
            sameLineRecords = new ArrayList<>();
        }

        //NOW CHECK LAST LINE
        Record lastLine = records.get(records.size()-1);
        Record beforeLastLine = records.get(records.size()-2);
        ArrayList<Record> lastRes = result.get(result.size()-1);
        if(isSameLine(lastLine, beforeLastLine) == true) {
            lastRes.add(lastLine);
        }
        else {
            ArrayList<Record> newArr = new ArrayList<>();
            newArr.add(lastLine);
            result.add(newArr);
        }



        return result;
    }

    public ArrayList<ArrayList<Record>> getSubarraysComboRecords() {
        return  getSubarraysComboRecords(this);
    }

    public void writeCombo(File fileToWriteTo, boolean isStrongestTenRSSI) throws Throwable {
        writeCombo(this, fileToWriteTo, headers, isStrongestTenRSSI);
    }


    public static void writeCombo(CSVCombo csvCombo, File fileToWriteTo, CSVHeaders headers ,boolean isStrongestTenRSSI) throws Throwable {
        System.out.println("Writing combo to: " + fileToWriteTo.getAbsolutePath());
        ArrayList<ArrayList<Record>> combos = csvCombo.combos;
        if(combos.size() == 0 || combos.get(0).size() == 0)
            throw new Throwable("Size is 0!");

        CSVWriter writer = new CSVWriter(new FileWriter(fileToWriteTo));
        ArrayList<String> lineToWrite;

        //Write fields line
        writer.writeNext(getCSVComboHeaders_Fields());

        String[] temp; //junk

        //For each 'Combo'
        for(ArrayList<Record> combo : combos) {
            lineToWrite = new ArrayList<>();
            if(combo.size() == 0)
                break;

            //inti the line so to: First Seen, Model, Device, Location, Alt, WITHOUT NUMBER OF RECORDS !!!!
            initComboLine(csvCombo, lineToWrite, combo);

            if(isStrongestTenRSSI) {
                combo = getComboByTenStrongestRSSI(combo);
                System.out.println("New size: " + combo.size());
            }

            //Number of records in this combo
            lineToWrite.add(""+combo.size());

            //For each record in 'Combo' array
            for (Record rec : combo) {
                //Returns "MAC, SSID, CHANNEL, RSSI" for rec.
                temp = getComboRecordDataByOrder(rec);

                //Space
                lineToWrite.add("");
                for (int i = 0; i < temp.length; i++) {

                    lineToWrite.add(temp[i]);
                }
            }
            System.out.println();
            String[] r = convertComboLineToCSVLine(lineToWrite);
            writer.writeNext(r);

        }//For each 'Combo' array
        writer.close();
        System.out.println("Writing done. Combo is generated.");
    }

    /**
     * Searches in CSV file, if find record by same MAC return its line and number of this record in the line.
     * EXAMPLE: <br>
     * [1] MAC1 .. RSSI1 , MAC2.. RSSI2 , MAC3 .. RSSI3
     * [2] MAC1 .. RSSI1 , MAC2.. RSSI2 , MAC3 .. RSSI3
     * [3] MAC1 .. RSSI1 , MAC2.. RSSI2 , MAC3 .. RSSI3
     * <br>Mac1 in line 1 and Mac3 in line 2 are the ones we searching for.
     * <br>So return:<br>
     * { {1,3},{2, 3} }
     * @param comboCSVFile
     * @param searchMac
     * @param k
     * @return
     * @throws IOException
     */
    public static ArrayList<int[]> getAllLinesWithSameMac(File comboCSVFile, String searchMac, int k) throws IOException {
        FileReader fr = new FileReader(comboCSVFile);
        CSVReader reader = new CSVReader(fr);

        ArrayList<String[]> allLines = (ArrayList<String[]>) reader.readAll();
        String[] line;
        //First colum at which first mac is there
        int firstMacColumn = 8;
        int currentColumn = firstMacColumn;
        String rssi, mac;

        ArrayList<int[]> result = new ArrayList<>();

        for(int i = 1; i < allLines.size() && k != 0; i++) {
            line = allLines.get(i);
            for(int column = firstMacColumn; column < line.length; column += 5) {
                rssi = line[column + 3];
                mac = line[column];
                if(mac.equals(searchMac)) {
                    System.out.println("["+i+1+"]   " + mac +"    RSSI: " + rssi);
                    k--;
                    addToResult:
                    {
                        int[] temp = {i+1, column+1};
                        result.add(temp);
                    }
                }
            }

        }

        reader.close();

        return result;
    }

    public ArrayList<Record> getAllRecordsBySameMac(String mac) {
        ArrayList<Record> result = new ArrayList<>();
        for(ArrayList<Record> combo : combos) {
            for(Record record : combo) {
                if(((String)record.get_Field(Record.Field.MAC)).equals(mac)) {
                    result.add(record);
                }
            }
        }
        return result;
    }

    public ArrayList<Record> getStrongestRecordsByMac(String searchMac, int k) throws IOException {
        ArrayList<Record> recordsWithSameMac = getAllRecordsBySameMac(searchMac);
        recordsWithSameMac.sort(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return Record.Field.compareFields(o2, o1, Record.Field.RSSI);
            }
        });
        ArrayList<Record> result = new ArrayList<>();

        for(int i = 0; i < recordsWithSameMac.size() && i < k; i++)
            result.add(recordsWithSameMac.get(i));

        return result;
    }

    public void algo1(String mac) {

    }

    private static ArrayList<Record> getComboByTenStrongestRSSI(ArrayList<Record> combo) {
        ArrayList<Record> newCombo = new ArrayList<>();

        Comparator<? super Record> comp = new Comparator<Record>() {
            @Override
            public int compare(Record rec1, Record rec2) {

                return Record.Field.compareFields(rec2, rec1, Record.Field.RSSI);
            }
        };
        combo.sort(comp);

        for(int i = 0; i < combo.size() && i < 10 ; i++) {
            newCombo.add(combo.get(i));
        }
        return newCombo;
    }

    /**
     * Adds to lineToWrite arguments which initializes the First Seen, Model, Device, Location, Alt, <b>WITHOUT NUMBER OF RECORDS !!!!</b>
     * @param lineToWrite
     * @param combo
     */
    private void initComboLine(ArrayList<String> lineToWrite, ArrayList<Record> combo) {
        initComboLine(this, lineToWrite, combo);
    }

    private static void initComboLine(CSVCombo csvCombo, ArrayList<String> lineToWrite, ArrayList<Record> combo) {

        String lat, lon, alt, dateStamp;

        //Timestamp
        dateStamp = combo.get(0).get_Field(Record.Field.FirstSeen).toString();
        lineToWrite.add(dateStamp);

        //ID
        String modelStamp = csvCombo.headers.wigle_getField(CSVHeaders.WigleField.model);
        String deviceStamp = csvCombo.headers.wigle_getField(CSVHeaders.WigleField.device);


        //Model
        lineToWrite.add(modelStamp);

        //Device
        lineToWrite.add(deviceStamp);

        //Location
        lat = combo.get(0).get_Field(Record.Field.Lat).toString();
        lineToWrite.add(lat);
        lon = combo.get(0).get_Field(Record.Field.Lon).toString();
        lineToWrite.add(lon);

        //Altitude
        alt = combo.get(0).get_Field(Record.Field.Alt).toString();
        lineToWrite.add(alt);
    }

    public static String[] getCSVComboHeaders_Fields() {
        ArrayList<String> line = new ArrayList<>();
        //WRITE FIELDS HEADER
        line.add("First Seen");
        line.add("Model");
        line.add("Device");
        line.add("Latitude");
        line.add("Longtitude");
        line.add("Altitude");
        line.add("Num Of Records");
        for(int i = 0; i < 10; i++) {
            line.add("");
            line.add("MAC");
            line.add("SSID");
            line.add("Channel");
            line.add("RSSI");

        }

        String[] finalResult = new String[line.size()];
        finalResult = line.toArray(finalResult);

        return finalResult;
    }

    /**
     *
     * @param record
     * @return MAC, SSID, CHANNEL, RSSI by this order
     */
    private static String[] getComboRecordDataByOrder(Record record) {
        ArrayList<String> comboLine = new ArrayList<>();
        String ssid, mac, rssi, channel;

        mac = record.get_Field(Record.Field.MAC).toString();
        ssid = record.get_Field(Record.Field.SSID).toString();
        channel = record.get_Field(Record.Field.Channel).toString();
        rssi = record.get_Field(Record.Field.RSSI).toString();

        comboLine.add(mac);
        comboLine.add(ssid);
        comboLine.add(channel);
        comboLine.add(rssi);

        String[] finalResult = new String[comboLine.size()];
        finalResult = comboLine.toArray(finalResult);

        return finalResult;
    }

    /**
     *
     * @param arr Array of data (MAC, SSID...) Anythinf with 'commas'.
     * @return Array, each element is 'comma' seperated.
     */
    public static String[] convertComboLineToCSVLine(ArrayList<String> arr) {
        String[] result = new String[arr.size()];
        for(int i = 0; i < arr.size()-1; i++) {
            result[i] = arr.get(i);
        }
        //add last element
        String last = arr.get(arr.size()-1);
        result[arr.size()-1] = last;
        return result;
    }

    /**
     * @since Matala 0 Question 2
     * @param r1
     * @param r2
     * @return True if r2 and r1 should be in the same line as described
     * in Matala 0 Question 2 (Same TIME, ID, Lat, Lon, Alt)
     */
    public static boolean isSameLine(Record r1, Record r2) {
        //TIME is already sorted
        //ID is the same for file
        //LAT , LON should be same for next line. If not, then don't add.
        //System.out.println("Comparing:");
        //r1.print();
        //r2.print();

        if(Record.Field.compareFields(r1, r2, Record.Field.FirstSeen) != 0) {
            //System.out.println("First seen incorrect.");
            return false;
        }
        if(Record.Field.compareFields(r1, r2, Record.Field.Lat) != 0) {

            //System.out.println("Lat incorrect.");
            return false;
        }
        if(Record.Field.compareFields(r1, r2, Record.Field.Lon) != 0) {
            //System.err.println("Lon incorrect.");
            return false;
        }
        if(Record.Field.compareFields(r1, r2, Record.Field.Alt) != 0) {
            //System.out.println("Alt incorrect.");
            return false;
        }
        //System.out.println("SAME LINE");
        return true;
    }


}
