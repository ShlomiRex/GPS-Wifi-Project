import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;

import Helper.Record;
import Helper.Records;

public class CSV {
	public Records records;
	private static final String OUT_NAME_SORTED = "sortedByWifi.csv";

	/**
	 * Original file (The merged 1 big file)
	 */
	private File file;

	/**
	 * 
	 * @param origionalFile
	 *            The merged 1 big file
	 * @param records
	 *            Records that get from CSV Factory
	 */
	public CSV(File origionalFile, Records records) {
		this.file = origionalFile;
		this.records = records;
	}

	//test
	public void print1035To1100Records() {
		for(int i = 1035; i < 1100; i++) {
			records.get(i).print();
			System.out.println();
		}
	}

	/**
	 * 
	 * @param outFolder Folder to output
	 */
	public void sortByWifiSpeed(String outFolder) {
		records.sortByWifiSpeed(outFolder+OUT_NAME_SORTED);
	}

}
