import Algorithems.Data.Algo2UserInput;
import CSV.Combo.ComboCSV;
import CSV.Data.GeoPoint;
import Utils.Paths;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class Algo2Test {

    @Test
    public void algo2() throws IOException {
        File file = new File(Paths.DATA_MOODLE_TESTING+"/_comb_all_BM2_.csv");
        ComboCSV comboCSV = new ComboCSV(file);

        //init input for testing
        Algo2UserInput[] input = new Algo2UserInput[3];
        String mac1 = "00:1a:dd:e3:06:e4"; //first line first mac
        String mac2 = "00:1a:dd:e3:06:e4"; //second line first mac
        String mac3 = "14:ae:db:43:88:35"; //fourth line first mac
        double rssi1 = -81;
        double rssi2 = -86;
        double rssi3 = -91;
        input[0] = new Algo2UserInput(mac1, rssi1);
        input[1] = new Algo2UserInput(mac2, rssi2);
        input[2] = new Algo2UserInput(mac3, rssi3);

        comboCSV.getLineBy_NoGPSMac(mac1);
        GeoPoint point = Algo2.algo2(comboCSV.comboLines, input[0], input[1], input[2]);
        System.out.println("Result of Algo2: ");
        System.out.println(point.toString());
    }
}