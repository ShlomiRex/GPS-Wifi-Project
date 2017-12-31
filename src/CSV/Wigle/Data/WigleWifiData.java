package CSV.Wigle.Data;

import CSV.Data.GeoPoint;
import CSV.Data.WifiSpectrum;
import CSV.Data.AP_WifiData;

import java.util.Date;

/**
 * Represents 1 line of Wigle CSV.
 */
public class WigleWifiData extends AP_WifiData {
    public String type, authModel;
    public WigleWifiData(Date firstSeen, String mac, String ssid, WifiSpectrum wifiSpectrum, GeoPoint location, String authMode, String type) {
        super(firstSeen, mac, ssid, wifiSpectrum, location);
        this.type = type;
        this.authModel = authMode;
    }

    public int compareFirstSeen(WigleWifiData other) {
        return firstSeen.compareTo(other.firstSeen);
    }

}
