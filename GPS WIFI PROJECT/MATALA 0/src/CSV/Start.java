package CSV;

import CSV.Record.GeoPoint;

public class Start {

	/**
	 * data gathered by professor
	 */
	private static final String PATH_FOLDER = "C:\\Users\\ShlomiPC\\Desktop\\Raw Data(For testing)\\28.10";
	private static final String DESKTOP_PATH = "C:/Users/ShlomiPC/Desktop/";

	public static void main(String[] args) throws Exception {
		CSVFactory factory = new CSVFactory(PATH_FOLDER);
		CSV csv = factory.csv;
		// csv.sortBy_FirstSeen(DESKTOP_PATH); //works
		// csv.sortBy_RSSI(DESKTOP_PATH, true); //works

		GeoPoint topLeft = new GeoPoint(32.75128, 34.502776);
		GeoPoint bottomRight = new GeoPoint(32.051869, 34.524945);

		csv.sortBy_Location(DESKTOP_PATH, topLeft, bottomRight);
	}
}