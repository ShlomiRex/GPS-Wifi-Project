package Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Records do not contain: Wiggle line, Header line
 * Records only stores the actual data, not wrappers
 * @author ShlomiPC
 *
 */
public class Records extends ArrayList<Record> {

	public String[] wigle;
	public String[] header;
	
	public Records() {
		super();
	}
	
	public String[] getLine(int i) {
		return get(i).origionalLine;
	}
	
	/**
	 * 
	 * @param outPath Full path including file path
	 */
	public void sortByWifiSpeed(String outPath) {
		System.out.println("Sorting by RSSI...");
		sort(new Comparator<Record>() {

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
			for (Record r : this) {
				pw.println(r.line);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void print() {
		System.out.println("Printing wigle and header...");
		System.out.println(Record.buffLine(wigle));
		System.out.println(Record.buffLine(header));
		System.out.println("Printing data....");
		for(Record r : this) {
			r.print();
		}
	}
	
	
	public void printTestRSSI() {
		int i = 0;
		System.out.println(Record.Field.RSSI.column);
		for(Record r : this) {
			System.out.println("["+i+"] RSSI: " + r.get_Field(Record.Field.RSSI));
			i++;
		}
	}

}
