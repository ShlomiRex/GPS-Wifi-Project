package CSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import CSV.Record.GeoPoint;

public class CSV {
	public Records records;
	public ArrayList<File> filesInCSV;

	/**
	 * 
	 * @param records
	 *            Records that get from CSV Factory
	 */
	public CSV(Records records) {
		this.records = records;
		filesInCSV = new ArrayList<>();
	}

	/**
	 * 
	 * @param outFolder
	 *            Folder to output
	 * @throws FileNotFoundException
	 */
	public void sortBy_FirstSeen(String outFolder) throws FileNotFoundException {
		System.out.println("Sorting by Date and Time...");
		File outFile = new File(outFolder + "sortedBy_FirstSeen.csv");
		PrintWriter pw = new PrintWriter(outFile);
		Records.Sort.By_FirstSeen(pw, records);
		System.out.println("Sorted file at: " + outFile.getAbsolutePath());
	}

	/**
	 * Sort by RSSI (wifi signal)
	 * 
	 * @param outFolder
	 * @param sortBest10
	 *            If true, sort by RSSI then print 10 best to file. If false, sort
	 *            by RSSI then print all to file.
	 * @throws FileNotFoundException
	 */
	public void sortBy_RSSI(String outFolder, boolean sortBest10) throws FileNotFoundException {

		File outFile;
		if (sortBest10) {
			outFile = new File(outFolder + "sortedBy_RSSI_Best10.csv");
			System.out.println("Sorting by RSSI and best 10...");
		} else {
			outFile = new File(outFolder + "sortedBy_RSSI.csv");
			System.out.println("Sorting by RSSI...");
		}
		PrintWriter pw = new PrintWriter(outFile);
		Records.Sort.By_RSSI(pw, records, sortBest10);
		System.out.println("Sorted file at: " + outFile.getAbsolutePath());
	}

	public void sortBy_Location(String outFolder, GeoPoint topLeft, GeoPoint bottomRight) throws IOException {
		String pointsString = topLeft.toString() + bottomRight.toString();
		System.out.println("Sorting by location: " + pointsString);

		File outFile = new File(outFolder + "sortedBy_Location" + pointsString + ".csv");

		Records.Sort.By_Location(outFile, records, topLeft, bottomRight);

		System.out.println("Sorted file at: " + outFile.getAbsolutePath());
	}

	public void print() {
		records.print();
	}
}
