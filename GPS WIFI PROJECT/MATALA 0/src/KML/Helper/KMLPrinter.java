package KML.Helper;
import java.io.PrintWriter;

/**
 * Helper class to print strings to kml file.
 * @author ShlomiPC
 *
 */
public abstract class KMLPrinter {
	/**
	 * Prints all placemarks to writer.
	 * @param printWriter
	 * @param placemarks
	 */
	public static void printPlacemarks(PrintWriter printWriter, Placemarks placemarks) {
		//header and kml
		String s1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		printWriter.println(s1);
		
		//kml element open
		String s2 = "<kml xmlns=\"http://www.opengis.net/kml/2.2\">";
		printWriter.println(s2);
		
		//document
		KMLPrinter.printKMLDocument(printWriter, placemarks);
		
		//kml element close
		printWriter.println("</kml>");
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
