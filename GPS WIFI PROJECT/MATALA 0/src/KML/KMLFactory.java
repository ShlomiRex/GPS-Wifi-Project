package KML;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import CSV.CSV;
import KML.Helper.KMLPrinter;
import KML.Helper.Placemarks;

public class KMLFactory {

	private Placemarks placemarks;
	
	public KMLFactory(CSV csv) {
		placemarks = new Placemarks(csv.records);
	}

	/**
	 * 
	 * @param outFile Generate the KML file.
	 * @throws FileNotFoundException Cannot write.
	 */
	public void generateKML(File outFile) throws FileNotFoundException {
		System.out.println("Generating kml...");
		PrintWriter printWriter = new PrintWriter(outFile);
		KMLPrinter.print(printWriter, placemarks);
		System.out.println("KML file: " + outFile.getAbsolutePath());
	}
}