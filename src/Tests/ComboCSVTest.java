package Tests;

import CSV.Combo.ComboCSV;
import CSV.Wigle.WigleCSV;
import Utils.Paths;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ComboCSVTest {
    @Test
    public void createComboFromWigleCSV() throws IOException {
        File wigle = new File(Paths.WIGLE_DATA+"/1.csv");
        WigleCSV wigleCSV = new WigleCSV(wigle.getAbsolutePath());
        File fileOut = new File(Paths.OUT_TESTS+"/ComboTest.csv");
        ComboCSV comboCSV = new ComboCSV(fileOut, wigleCSV);
        comboCSV.filterStrongestRSSI();
    }
}