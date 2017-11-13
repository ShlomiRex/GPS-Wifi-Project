package KML;
import java.io.File;
import java.io.IOException;

import CSV.CSV;
import CSV.CSVFactory;

public class Start {

	/**
	 * data gathered by professor 
	 */
	private static final String CSV_FOLDER_PATH_IN = "C:/Users/ShlomiPC/Desktop/data/28.10/";
	private static final String KML_FILE_PATH_OUT = "C:/Users/ShlomiPC/Desktop/out.kml";
	
	public static void main(String[] args) throws Exception {
		CSVFactory csvFactory = new CSVFactory(CSV_FOLDER_PATH_IN);
		CSV csv = csvFactory.csv; //get csv
		
		//create kml factory given csv
		KMLFactory kmlFactory = new KMLFactory(csv);
		
		File output = new File(KML_FILE_PATH_OUT);
		//generate kml
		kmlFactory.generateKML(output);
	}
}