package CSV;
import java.io.IOException;
import java.text.ParseException;

public class Start {

	/**
	 * data gathered by professor 
	 */
	private static final String PATH_FOLDER = "C:/Users/ShlomiPC/Desktop/data/28.10/";
	private static final String DESKTOP_PATH = 	"C:/Users/ShlomiPC/Desktop/";
	
	public static void main(String[] args) throws Exception {
		CSVFactory factory = new CSVFactory(PATH_FOLDER);
		CSV csv = factory.csv;
		//csv.sortBy_FirstSeen(PATH_OUT);
		csv.sortBy_RSSI(DESKTOP_PATH, true);
	}
}