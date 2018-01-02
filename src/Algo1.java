
import CSV.Combo.ComboCSV;
import CSV.Wigle.WigleCSV;
import Utils.Paths;

import java.io.File;
import java.io.IOException;

public abstract class Algo1 {

    public static void main(String[] args) throws IOException {
        String csvPath = Paths.WIGLE_DATA + "/1_algo1.csv";
        WigleCSV wigleCSV = new WigleCSV(csvPath);
        ComboCSV comboCSV = new ComboCSV(new File(Paths.OUT_TESTS + "/algo1test.csv"), wigleCSV, true);
        String mac = "b4:ee:b4:36:d2:b0";
        int k = 3;
        double[] sumWTotal = comboCSV.calculateAPLocation(mac, k);
        System.out.printf("SUM TOTAL: FINAL RESULT: %f %f %f" ,sumWTotal[0], sumWTotal[1], sumWTotal[2]);
    }
}
