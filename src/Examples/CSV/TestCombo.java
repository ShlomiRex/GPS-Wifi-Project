package Examples.CSV;

import CSVPckg.CSV;
import CSVPckg.Record;
import CSVPckg.Records;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCombo {

    String path = "src/Data/Small Data For Checking/1.csv";
    File file = new File(path);

    @Test
    public void test() throws Throwable {
        CSV csv = new CSV(file);

        Record r1 = csv.records.get(0);
        Record r2 = csv.records.get(1);
        Record r3 = csv.records.get(2);
        Record r4 = csv.records.get(3);
        Record r5 = csv.records.get(4);
        Record r6 = csv.records.get(5);

        assertTrue(csv.isSameLine(r1, r2));
        assertTrue(! csv.isSameLine(r2, r3));
        assertTrue(csv.isSameLine(r3, r4));
        assertTrue(csv.isSameLine(r4, r5));
        assertTrue(! csv.isSameLine(r5, r6));

        test2();
    }

    @Test
    public void test2() throws Throwable {
        CSV csv = new CSV(file);
        ArrayList<ArrayList<Record>> res = csv.Matala0_Question2_GetComboRecords();
        ArrayList<Record> arrr1 = res.get(0);
        ArrayList<Record> arrr2 = res.get(1);

        System.out.println("Same combo:");
        for(Record r : arrr1) {
            r.print();
        }

        System.out.println("Same combo:");
        for(Record r : arrr2) {
            r.print();
        }
    }
}
