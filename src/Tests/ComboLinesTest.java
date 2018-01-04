package Tests;

import CSV.Combo.ComboCSV;
import Utils.FileUtils;
import Utils.Paths;
import au.com.bytecode.opencsv.CSVReader;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ComboLinesTest {

    @Test
    public void write() throws IOException {
        File file = new File(Paths.DATA_MOODLE_TESTING + "_comb_all_BM2_.csv");
        ComboCSV comboCSV = new ComboCSV(file);
        File out = new File(Paths.OUT_TESTS+"ComboLinesTest_WriteTest.csv");
        comboCSV.writeToFile(out);

        CSVReader reader = new CSVReader(new FileReader(out));
        List<String[]> allIn = reader.readAll();
        reader.close();

        reader = new CSVReader(new FileReader(out));
        List<String[]> allOut = reader.readAll();
        reader.close();

        System.out.println("In size: " + allIn.size());
        System.out.println("Out size: " + allOut.size());

        assertEquals(allIn.size(), allOut.size());

        for(int i = 0; i < allIn.size(); i++) {
            if(Arrays.equals(allIn.get(i), allOut.get(i)) == false)
                fail("Not equal: \n"+allIn.get(i) + " \n" + allOut.get(i));
        }
        System.out.println("All is good. Input file is exactly the same as the output file.");
    }

    @Test
    public void filterStrongestRSSI() throws IOException {

        File file = new File(Paths.DATA_MOODLE_TESTING + "_comb_all_BM2_.csv");
        ComboCSV comboCSV = new ComboCSV(file);
        File out = new File(Paths.OUT_TESTS+"ComboLinesTest_WriteTest.csv");
        comboCSV.writeToFile(out);

        CSVReader reader = new CSVReader(new FileReader(out));
        List<String[]> allIn = reader.readAll();
        reader.close();

        reader = new CSVReader(new FileReader(out));
        List<String[]> allOut = reader.readAll();
        reader.close();

        System.out.println("In size: " + allIn.size());
        System.out.println("Out size: " + allOut.size());

        assertEquals(allIn.size(), allOut.size());

        for(int i = 0; i < allIn.size(); i++) {
            if(Arrays.equals(allIn.get(i), allOut.get(i)) == false)
                fail("Not equal: \n"+allIn.get(i) + " \n" + allOut.get(i));
        }
        System.out.println("All is good. Input file is exactly the same as the output file.");
    }

    @Test
    public void filterOR_by_macs() throws IOException {

    }
}