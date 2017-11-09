package KML.Helper;
import java.io.PrintWriter;

import CSV.Helper.Record;

public abstract class KMLPrinter {
	/**
	 * Prints all the KML.
	 * @param printWriter
	 */
	public static void print(PrintWriter printWriter, Placemarks placemarks) {
		//header and kml
		KMLPrinter.printKMLHeader(printWriter);
		
		//document
		KMLPrinter.printKMLDocument(printWriter, placemarks);
		//close document
		printWriter.println("</Document>");
		
		
		//close header
		System.out.println("printing kml");
		printWriter.println("</kml>");
	}
	
	private static void printKMLHeader(PrintWriter printWriter) {
		String s1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		String s2 = "<kml xmlns=\"http://www.opengis.net/kml/2.2\">";
		printWriter.println(s1);
		printWriter.println(s2);
	}
	
	private static void printKMLDocument(PrintWriter printWriter, Placemarks placemarks) {
		
		//open element
		printWriter.println("<Document>");
		
		//Write 3 styles
		String s1 = "<Style id=\"red\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style>";
		String s2 = "<Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style>";
		String s3 =	"<Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style>";
		
		printWriter.println(s1);	
		printWriter.println(s2);
		printWriter.println(s3);
		
		//print folder
		printFolder(printWriter, placemarks);
		
		//close element
		printWriter.println("</Document>");
	}


	private static void printFolder(PrintWriter printWriter, Placemarks placemarks) {
		//print name
		String s1 = "<Folder><name>Wifi Networks</name>";
		//TODO: CLOSE FOLDER ELEMENT
		printWriter.println(s1);
		
		//print all placemarks
		placemarks.printPlacemarks(printWriter);
		
		//close folder
		printWriter.println("</Folder>");
	}

}
