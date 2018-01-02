package CSV.Combo;

import CSV.Data.AP_WifiData;
import CSV.Data.Basic.AbstractCSV;
import CSV.Wigle.WigleCSV;
import Utils.FolderUtils;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This represents a collection of CSV
 */
public class ComboCSV extends AbstractCSV{

    public ArrayList<ComboLine> wigleCSVArrayList;
    public ComboLines comboLines;

//    /**
//     *
//     * @param csvsFolder - Folder containing wigle csvs to be converted.
//     * @param fileToWrite - Write to this file.
//     * @throws IOException - If problem reading file or file doesn't exists.
//     */
//    public ComboCSV(File fileToWrite, File csvsFolder) throws IOException {
//        this(fileToWrite, FolderUtils.getWigles(csvsFolder));
//    }

//    /**
//     *
//     * @param fileToWrite - Write to this file.
//     * @param wigleCSVList - List of wigle csv to write.
//     * @throws IOException
//     */
//    public ComboCSV(File fileToWrite ,List<WigleCSV> wigleCSVList) throws IOException {
//        super(fileToWrite.getAbsolutePath());
//        this.wigleCSVArrayList = new ArrayList<>();
//        this.comboLines = new ComboLines(wigleCSVList);
//        comboLines.write(this);
//        lines = read(this);
//    }

    /**
     *
     * @param fileToWrite - Write to this file.
     * @param wigleCSVList - List of wigle csv to write.
     * @param takeStrongest10 - True if want 10 elements in each combo line.
     * @throws IOException
     */
    public ComboCSV(File fileToWrite ,WigleCSV wigleCSVList, boolean takeStrongest10) throws IOException {
        //set this csv file as file we write to.
        super(fileToWrite.getAbsolutePath());

        System.out.println("Initializing combo csv...");
        //set combo lines
        this.comboLines = new ComboLines(wigleCSVList);
        //write combo
        comboLines.write(this);
        //init csv lines
        lines = read(this);
        System.out.println("CSV Combo initialized: " + fileToWrite.getAbsolutePath());

        if(takeStrongest10)
            filterStrongestRSSI();
    }

    /**
     * This csv is always valid. (For now)
     * @return
     */
    @Override
    public boolean checkValidCSV() {
        //tODO: Decide validation of combo.
        return true;
    }

    /**
     * Filters all combo lines whos size is above 10 and set line max combo data to 10 and takes only strongest RSSI.<br>
     *     Also writes changes to file.
     */
    private void filterStrongestRSSI() throws IOException {
        comboLines.filterStrongestRSSI();
        comboLines.write(this);
    }

    /**
     * @since MATALA 2 QUESTION 2
     * @param mac
     * @param k - Take 'k' strongest matching mac datas. (usually 4 or 3 as descrobed in matala 2)
     * @return - Array of sum of weights of size 3. <br>
     *             First element: wcenter lat <br>
     *             Second element: wcenter lon <br>
     *             Third element: wcenter alt
     */
    public double[] calculateAPLocation(String mac, int k) {
        if(k <= 0)
            return null;
        double[] result = new double[3];
        double wcenter_lat = 0, wcenter_lon = 0, wcenter_alt = 0;
        List<AP_WifiData> l = getKStrongestAPWifiDataByMac(mac, k);
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

        //return result
        result[0] = wcenter_lat;
        result[1] = wcenter_lon;
        result[2] = wcenter_alt;
        return  result;
    }

    /**
     * As the name suggests.
     * @return
     */
    private List<AP_WifiData> getKStrongestAPWifiDataByMac(String mac, int k) {
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
