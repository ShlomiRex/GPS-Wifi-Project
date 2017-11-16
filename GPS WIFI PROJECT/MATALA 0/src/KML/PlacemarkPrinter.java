package KML;

import java.io.PrintWriter;

import CSV.Record;

public abstract class PlacemarkPrinter {
	/**
	 * @return
	 * 
	 *         <pre>
	 * 	{@code
	 * 		<![CDATA[the parameter]]>
	 * 	}
	 *         </pre>
	 * 
	 * @param name
	 */
	public static String getCDATAWrapper(String name) {
		return "<![CDATA[" + name + "]]>";
	}

	/**
	 * Prints name element to placemark and the data is the SSID of the record.
	 * 
	 * @param printWriter
	 * @param record
	 */
	public static void printNameElement_SSID(PrintWriter printWriter, Record record) {
		printWriter.println("<name>");
		String name = (String) record.get_Field(Record.Field.SSID);
		String nameElementString = getCDATAWrapper(name); // wrap name in element string
		printWriter.println(nameElementString);
		printWriter.println("</name>");
	}

	/**
	 * Prints the description of the placemark.
	 * 
	 * @param printWriter
	 * @param record
	 */
	public static void printDescription(PrintWriter printWriter, Record record) {

		// element open
		printWriter.println("<description>");

		// NEED: MAC, SSID, AuthMode, RSSI, CHANNEL, Time & Date
		String mac = "MAC: <b>" + record.get_Field(Record.Field.MAC) + "</b><br/>";
		String ssid = "SSID: <b>" + record.get_Field(Record.Field.SSID) + "</b><br/>";
		String authMode = "AuthMode: <b>" + record.get_Field(Record.Field.AuthMode) + "</b><br/>";
		String RSSI = "RSSI: <b>" + record.get_Field(Record.Field.RSSI) + "</b><br/>";
		String channel = "Channel: <b>" + record.get_Field(Record.Field.Channel) + "</b><br/>";
		String time_and_date = "Time: <b>" + record.get_Field(Record.Field.FirstSeen) + "</b><br/>";

		String bigString = mac + ssid + authMode + RSSI + channel + time_and_date;
		String descriptionString = getCDATAWrapper(bigString);

		printWriter.println(descriptionString);

		// element close
		printWriter.println("</description>");

	}

	/**
	 * Prints the point element that contains only coordinants.
	 * 
	 * @param printWriter
	 * @param record
	 */
	public static void printPoint(PrintWriter printWriter, Record record) {
		printWriter.println("<Point>");
		Float latitude = (Float) record.get_Field(Record.Field.Lat);
		Float longtitude = (Float) record.get_Field(Record.Field.Lon);
		String coordinantesString = "<coordinates>" + longtitude + "," + latitude + "</coordinates>";
		printWriter.println(coordinantesString);
		printWriter.println("</Point>");
	}

}
