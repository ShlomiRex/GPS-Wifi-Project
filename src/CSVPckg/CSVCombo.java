package CSVPckg;

import CSVPckg.CSV;
import CSVPckg.CSVHeaders;
import CSVPckg.Record;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CSVCombo extends CSV {


    public CSVCombo(File fileToConvert) throws Throwable {
        super(fileToConvert);
    }

    public CSVCombo(CSV csv) {
        super(csv);
    }


    /**
     * @since MATALA 0 QUESTION 2
     * @return Subarrays of same combo lines (same Time, ID, ect...)
     * 			Each subarray contains elements of Record which are in the CSV file.<br>
     * 			EACH CSV: MODEL, DEVICE<br>
     * 			EACH COMBO: TIME, ID, LAT, LON, ALT<br>
     *
     */
    public ArrayList<ArrayList<Record>> getSubarraysComboRecords() {
        //SORT BY:
        //TIME, ID, LAT, LON, ALT

        //TIME is already sorted
        //ID is the same for file
        //LAT , LON should be same for next line. If not, then don't add.

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

    public void writeCombo(File fileToWriteTo, boolean isStrongestTenRSSI) throws Throwable {
        System.out.println("Writing combo to: " + fileToWriteTo.getAbsolutePath());
        ArrayList<ArrayList<Record>> combos = getSubarraysComboRecords();
        if(combos.size() == 0 || combos.get(0).size() == 0)
            throw new Throwable("Size is 0!");

        CSVWriter writer = new CSVWriter(new FileWriter(fileToWriteTo));
        ArrayList<String> lineToWrite;

        //Write fields line
        writer.writeNext(getComboFields());

        String[] temp; //junk

        //For each 'Combo' array
        for(ArrayList<Record> combo : combos) {
            lineToWrite = new ArrayList<>();
            if(combo.size() == 0)
                break;

            initComboLine(lineToWrite, combo);

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
            writer.writeNext(getCSVStringFromArrayList(lineToWrite));
            lineToWrite = new ArrayList<>();

        }//For each 'Combo' array
        writer.close();
        System.out.println("Writing done. Combo is generated.");
    }

    private ArrayList<Record> getComboByTenStrongestRSSI(ArrayList<Record> combo) {
        ArrayList<Record> newCombo = new ArrayList<>();

        Comparator<? super Record> comp = new Comparator<Record>() {
            @Override
            public int compare(Record rec1, Record rec2) {

                return Record.Field.compareFields(rec1, rec2, Record.Field.RSSI);
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
        String lat, lon, alt, dateStamp;

        //Timestamp
        dateStamp = combo.get(0).get_Field(Record.Field.FirstSeen).toString();
        lineToWrite.add(dateStamp);

        //ID
        String modelStamp = headers.wigle_getField(CSVHeaders.WigleField.model);
        String deviceStamp = headers.wigle_getField(CSVHeaders.WigleField.device);


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

    public String[] getComboFields() {
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
    private String[] getComboRecordDataByOrder(Record record) {
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
    public String[] getCSVStringFromArrayList(ArrayList<String> arr) {
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
