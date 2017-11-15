package KML;
import java.io.PrintWriter;
import java.util.ArrayList;

import CSV.Record;
import CSV.Records;

/**
 * A collection of place marks.
 * @author ShlomiPC
 *
 */
public class Placemarks extends ArrayList<Placemark>{

	/**
	 * By given records, create placemarks.
	 * @param records Records from CSV file.
	 */
	public Placemarks(Records records) {
		for(Record record : records) {
			add(new Placemark(record));
		}
	}
	
	/**
	 * Print all placemarks by given writer.
	 * @param printWriter
	 */
	public void printPlacemarks(PrintWriter printWriter) {
		for(Placemark placemark : this) {
			placemark.print(printWriter);
		}
	}

}
