package main;
import java.io.File;
import java.io.FileNotFoundException;

import Helper.Records;

public class CSV {
	public Records records;
	private static final String OUT_NAME_SORTED = "sortedByWifi.csv";
//what'sup shlomy
	/**
	 * 
	 * @param records
	 *            Records that get from CSV Factory
	 */
	public CSV(Records records) {
		this.records = records;
	}
//asdfssdfasdasdasas
	/**\
	 * 
	 * @param outFolder Folder to output
	 */
	public void sortBy_FirstSeen(String outFolder) {
		//TODO: Add predicate
		File outFile = new File(outFolder+OUT_NAME_SORTED);
		try {
			records.sortBy_FirstSeen(outFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Problem writing to file.");
		}
	}

	public void print() {
		records.print();
	}

	

}
