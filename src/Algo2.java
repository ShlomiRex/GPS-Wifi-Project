import CSV.Combo.ComboCSV;
import CSV.Combo.ComboLine;
import CSV.Data.AP_WifiData;
import CSV.Wigle.WigleCSV;
import Utils.Paths;

import java.io.File;
import java.io.IOException;

public class Algo2 {
    private int power = 2,
                norm = 10000,
                min_dif = 3,
                no_signal = -120,
                dif_no_signal = 100;
    private double sig_dif = 0.4;

    public static void main(String[] args) throws IOException {
        //ComboCSV comboCSV = new ComboCSV(new File(Paths.ALGO2+"_comb_all_BM2.csv"));

    }

    /**
     *
     * @param no_gps It's the no gps line.
     * @param comboLine It's the BM2 line.
     * @return
     */
    public double[] calculatePi(ComboLine no_gps, ComboLine comboLine) {
        int nogps_size = no_gps.size();
        double[] w = new double[nogps_size];
        double[] input = new double[nogps_size];

        for(int i = 0; i < no_gps.size(); i++ ) {
            input[i] = no_gps.get(i).wifiSpectrum.rssi;
        }



        return w;
    }

    //Input: MAC, RSSI
    //Output: Pi
    //ALGO 2: Take mac, search in all combo lines, take all ap_Wifidatas,
    //if thre are less than 3 macs found, put -120 in rssi (yellow)
    //IF there are more than 3 macs found

    private double calcDiff(double input_sig, double combo_wifi_signal) {
        if(combo_wifi_signal == no_signal) {
            return dif_no_signal;
        }
        return Math.max(Math.abs(input_sig-combo_wifi_signal), min_dif);
    }

    private double calcWeight(double dif, double input_sig) {
        return norm / ( Math.pow(dif, sig_dif) * Math.pow(input_sig, power) );
    }

    private double calcPi(double w1, double w2, double w3) {
        return w1 * w2 * w3;
    }
}
