package CSV.Combo;

import CSV.Data.AP_WifiData;
import CSV.Wigle.Data.WigleWifiData;
import CSV.Wigle.WigleCSV;
import au.com.bytecode.opencsv.CSVWriter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComboLine extends ArrayList<AP_WifiData> {

    public String model, device;

    /**
     *
     * @param datas - All data (All ap wifi data)
     * @param model - Model of all of them (in same CSV) [With '=' sign]
     * @param device - Device of all of them (in same CSV) [With '=' sign]
     */
    public ComboLine(List<WigleWifiData> datas, String model, String device) {
        addAll(datas);
        this.model = model;
        this.device = device;
    }

    public void print() {
        System.out.println(this);
    }

    /** Returns String as in CSV string. (With commas)**/
    @Override
    public String toString() {
        String[] line = getLine();
        String result = "";
        for(int i = 0; i < line.length-1; i++) {
            result += line[i] +",";
        }
        result += line[line.length-1];
        return result;
    }

    /**  Without commas. **/
    public String[] getLine() {
        if(size() == 0)
            return null;

        ArrayList<String> result = new ArrayList<>();

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.add(myFormat.format(get(0).firstSeen));
        result.add(model + "_" + device);
        result.add(get(0).location.lat+"");
        result.add(get(0).location.lon+"");
        result.add(get(0).location.alt+"");
        result.add(size()+"");

        for(AP_WifiData ap_wifiData : this) {
            result.add(ap_wifiData.ssid);
            result.add(ap_wifiData.mac);
            result.add(ap_wifiData.wifiSpectrum.channel+"");
            result.add(ap_wifiData.wifiSpectrum.rssi+"");
        }
        String[] returnThis = new String[result.size()];
        for(int i = 0; i < returnThis.length; i++) {
            returnThis[i] = result.get(i);
        }
        return returnThis;
    }

    public void appendToWriter(CSVWriter writer) {
        writer.writeNext(getLine());
    }
}
