package Tests;

import CSV.Wigle.WigleCSV;
import Utils.Paths;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class WigleCSVTest {


    @Test
    public void createCSV() throws IOException {
        WigleCSV wigleCSV;
        File folder = folder = new File(Paths.WIGLE_DATA);


        wigleCSV = new WigleCSV(folder.getAbsolutePath()+"/1.csv");
        assertTrue(wigleCSV.checkValidCSV());

        wigleCSV = new WigleCSV(folder.getAbsolutePath()+"/1_headers_not_valid.csv");
        assertTrue(! wigleCSV.checkValidCSV());
    }

    @Test
    public void readWriteCSV() throws IOException {
        WigleCSV wigleCSV;
        File folder = folder = new File(Paths.WIGLE_DATA);
        File file = new File(folder.getAbsolutePath()+"/1.csv");

        //create wigle csv and write the csv to file
        wigleCSV = new WigleCSV(file.getAbsolutePath());
        File filetoWrite = new File(Paths.OUT_TESTS+"/readWriteCSVTest.csv");
        wigleCSV.writeToFile(filetoWrite);

        //create new wigle csv and retreive the lines which we wrote before
        List<String[]> lines = wigleCSV.getLines();
        WigleCSV newWigleCSV = new WigleCSV(filetoWrite.getAbsolutePath());
        List<String[]> newLines = newWigleCSV.getLines();

        String line1, line2;
        //check each line if equal
        for(int i = 0; i < lines.size(); i++) {
            line1 = Arrays.toString(lines.get(i));
            line2 = Arrays.toString(newLines.get(i));
            if(line1.equals(line2) == false) {
                System.out.println("\n\n\n"+line1);
                System.out.println(line2);
                fail("Lines are not equal. LINE NUM: " + i);
            }
        }

    }

}