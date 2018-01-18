import Algorithems.Data.Algo2UserInput;
import CSV.Combo.ComboCSV;
import CSV.Combo.ComboLine;
import CSV.Combo.ComboLines;
import CSV.Data.AP_WifiData;
import CSV.Data.GeoPoint;
import Utils.Paths;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Algo2 {
    private static int power = 2,
                norm = 10000,
                min_dif = 3,
                no_signal = -120,
                dif_no_signal = 100;
    private static double sig_dif = 0.4;

//    /**
//     *
//     * @param lines - Combo lines to calculate pi
//     * @param input - The input line as described in Excel Algo 2, with each input has Mac and RSSI.
//     * @return - Location of AP.
//     * @deprecated - NOT WORKING
//     */
//    public static GeoPoint algo2(ComboLines lines, Algo2UserInput[] input) {
//        int input_size = input.length;
//        int lines_size = lines.size();
//
//        if(input_size!= 3)
//            return null;
//
//        String[] input_macs = new String[input_size];
//        for(int i = 0; i < input_size; i++)
//            input_macs[i] = input[i].mac;
//        //filter
//        lines = lines.filterOR_by_macs(input_macs);
//        lines.print();
//
//        double[] weights_in_line = new double[3];
//        int lineIndex = 0;
//        double input_signal, pi, dif;
//        boolean founded = false;
//        ComboLineWithPi[] comboLineWithPis = new ComboLineWithPi[lines.size()];
//        for(ComboLine comboLine : lines) {
//            for(int i = 0; i < 3; i++) {
//                input_signal = input[i].rssi;
//                founded = false;
//                for (AP_WifiData ap_wifiData1 : comboLine) {
//                    if (ap_wifiData1.mac.equals(input[i])) {
//                        dif = calcDiff(input_signal, ap_wifiData1.wifiSpectrum.rssi);
//                        weights_in_line[i] = calcWeight(dif, input_signal);
//                        founded = true;
//                        break;
//                    }
//                }
//                if (founded == false) {
//                    dif = dif_no_signal;
//                    weights_in_line[i] = calcWeight(dif, input_signal);
//                }
//            }
//
//            pi = weights_in_line[0] * weights_in_line[1] * weights_in_line[2];
//            comboLineWithPis[lineIndex] = new ComboLineWithPi(comboLine, pi);
//
//            lineIndex++;
//        }
//
//        int numOfStrongestLinesByPiToTake = 4;
//        List<ComboLineWithPi> comboLineWithPis_StrongestByPi = (List<ComboLineWithPi>)Arrays.
//                stream(comboLineWithPis).
//                parallel().
//                sorted().
//                limit(numOfStrongestLinesByPiToTake)
//                .collect(Collectors.toList());
//
//        GeoPoint[] geoPointsOfStrongest = new GeoPoint[numOfStrongestLinesByPiToTake];
//        for(int i = 0; i < geoPointsOfStrongest.length; i++) {
//            geoPointsOfStrongest[i] = comboLineWithPis_StrongestByPi.get(i).comboLine.get(0).location;
//        }
//        double[] signalsOfStrongest = new double[numOfStrongestLinesByPiToTake];
//        for(int i = 0; i  < numOfStrongestLinesByPiToTake; i++) {
//            signalsOfStrongest[i] = comboLineWithPis_StrongestByPi.get(i).comboLine.get(0).wifiSpectrum.rssi;
//        }
//        return Algo1.algo1(geoPointsOfStrongest, signalsOfStrongest);
//    }

    public static void main(String[] args) {

    }

    public static GeoPoint algo2(ComboLines comboLines, Algo2UserInput input1, Algo2UserInput input2, Algo2UserInput input3) {
        if(input1 == null || input2 == null || input3 == null)
            throw new IllegalArgumentException("Input is null.");
        ComboLineWithPi[] comboLineWithPis = new ComboLineWithPi[comboLines.size()];
        int comboLineWithPisIndex = 0;
        double w1, w2, w3, dif, pi;
        String input_mac;
        AP_WifiData ap_wifiData;


        for(ComboLine comboLine : comboLines) {
            //input 1
            ap_wifiData = findAPInComboByMac(comboLine, input1.mac);
            //if not found
            if(ap_wifiData == null)
                dif = dif_no_signal;
            else {
                dif = calcDiff(input1.rssi, ap_wifiData.wifiSpectrum.rssi);
            }
            w1 = calcWeight(dif, input1.rssi);

            //input 2
            ap_wifiData = findAPInComboByMac(comboLine, input2.mac);
            //if not found
            if(ap_wifiData == null)
                dif = dif_no_signal;
            else {
                dif = calcDiff(input2.rssi, ap_wifiData.wifiSpectrum.rssi);
            }
            w2 = calcWeight(dif, input2.rssi);

            //input 3
            ap_wifiData = findAPInComboByMac(comboLine, input3.mac);
            //if not found
            if(ap_wifiData == null)
                dif = dif_no_signal;
            else {
                dif = calcDiff(input3.rssi, ap_wifiData.wifiSpectrum.rssi);
            }
            w3 = calcWeight(dif, input3.rssi);

            pi = calcPi(w1, w2, w3);
            comboLineWithPis[comboLineWithPisIndex] = new ComboLineWithPi(comboLine,pi);
            comboLineWithPisIndex++;
            //System.out.println("W1 = " + w1 + " W2 = " + w2 + " W3 = " + w3 + " PI = " + pi);
        }
        final int k = 3;
        //take K strongest pi
        List<ComboLineWithPi> comboLineWithPis_StrongestByPi = (List<ComboLineWithPi>)Arrays.
                stream(comboLineWithPis).
                parallel().
                sorted(new Comparator<ComboLineWithPi>() {
                    @Override
                    public int compare(ComboLineWithPi o1, ComboLineWithPi o2) {
                        return Double.compare(o2.pi, o1.pi);
                    }
                }).
                limit(k).
                collect(Collectors.toList());
        System.out.println("K Strongest combo lines by pi: ");
        for(ComboLineWithPi comboLineWithPi : comboLineWithPis_StrongestByPi) {
            System.out.println(comboLineWithPi);
        }
        //prepare for algo1
        GeoPoint[] geoPoints = new GeoPoint[k];
        double[] signals = new double[k];
        for(int i = 0; i < k; i++) {
            geoPoints[i] = comboLineWithPis_StrongestByPi.get(i).comboLine.getLocation();

        }
        signals[0] = input1.rssi;
        signals[1] = input2.rssi;
        signals[2] = input3.rssi;

        double pi1 = comboLineWithPis_StrongestByPi.get(0).pi;
        double pi2 = comboLineWithPis_StrongestByPi.get(1).pi;
        double pi3 = comboLineWithPis_StrongestByPi.get(2).pi;

        double lat1 = comboLineWithPis_StrongestByPi.get(0).comboLine.getLocation().lat;
        double lat2 = comboLineWithPis_StrongestByPi.get(1).comboLine.getLocation().lat;
        double lat3 = comboLineWithPis_StrongestByPi.get(2).comboLine.getLocation().lat;

        double lon1 = comboLineWithPis_StrongestByPi.get(0).comboLine.getLocation().lon;
        double lon2 = comboLineWithPis_StrongestByPi.get(1).comboLine.getLocation().lon;
        double lon3 = comboLineWithPis_StrongestByPi.get(2).comboLine.getLocation().lon;

        double alt1 = comboLineWithPis_StrongestByPi.get(0).comboLine.getLocation().alt;
        double alt2 = comboLineWithPis_StrongestByPi.get(1).comboLine.getLocation().alt;
        double alt3 = comboLineWithPis_StrongestByPi.get(2).comboLine.getLocation().alt;

        double wLat_Sum = lat1 * pi1 + lat2 * pi2 + lat3 * pi3;
        double wLon_Sum = lon1 * pi1 + lon2 * pi2 + lon3 * pi3;
        double wAlt_Sum = alt1 * pi1 + alt2 * pi2 + alt3 * pi3;
        double wSum_Weights = pi1 + pi2 + pi3;

        double result_lat = wLat_Sum/wSum_Weights;
        double result_lon = wLon_Sum/wSum_Weights;
        double result_alt = wAlt_Sum/wSum_Weights;

        return new GeoPoint(result_lat, result_lon, result_alt);
    }

    /**
     * In combo line, find AP_WifiData by given MAC address, Return null if not found.
     * @param mac
     * @return
     */
    private static AP_WifiData findAPInComboByMac(ComboLine comboLine, String mac) {
        for(AP_WifiData ap_wifiData : comboLine) {
            if(ap_wifiData.mac.equals(mac))
                return ap_wifiData;
        }
        return null;
    }


    private static class ComboLineWithPi implements Comparable<ComboLineWithPi>{
        ComboLine comboLine;
        double pi;
        public ComboLineWithPi(ComboLine comboLine, double pi) {
            this.comboLine = comboLine;
            this.pi = pi;
        }

        @Override
        public String toString() {
            return "COMBO LINE: " + comboLine.toString() + " PI : " + pi;
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

    /**
     * Simple multiplication
     * @param w1
     * @param w2
     * @param w3
     * @return
     */
    private static double calcPi(double w1, double w2, double w3) {
        return w1 * w2 * w3;
    }

}
