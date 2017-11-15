package KML;

import java.io.PrintWriter;

import CSV.Record;

/**
 * An object that represents a point on the globe.
 * @author ShlomiPC
 *
 */
public class Placemark {
	
	/**
	 * The record of CSV file that represents this class.
	 */
	private Record record;
	
	public Placemark(Record record) {
		this.record = record;
	}

	/**
	 * Write a placemark to writer.
	 * @param printWriter
	 */
	public void print(PrintWriter printWriter) {
		//print placemark element
		printWriter.println("<Placemark>");
		
		//print name element
		PlacemarkPrinter.printNameElement_SSID(printWriter, record);
		
		//print description
		PlacemarkPrinter.printDescription(printWriter, record);
		
		//print point
		PlacemarkPrinter.printPoint(printWriter, record);
		
		//close placemark element
		printWriter.println("</Placemark>");
	}

}
