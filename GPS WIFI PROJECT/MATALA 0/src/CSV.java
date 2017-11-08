import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;

import Helper.Record;
import Helper.Records;

public class CSV {
	public Records records;

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

	public void sortByWifiSpeed(String outPath) {
		System.out.println("Sorting by RSSI...");

		records.sort(new Comparator<Record>() {

			@Override
			public int compare(Record o1, Record o2) {
				Float o1Speed = (Float) o1.get_Field(Record.Field.RSSI);
				Float o2Speed = (Float) o2.get_Field(Record.Field.RSSI);
				return o1Speed.compareTo(o2Speed);
			}
		});

		File outFile = new File(outPath);
		try {
			PrintWriter pw = new PrintWriter(outFile);
			for (Record r : records) {
				pw.println(r.line);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void print1035To1100Records() {
		for(int i = 1035; i < 1100; i++) {
			records.get(i).print();
			System.out.println();
		}
	}

}
