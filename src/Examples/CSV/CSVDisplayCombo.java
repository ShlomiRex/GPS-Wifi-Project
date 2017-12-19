package Examples.CSV;


import java.io.File;
import java.util.ArrayList;

import CSVPckg.CSV;
import CSVPckg.Record;

public class CSVDisplayCombo {
    public static void main(String[] args) throws Throwable {
        File file = new File("src/Data/Small Data For Checking/1.csv");
        CSV csv = new CSV(file);
        csv.printHeaders();
        ArrayList<ArrayList<Record>> result = csv.Matala0_Question2_GetComboRecords();

    }
}
