package CSV;
import java.io.File;
import java.io.FileNotFoundException;

import CSV.Helper.Records;

public class CSV {
	public Records records;

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
		File outFile = new File(outFolder+"sortedBy_FirstSeen.csv");
		try {
			records.sortBy_FirstSeen(outFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Problem writing to file.");
		}
		System.out.println("Sorted file at: " + outFile.getAbsolutePath());
	}
	
	/**
	 * Sort by RSSI (wifi signal)
	 * @param outFolder
	 * @param sortBest10 If true, sort by RSSI then print 10 best to file.
	 * 					If false, sort by RSSI then print all to file.
	 */
	public void sortBy_RSSI(String outFolder, boolean sortBest10) {
		File outFile = new File(outFolder+"sortedBy_RSSI.csv");
		try {
			records.sortBy_RSSI(outFile, sortBest10);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Problem writing to file.");
		}
		System.out.println("Sorted file at: " + outFile.getAbsolutePath());
	}

	public void print() {
		records.print();
	}
}
