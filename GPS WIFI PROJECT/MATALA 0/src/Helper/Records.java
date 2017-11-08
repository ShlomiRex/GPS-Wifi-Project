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
				return o2Speed.compareTo(o1Speed);
			}
		});


		File outFile = new File(outPath);
		
		try {
			PrintWriter pw = new PrintWriter(outFile);
			//write wigle and header
			writeToFile_WigleAndHeader(pw);
			//write 10 best signals
			for(int i = 0; i < 10; i++) 
				pw.println(get(i).line);
			pw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	} //end sort

	public void print() {
		System.out.println(Record.buffLine(wigle));
		System.out.println(Record.buffLine(header));
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
	
	/**
	 * Write to end of file 2 lines: Wigle and then header.
	 * @param f
	 * @throws FileNotFoundException
	 */
	public void writeToFile_WigleAndHeader(PrintWriter pw) {
		String w = Record.buffLine(wigle);
		String h = Record.buffLine(header);
		pw.println(w);
		pw.println(h);
	}
	
	/**
	 * Writes to file Wigle, Header, Records
	 * @param file
	 * @throws FileNotFoundException
	 */
	public void writeToFile(PrintWriter pw) throws FileNotFoundException {
		writeToFile_WigleAndHeader(pw);
		for(Record r : this)
			pw.println(r.line);
		pw.close();
	}

}
