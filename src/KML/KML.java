package KML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.function.LongToDoubleFunction;

import CSV.CSV;
import CSV.Record;
import CSV.Record.Field;
import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.ColorMode;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.ListItemType;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.StyleSelector;

public class KML {
	
	public CSV csv;
	private Document doc;
	private Folder folder;
	private Kml kml;
	
	
	public KML(CSV csv) {
		this.csv = csv;
		if(csv == null)
			throw new NullPointerException("CSV is null.");
	}
	
	public void generateKML(File fileToGenerate) throws FileNotFoundException {
		System.out.println("Generating kml...");
		kml = new Kml();
		doc = kml.createAndSetDocument().withName("Wifi Placemarks");
		folder = doc.createAndAddFolder();
		folder.withName("Wifi WAP").withOpen(true);

		for(Record record : csv.records)
			addPlacemarks(record);
		
		kml.marshal(fileToGenerate);
		System.out.println("Done. File is at: " + fileToGenerate.getAbsolutePath());
	}

	private void addPlacemarks(Record record) {
		Placemark placemark = folder.createAndAddPlacemark();
		placemark.setDescription(getDescription(record));
		//placemark.setDescription(wrapCDATA("<b>Hello</b>"));
		placemark.setName((String) record.get_Field(Field.SSID));
		Float longitude = (Float) record.get_Field(Field.Lon);
		Float latitude = (Float) record.get_Field(Field.Lat);
		placemark.createAndSetPoint().addToCoordinates(longitude, latitude); // set coordinates
	}
	
	public static String wrapCDATA(String string) {
		return "\"<![CDATA[" + string + "]]/>\"";
	}
	
	public static String getDescription(Record record) {

		// NEED: MAC, SSID, AuthMode, RSSI, CHANNEL, Time & Date
		String mac = "MAC: <b>" + record.get_Field(Field.MAC) + "</b><br/>";
		String ssid = "SSID: <b>" + record.get_Field(Field.SSID) + "</b><br/>";
		String authMode = "AuthMode: <b>" + record.get_Field(Field.AuthMode) + "</b><br/>";
		String RSSI = "RSSI: <b>" + record.get_Field(Field.RSSI) + "</b><br/>";
		String channel = "Channel: <b>" + record.get_Field(Field.Channel) + "</b><br/>";
		String time_and_date = "Time: <b>" + record.get_Field(Field.FirstSeen) + "</b><br/>";

		String bigString = mac + ssid + authMode + RSSI + channel + time_and_date;
		//String descriptionString = wrapCDATA(bigString);

		//System.out.println(bigString);

		return bigString;
	}
}
