package CSV.Combo;

import CSV.Data.AP_WifiData;
import CSV.Data.GeoPoint;
import CSV.Data.WifiSpectrum;
import CSV.Enums.DateFormats;
import CSV.Enums.EComboDatas;
import CSV.Wigle.Data.WigleWifiData;
import au.com.bytecode.opencsv.CSVWriter;
import com.sun.istack.NotNull;
import org.omg.CORBA.ExceptionList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ComboLine extends ArrayList<AP_WifiData> {

    public String model, device;

    /**
     * Call this function if conversion is from WIGLE CSV.
     * @param datas - All data (All ap wifi data)
     * @param model - Model of all of them (in same CSV) [With '=' sign]
     * @param device - Device of all of them (in same CSV) [With '=' sign]
     */
    public ComboLine(List<WigleWifiData> datas, String model, String device) {
        addAll(datas);
        this.model = model;
        this.device = device;
    }

    /**
     * Call this function if the conversion if from COMBO LINE.
     *  This constuctor only works for COMBO FILE and NOT WIGLE FILE.
     * @param comboLine - A combo line. (Can be invalid, but throws.)
     * @throws Exception - Throws exception if line is not combo line.
     */
    public ComboLine(String[] comboLine) throws Exception {
        if(comboLine == null)
            throw new Exception("Null line.");
        if(comboLine.length <= 6 || comboLine.length > 46)
            throw new Exception("Invalid combo line size [" + comboLine.length + "] : " + Arrays.toString(comboLine));
        Date firstSeen = DateFormats.parse(comboLine[0]);

        ArrayList<AP_WifiData> datas = new ArrayList<>();
        int numOfDatas = Integer.parseInt(comboLine[EComboDatas.NumOfDatas.column]);

        int startingDataCol = EComboDatas.StartingDataColumn.column;
        int endDataCol = startingDataCol-1 + numOfDatas*4;


        String[] tempWifiDataLine = new String[4];
        AP_WifiData tempWifiData;


        String mac;
        String ssid;
        WifiSpectrum wifiSpectrum;
        GeoPoint location;
        double channel, rssi;

        double lat, lon, alt;
        lat = Double.parseDouble(comboLine[EComboDatas.Lat.column]);
        lon = Double.parseDouble(comboLine[EComboDatas.Lon.column]);
        alt = Double.parseDouble(comboLine[EComboDatas.Alt.column]);

        for(int i = startingDataCol; i < endDataCol-4; i += 4) {
            tempWifiDataLine[0] = comboLine[i];
            tempWifiDataLine[1] = comboLine[i+1];
            tempWifiDataLine[2] = comboLine[i+2];
            tempWifiDataLine[3] = comboLine[i+3];

            ssid = tempWifiDataLine[0];
            mac = tempWifiDataLine[1]; //i had problem with this, i put 0 insted of 1! LOL
            channel = Double.parseDouble(tempWifiDataLine[2]);
            rssi = Double.parseDouble(tempWifiDataLine[3]);
            location = new GeoPoint(lat, lon, alt);

            wifiSpectrum = new WifiSpectrum(rssi, channel);
            tempWifiData = new AP_WifiData(firstSeen, mac, ssid, wifiSpectrum, location);
            add(tempWifiData);
        }
    }

    public void print() {
        System.out.println(this);
    }

    /** Returns String as in CSV string. (With commas)**/
    @Override
    public String toString() {
        String[] line = asArray();
        String result = "";
        for(int i = 0; i < line.length-1; i++) {
            result += line[i] +",";
        }
        result += line[line.length-1];
        return result;
    }

    public String[] asArray() {
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
        writer.writeNext(asArray());
    }

    public Date getFirstSeen() {
        return get(0).firstSeen;
    }

    public GeoPoint getLocation() {
        return get(0).location;
    }

    /**
     * May return null if out of bounds.
     * @param index
     * @return
     */
    @Override
    public AP_WifiData get(int index) {
        if(index > size() || index < 0)
            return null;
        return super.get(index);
    }
}
