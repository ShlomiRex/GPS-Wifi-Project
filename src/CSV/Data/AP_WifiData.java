package CSV.Data;

import CSV.Data.GeoPoint;
import CSV.Data.WifiSpectrum;

import java.awt.print.Printable;
import java.util.Date;

public class AP_WifiData {
    public GeoPoint location;
    public String mac, ssid;
    public Date firstSeen;
    public WifiSpectrum wifiSpectrum;

    public AP_WifiData(Date firstSeen, String mac, String ssid, WifiSpectrum wifiSpectrum, GeoPoint location) {
        this.location = location;
        this.firstSeen = firstSeen;
        this.wifiSpectrum = wifiSpectrum;
        this.ssid = ssid;
        this.mac = mac;
    }

    @Override
    public String toString() {
        return mac + ", " + ssid + ", LOC: [" + location.toString() + "] , WIFI: [" + wifiSpectrum.toString()+"]";
    }


}
