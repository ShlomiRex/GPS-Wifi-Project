package CSV;

import java.text.ParseException;
import java.util.Arrays;

public class Record {
	

	/**
	 * Line that represents the entire record
	 */
	private String[] line;
	private Fields fields;
	
	/**
	 * Please make sure that the Record line is not the head line!
	 * @param line
	 * @throws ParseException
	 */
	public Record(String[] line) throws ParseException {
		this.line = line;
		fields = new Fields(this);
	}
	
	/**
	 * 
	 * @param column Index of column: BSSID = 0, LAT = 1... TIME = 10
	 * @return
	 */
	public String get_Field(int column) {
		if(column < 0 || column > line.length-1) {
			return null;
		}
		
		return line[column];
	}

	public void print() {
		System.out.println(Arrays.toString(line));
	}
}
