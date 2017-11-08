import java.io.File;
import java.io.FileNotFoundException;

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
	public void sortBy_FirstSeen(String outFolder) {
		File outFile = new File(outFolder+OUT_NAME_SORTED);
		try {
			records.sortBy_FirstSeen(outFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problem writing to file.");
		}
	}

	public void print() {
		records.print();
	}

	

}
