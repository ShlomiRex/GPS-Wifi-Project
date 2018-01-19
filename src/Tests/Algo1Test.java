package Tests;

import Algorithems.Algo1;
import CSV.Combo.ComboCSV;
import CSV.Data.GeoPoint;
import CSV.Wigle.WigleCSV;
import Utils.Paths;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class Algo1Test {
    @Test
    public void test() throws IOException {
        String csvPath = Paths.WIGLE_DATA + "/1_algo1.csv";
        WigleCSV wigleCSV = new WigleCSV(csvPath);
        ComboCSV comboCSV = new ComboCSV(new File(Paths.OUT_TESTS + "/algo1test.csv"), wigleCSV, true);
        String mac = "b4:ee:b4:36:d2:b0";
        int k = 3;
        GeoPoint location = Algo1.algo1(comboCSV.comboLines, mac, k);
        System.out.printf("Location: " + location.toString());
    }
}