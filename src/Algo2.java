import Algorithems.Data.Algo2UserInput;
import CSV.Combo.ComboCSV;
import CSV.Combo.ComboLine;
import CSV.Combo.ComboLines;
import CSV.Data.AP_WifiData;
import CSV.Data.GeoPoint;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Algo2 {
    private static int power = 2,
                norm = 10000,
                min_dif = 3,
                no_signal = -120,
                dif_no_signal = 100;
    private static double sig_dif = 0.4;

    public static void main(String[] args) throws IOException {
        File file = Utils.FileUtils.getFileFromUser();
        ComboCSV comboCSV = new ComboCSV(file);

        Algo2UserInput[] input = new Algo2UserInput[3];
        String mac1 = "1c:b9:c4:15:ed:b8";
        String mac2 = "8c:0c:90:ae:16:83";
        String mac3 = "1c:b9:c4:16:ed:3c";
        double rssi1 = -81;
        double rssi2 = -86;
        double rssi3 = -91;
        input[0] = new Algo2UserInput(mac1, rssi1);
        input[1] = new Algo2UserInput(mac2, rssi2);
        input[2] = new Algo2UserInput(mac3, rssi3);
        comboCSV.getLineBy_NoGPSMac(mac1);
        GeoPoint point = algo2(comboCSV.comboLines, input);
        System.out.println(point.toString());
    }

    /**
     *
     * @param lines - Combo lines to calculate pi
     * @param input - The input line as described in Excel Algo 2, with each input has Mac and RSSI.
     * @return - Location of AP.
     */
    public static GeoPoint algo2(ComboLines lines, Algo2UserInput[] input) {
        int input_size = input.length;
        int lines_size = lines.size();

        if(input_size!= 3)
            return null;

        String[] input_macs = new String[input_size];
        for(int i = 0; i < input_size; i++)
            input_macs[i] = input[i].mac;
        //filter
        lines = lines.filterOR_by_macs(input_macs);

        for(int i = 0; i < lines.size(); i++) {
            System.out.println(lines.get(i).toString());
        }

        double[] weights_in_line = new double[3];
        int lineIndex = 0;
        double input_signal, pi, dif;
        boolean founded = false;
        ComboLineWithPi[] comboLineWithPis = new ComboLineWithPi[lines.size()];
        for(ComboLine comboLine : lines) {
            for(int i = 0; i < 3; i++) {
                input_signal = input[i].rssi;
                founded = false;
                for (AP_WifiData ap_wifiData1 : comboLine) {
                    if (ap_wifiData1.mac.equals(input[i])) {
                        dif = calcDiff(input_signal, ap_wifiData1.wifiSpectrum.rssi);
                        weights_in_line[i] = calcWeight(dif, input_signal);
                        founded = true;
                        break;
                    }
                }
                if (founded == false) {
                    dif = dif_no_signal;
                    weights_in_line[i] = calcWeight(dif, input_signal);
                }
            }

            pi = weights_in_line[0] * weights_in_line[1] * weights_in_line[2];
            comboLineWithPis[lineIndex] = new ComboLineWithPi(comboLine, pi);

            lineIndex++;
        }

        int numOfStrongestLinesByPiToTake = 4;
        List<ComboLineWithPi> comboLineWithPis_StrongestByPi = (List<ComboLineWithPi>)Arrays.
                stream(comboLineWithPis).
                parallel().
                sorted().
                limit(numOfStrongestLinesByPiToTake)
                .collect(Collectors.toList());

        GeoPoint[] geoPointsOfStrongest = new GeoPoint[numOfStrongestLinesByPiToTake];
        for(int i = 0; i < geoPointsOfStrongest.length; i++) {
            geoPointsOfStrongest[i] = comboLineWithPis_StrongestByPi.get(i).comboLine.get(0).location;
        }
        double[] signalsOfStrongest = new double[numOfStrongestLinesByPiToTake];
        for(int i = 0; i  < numOfStrongestLinesByPiToTake; i++) {
            signalsOfStrongest[i] = comboLineWithPis_StrongestByPi.get(i).comboLine.get(0).wifiSpectrum.rssi;
        }
        return Algo1.algo1(geoPointsOfStrongest, signalsOfStrongest);
    }

    private static class ComboLineWithPi implements Comparable<ComboLineWithPi>{
        ComboLine comboLine;
        double pi;
        public ComboLineWithPi(ComboLine comboLine, double pi) {
            this.comboLine = comboLine;
            this.pi = pi;
        }

        @Override
        public int compareTo(ComboLineWithPi other) {
            return Double.compare(pi, other.pi);
        }
    }

    /**
     *
     * @param input_sig
     * @param combo_wifi_signal
     * @return
     */
    private static double calcDiff(double input_sig, double combo_wifi_signal) {
        return Math.max(Math.abs(input_sig-combo_wifi_signal), min_dif);
    }

    /**
     * Calculates weight and returns it.
     * @param dif
     * @param input_sig
     * @return
     */
    private static double calcWeight(double dif, double input_sig) {
        return norm / (Math.pow(dif, sig_dif) * Math.pow(input_sig, power));
    }

}
