package main;
import java.io.IOException;
import java.text.ParseException;

public class Start {

	/**
	 * data gathered by professor 
	 */
	private static final String PATH_FOLDER = "C:/Users/ShlomiPC/Desktop/data/28.10/";
	private static final String PATH_FOLDER2 = "C:/Users/ShlomiPC/Desktop/wigle/";
	private static final String PATH_OUT = 	"C:/Users/ShlomiPC/Desktop/";
	
	public static void main(String[] args) throws IOException, ParseException {
		CSVFactory factory = new CSVFactory(PATH_FOLDER, PATH_OUT);
		CSV csv = factory.csv;
		csv.sortBy_FirstSeen(PATH_OUT);
		
	}

}
