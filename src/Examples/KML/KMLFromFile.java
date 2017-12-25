package Examples.KML;

import java.io.File;

import CSVPckg.CSV;
import CSVPckg.CSVFactory;
import KML.KML;

public class KMLFromFile {
	public static void main(String[] args) throws Throwable {
		File file = new File("/MyWigle.csv");
		CSV csv = CSVFactory.getWigleCSVFromFile(file);
		KML kml = new KML(csv);
		
		File outKml = new File("/Example_KMLFromFile.kml");
		kml.generateKML(outKml);
	}
}
