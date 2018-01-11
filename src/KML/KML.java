package KML;

import CSV.Data.Basic.AbstractCSV;
import CSV.Wigle.Data.WigleWifiData;
import CSV.Wigle.WigleCSV;
import com.sun.prism.impl.Disposer;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

import java.io.File;
import java.io.FileNotFoundException;

public class KML extends File {
    public WigleCSV wigleCSV;
    private Document doc;
    private Folder folder;
    private Kml kml;

    /**
     *
     * @param filePathName Path to output the kml.
     * @param csvToConvert
     */
    public KML(String filePathName, WigleCSV csvToConvert) {
        super(filePathName);
        if(csvToConvert == null || csvToConvert == null)
            throw new NullPointerException("Invalid arguments.");

        this.wigleCSV = csvToConvert;
    }

    public void generateKML() throws FileNotFoundException {
        System.out.println("Generating kml...");
        Kml kml = new Kml();
        doc = kml.createAndSetDocument().withName("Wifi Placemarks");
        folder = doc.createAndAddFolder();
        folder.withName("Wifi WAP").withOpen(true);

        for(WigleWifiData wigleWifiData : wigleCSV.csvData)
            addPlacemarks(wigleWifiData);

        kml.marshal(this);
        System.out.println("Done. File is at: " + getAbsolutePath());
    }

    private void addPlacemarks(WigleWifiData wigleWifiData) {
        Placemark placemark = folder.createAndAddPlacemark();
        placemark.setDescription(getDescription(wigleWifiData));
        //placemark.setDescription(wrapCDATA("<b>Hello</b>"));
        placemark.setName(wigleWifiData.ssid);
        placemark.createAndSetPoint().addToCoordinates(wigleWifiData.location.lon, wigleWifiData.location.lat); // set coordinates
    }

    public static String getDescription(WigleWifiData wigleWifiData) {
        // NEED: MAC, SSID, AuthMode, RSSI, CHANNEL, Time & Date
        String mac = "MAC: <b>" + wigleWifiData.mac + "</b><br/>";
        String ssid = "SSID: <b>" + wigleWifiData.ssid + "</b><br/>";
        String RSSI = "RSSI: <b>" + wigleWifiData.wifiSpectrum.rssi + "</b><br/>";
        String channel = "Channel: <b>" + wigleWifiData.wifiSpectrum.channel + "</b><br/>";
        String firstseen = "Time: <b>" + wigleWifiData.firstSeen + "</b><br/>";
        String authmode = "Auth: <b>" + wigleWifiData.authMode + "</b><br/>";

        return mac + ssid + authmode + RSSI + channel + firstseen;
    }

}
