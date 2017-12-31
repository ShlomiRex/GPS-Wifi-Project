package CSV.Wigle.Data;

import CSV.Data.GeoPoint;
import CSV.Data.WifiSpectrum;
import CSV.Enums.EWigleCSVHeader_FieldsHeader;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents the data (without headers) of a csv.
 */
public class WigleCSVData extends ArrayList<WigleWifiData> {

    /**
     *
     * @param csvLines - Including headers. (Everything)
     * @param dataIndex - Line index when the actual data is
     */
    public WigleCSVData(List<String[]> csvLines, int dataIndex) {

        Date firstSeen;
        String mac;
        String ssid;
        WifiSpectrum wifiSpectrum;
        GeoPoint location;
        String authMode;
        String type;

        double rssi, channel, lat, lon, alt;

        WigleWifiData wigleWifiData;

        for(int i = dataIndex; i < csvLines.size(); i++) {
            firstSeen = getDate(csvLines.get(i)[EWigleCSVHeader_FieldsHeader.FirstSeen.column]);
            mac = csvLines.get(i)[EWigleCSVHeader_FieldsHeader.MAC.column];
            ssid = csvLines.get(i)[EWigleCSVHeader_FieldsHeader.SSID.column];

            rssi = Double.parseDouble(csvLines.get(i)[EWigleCSVHeader_FieldsHeader.RSSI.column]); //TODO: FIX
            channel = Double.parseDouble(csvLines.get(i)[EWigleCSVHeader_FieldsHeader.Channel.column]);

            wifiSpectrum = new WifiSpectrum(rssi, channel);

            lat = Double.parseDouble(csvLines.get(i)[EWigleCSVHeader_FieldsHeader.Lat.column]);
            lon = Double.parseDouble(csvLines.get(i)[EWigleCSVHeader_FieldsHeader.Lon.column]);
            alt = Double.parseDouble(csvLines.get(i)[EWigleCSVHeader_FieldsHeader.Alt.column]);

            location = new GeoPoint(lat, lon, alt);

            authMode = csvLines.get(i)[EWigleCSVHeader_FieldsHeader.AuthMode.column];
            type = csvLines.get(i)[EWigleCSVHeader_FieldsHeader.Type.column];

            wigleWifiData = new WigleWifiData(firstSeen, mac, ssid, wifiSpectrum, location, authMode, type);




            add(wigleWifiData);
        }

//        //print first 3lines
//        for(int i = dataIndex; i < dataIndex+3; i++) {
//            String[] s = csvLines.get(i);
//            System.out.println(Arrays.toString(s));
//
//            String s5 = s[EWigleCSVHeader_FieldsHeader.RSSI.column];
//            String s5_2 = s[5];
//
//            System.out.println(s5_2);
//            System.out.println(s5);
//            System.out.println(Double.parseDouble(s5));
//
//
//
//        }

    }

    private Date getDate(String str) {

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = null;
        try {
            date = format2.parse(str);
        } catch (ParseException e) {
            try {
                date = format1.parse(str);
            } catch (ParseException e1) {
                e1.printStackTrace();
                System.err.println("Error parsing: " + str);
            }
        }
        return date;
    }

    public void sortBy_FirstSeen() {
        this.sort(new Comparator<WigleWifiData>() {
            @Override
            public int compare(WigleWifiData w1, WigleWifiData w2) {
                return w1.firstSeen.compareTo(w2.firstSeen);
            }
        });

    }

}
