package KML;

import java.io.File;
import CSV.CSV;
import CSV.CSVFactory;

public class Start {

	/**
	 * data gathered by professor, folder of files of csv
	 */
	private static final String CSV_FOLDER_PATH_IN = "C:/Users/ShlomiPC/Desktop/abc";
	private static final String KML_FILE_PATH_OUT = "C:/Users/ShlomiPC/Desktop/out.kml";

	public static void main(String[] args) throws Exception {
		//TODO: In csv factory, insted of folder, do file path and return csv.
		CSVFactory csvFactory = new CSVFactory(CSV_FOLDER_PATH_IN); //read folder
		CSV csv = csvFactory.csv; // get csv
		
		// create kml factory by given csv
		KMLFactory kmlFactory = new KMLFactory(csv);

		File output = new File(KML_FILE_PATH_OUT);
		// generate kml
		kmlFactory.generateKML(output);
	}
}