package Examples.CSV;

import CSVPckg.CSV;
import CSVPckg.CSVCombo;
import CSVPckg.CSVHeaders;
import CSVPckg.Record;
import Examples.IOUser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCombo {

    String path = "src/Data/Small Data For Checking/1.csv";
    File file = new File(path);



    @Test
    public void test_CSVIsComboFunction() throws Throwable {
        CSVCombo csvCombo = new CSVCombo(file);

        Record r1 = csvCombo.records.get(0);
        Record r2 = csvCombo.records.get(1);
        Record r3 = csvCombo.records.get(2);
        Record r4 = csvCombo.records.get(3);
        Record r5 = csvCombo.records.get(4);
        Record r6 = csvCombo.records.get(5);

        assertTrue(csvCombo.isSameLine(r1, r2));
        assertTrue(! csvCombo.isSameLine(r2, r3));
        assertTrue(csvCombo.isSameLine(r3, r4));
        assertTrue(csvCombo.isSameLine(r4, r5));
        assertTrue(! csvCombo.isSameLine(r5, r6));

    }

    @Test
    public void test_PrintAllCombos() throws Throwable {
        CSVCombo csvCombo = new CSVCombo(file);
        ArrayList<ArrayList<Record>> res = csvCombo.getSubarraysComboRecords();
        for(ArrayList<Record> element : res) {
            System.out.println("Same combo:");
            for(Record r : element) {
                r.print();
            }
        }
    }

    @Test
    public void test_CSVHeaderGet() throws Throwable {
        CSV csv = new CSV(file);
        assertEquals("model=Lenovo PB2-690Y",csv.headers.wigle_getField(CSVHeaders.WigleField.model));
        assertEquals("device=PB2PRO", csv.headers.wigle_getField(CSVHeaders.WigleField.device));
    }

    @Test
    public void test_ComboWriting() throws Throwable {
        //CSVCombo csvCombo = new CSVCombo(file);
        CSVCombo csvCombo = new CSVCombo(IOUser.getFileByUser());
        File fileOut = new File("src/Data/testComboWriting2.csv");
        csvCombo.writeCombo(fileOut, false);
        IOUser.openFile(fileOut);
    }

    @Test
    public void test_getCSVStringFromArrayList() throws Throwable {
        CSVCombo csvCombo = new CSVCombo(file);
        ArrayList<String> testArr =  new ArrayList<>();
        testArr.add("A");
        testArr.add("B");
        testArr.add("C");
        testArr.add("D");
        testArr.add("E");
        String[] res = csvCombo.getCSVStringFromArrayList(testArr);
        String actual;
        String expected = "A,B,C,D,E";

        StringBuilder builder = new StringBuilder();
        for (String value : res) {
            builder.append(value);
        }
        actual = builder.toString();

        System.out.println(expected);
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void test_getComboFields() throws Throwable {
        CSVCombo csvCombo = new CSVCombo(file);
        String[] result = csvCombo.getComboFields();
        for(int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println("\nFields: " + result.length);
    }
}
