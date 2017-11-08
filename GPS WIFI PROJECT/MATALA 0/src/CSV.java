import java.io.File;

import Helper.Record;
import Helper.Records;

public class CSV {
	public Records records;
	private static final String OUT_NAME_SORTED = "sortedByWifi.csv";

	/**
	 * 
	 * @param records
	 *            Records that get from CSV Factory
	 */
	public CSV(Records records) {
		this.records = records;
	}

	/**
	 * 
	 * @param outFolder Folder to output
	 */
	public void sortByWifiSpeed(String outFolder) {
		records.sortByWifiSpeed(outFolder+OUT_NAME_SORTED);
	}

	public void print() {
		records.print();
	}

	

}
