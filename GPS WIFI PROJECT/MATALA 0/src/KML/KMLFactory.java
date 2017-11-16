package KML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import CSV.CSV;

/**
 * Factory which its final product is KML file.
 */
public class KMLFactory {

	private Placemarks placemarks;

	public KMLFactory(CSV csv) {
		placemarks = new Placemarks(csv.records);
	}

	/**
	 * 
	 * @param outFile
	 *            Generate the KML file.
	 * @throws IOException
	 */
	public void generateKML(File outFile) throws IOException {
		System.out.println("Generating kml...");
		// Very important that PrintWriter will have auto flushing!
		// that was our problem in writing but now everything works
		PrintWriter printWriter = new PrintWriter(new FileWriter(outFile), true);
		KMLPrinter.printPlacemarks(printWriter, placemarks);
		System.out.println("KML file: " + outFile.getAbsolutePath());
	}
}