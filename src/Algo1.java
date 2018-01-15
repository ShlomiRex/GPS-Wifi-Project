
import CSV.Combo.ComboCSV;
import CSV.Combo.ComboLine;
import CSV.Combo.ComboLines;
import CSV.Data.AP_WifiData;
import CSV.Data.GeoPoint;
import CSV.Wigle.WigleCSV;
import Utils.Paths;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class Algo1 {

    public static void main(String[] args) throws IOException {
        String csvPath = Paths.WIGLE_DATA + "/1_algo1.csv";
        WigleCSV wigleCSV = new WigleCSV(csvPath);
        ComboCSV comboCSV = new ComboCSV(new File(Paths.OUT_TESTS + "/algo1test.csv"), wigleCSV, true);
        String mac = "b4:ee:b4:36:d2:b0";
        int k = 3;
        GeoPoint location = algo1(comboCSV.comboLines, mac, k);
        System.out.printf("Location: " + location.toString());
    }
    /**
     * Calculates GPS location by given array of routers and their location and each location his RSSI.
     * @param geoPoints
     * @param signals
     * @return Null if sizes are not equal. Each geoPoint must be coresponse to signal at the same index.
     * @deprecated - Do not use if you know what to do.
     */
    public static GeoPoint algo1(GeoPoint[] geoPoints, double[] signals) {
        int size = geoPoints.length;
        double wLat, wLon, wAlt;
        double[] weights = new double[size];
        double sumWLat = 0, sumWLon = 0, sumWAlt = 0;
        for(int i = 0; i < size; i++) {
            weights[i] = 1/Math.pow(signals[i],signals[i]);
            wLat = geoPoints[i].lat * weights[i];
            wLon = geoPoints[i].lon * weights[i];
            wAlt = geoPoints[i].alt * weights[i];

            sumWLat += wLat;
            sumWLon += wLon;
            sumWAlt += wAlt;
        }
        double sumWeights = Arrays.stream(weights).sum();

        return new GeoPoint(sumWLat * sumWeights, sumWLon * sumWeights, sumWAlt * sumWeights);
    }

    /**
     * Calculate GeoPoint of spesific router by given MAC address in data of comboLiens. Take K strongest matching lines.
     * @since MATALA 2 QUESTION 2
     * @param mac
     * @param k - Take 'k' strongest matching mac datas. (usually 4 or 3 as descrobed in matala 2)
     * @return - Array of sum of weights of size 3. <br>
     *             First element: wcenter lat <br>
     *             Second element: wcenter lon <br>
     *             Third element: wcenter alt
     */
    public static GeoPoint algo1(ComboLines comboLines, String mac, int k) {
        if(k <= 0)
            return null;
        double wcenter_lat = 0, wcenter_lon = 0, wcenter_alt = 0;
        List<AP_WifiData> l = getKStrongestAPWifiDataByMac(comboLines, mac, k);
        ArrayList<AP_WifiData> kStrongest_ap_wifiData = (ArrayList<AP_WifiData>) l;

        double[] weights = new double[k];
        double[] wLats = new double[k];
        double[] wLons = new double[k];
        double[] wAlts = new double[k];

        for(int i = 0; i < kStrongest_ap_wifiData.size(); i++) {
            weights[i] = 1/(kStrongest_ap_wifiData.get(i).wifiSpectrum.rssi * kStrongest_ap_wifiData.get(i).wifiSpectrum.rssi);
            wLats[i] = kStrongest_ap_wifiData.get(i).location.lat * weights[i];
            wLons[i] = kStrongest_ap_wifiData.get(i).location.lon * weights[i];
            wAlts[i] = kStrongest_ap_wifiData.get(i).location.alt * weights[i];
        }

        //set sum via java stream sum func
        double[] sum = new double[3];
        sum[0] = Arrays.stream(wLats).sum();
        sum[1] = Arrays.stream(wLons).sum();
        sum[2] = Arrays.stream(wAlts).sum();

        double weightsSum = Arrays.stream(weights).sum();

        wcenter_lat = sum[0] / weightsSum;
        wcenter_lon = sum[1] / weightsSum;
        wcenter_alt = sum[2] / weightsSum;

        return new GeoPoint(wcenter_lat, wcenter_lon, wcenter_alt);
    }

    /**
     * As the name suggests.
     * @return
     */
    private static List<AP_WifiData> getKStrongestAPWifiDataByMac(ComboLines comboLines, String mac, int k) {
        //Find all ap wifi datas with same mac
        ArrayList<AP_WifiData> all = new ArrayList<>();
        for(ComboLine comboLine : comboLines) {
            for(AP_WifiData ap_wifiData : comboLine) {
                if(ap_wifiData.mac.equals(mac))
                    all.add(ap_wifiData);
            }
        }
        all.sort(new Comparator<AP_WifiData>() {
            @Override
            public int compare(AP_WifiData o1, AP_WifiData o2) {
                return Double.compare(o1.wifiSpectrum.rssi, o2.wifiSpectrum.rssi);
            }
        });

        System.out.println("K strongest: ");

        if(all.size() > k) {
            List<AP_WifiData> returnThis = new ArrayList<>(all.subList(0, k));

            for(AP_WifiData ap_wifiData : returnThis) {
                System.out.println(ap_wifiData.toString());
            }

            return returnThis;
        }

        for(AP_WifiData ap_wifiData : all) {
            System.out.println(ap_wifiData.toString());
        }

        return all;
    }

}
