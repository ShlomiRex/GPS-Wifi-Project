package Examples.CSV;

import CSVPckg.CSV;
import CSVPckg.CSVHeaders;
import CSVPckg.Record;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

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

            while(isSameLine(r1,r2) == true) {
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

    public void writeCombo(File fileToWriteTo) throws Throwable {
        System.out.println("Writing combo to: " + fileToWriteTo.getAbsolutePath());
        ArrayList<ArrayList<Record>> combos = getSubarraysComboRecords();
        if(combos.size() == 0 || combos.get(0).size() == 0)
            throw new Throwable("Size is 0!");


        CSVWriter writer = new CSVWriter(new FileWriter(fileToWriteTo));
        ArrayList<String> lineToWrite = new ArrayList<>();

        //ID
        String modelStamp = headers.wigle_getField(CSVHeaders.WigleField.model);
        String deviceStamp = headers.wigle_getField(CSVHeaders.WigleField.device);

        //Time
        String dateStamp;
        //Location
        String lat, lon;
        String alt;

        //For each element in combo
        String ssid, mac, rssi, channel;
        for(ArrayList<Record> combo : combos) {
            for(Record rec : combo) {
                //Timestamp
                dateStamp = rec.get_Field(Record.Field.FirstSeen).toString();
                lineToWrite.add(dateStamp);
                //Model
                lineToWrite.add(modelStamp);
                //Device
                lineToWrite.add(deviceStamp);


                //Location
                lat = rec.get_Field(Record.Field.Lat).toString();
                lineToWrite.add(lat);
                lon = rec.get_Field(Record.Field.Lon).toString();
                lineToWrite.add(lon);

                //Altitude
                alt = rec.get_Field(Record.Field.Alt).toString();

                //Space
                lineToWrite.add("");

                //Time of combo
                lineToWrite.add(""+combo.size());

                //MAC
                mac = rec.get_Field(Record.Field.MAC).toString();
                lineToWrite.add(mac);

                //SSID
                ssid = rec.get_Field(Record.Field.SSID).toString();
                lineToWrite.add(ssid);

                //Channel (Frequency)
                channel = rec.get_Field(Record.Field.Channel).toString();
                lineToWrite.add(channel);

                //RSSI
                rssi = rec.get_Field(Record.Field.RSSI).toString();
                lineToWrite.add(rssi);

                //Space
                lineToWrite.add("");
            }
            String[] arr = lineToWrite.toArray(new String[lineToWrite.size()]);
            writer.writeNext(arr);
        }
        System.out.println("Writing done. Combo is generated.");
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
