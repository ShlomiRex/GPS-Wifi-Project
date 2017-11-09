package KML.Helper;

import java.io.PrintWriter;

import CSV.Helper.Record;

public class Placemark {
	
	
	private Record record;
	
	/**
	 * 
	 * @param line Line of csv
	 */
	public Placemark(Record record) {
		this.record = record;
	}

	/**
	 * Print a placemark to printwriter.
	 * @param printWriter
	 */
	public void print(PrintWriter printWriter) {
		//print placemark element
		printWriter.println("<Placemark>");
		
		//print name element
		PlacemarkPrinter.printName(printWriter, record);
		
		//print description
		PlacemarkPrinter.printDescription(printWriter, record);
		
		//print point
		PlacemarkPrinter.printPoint(printWriter, record);
		
		//close placemark element
		printWriter.println("</Placemark>");
	}

}
