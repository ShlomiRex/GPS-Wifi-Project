package Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Record extends ArrayList<Object>{


	public enum Field {
		MAC(0), SSID(1), AuthMode(2), FirstSeen(3), Channel(4),
		RSSI(5), Lat(6), Lon(7), Alt(8), Acc(9), Type(10);
		
		public int column;
		
		Field(int column) {
			this.column = column;
		}
	}
	
	public String line;
	public String[] origionalLine;

	public Record(String[] s) {
		super();
		origionalLine = s;
		line = buffLine(s);
		//after record is created, create object array
		try {
			addToObjectArray();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Add the record String[] array to object[] array
	 * @throws ParseException 
	 */
	private void addToObjectArray() throws ParseException {
		//MAC(0) , SSID(1) , Auth(2), Type(10) is string
		//FirstSeen(3) is date
		//Channel(4) to Acc(9) are float
		//Type(10) is string
		
		//add mac, ssid, auth first(strings)
		add(origionalLine[0]);
		add(origionalLine[1]);
		add(origionalLine[2]);
		
		//add first seen (date)
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM");
		//SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd HH:MM");
		SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy/MM/dd HH:MM");
		Date date = formatter3.parse(origionalLine[3]);
		add(date);

		//add 4 to 9
		add(Float.parseFloat(origionalLine[4]));
		add(Float.parseFloat(origionalLine[5]));
		add(Float.parseFloat(origionalLine[6]));
		add(Float.parseFloat(origionalLine[7]));
		add(Float.parseFloat(origionalLine[8]));
		add(Float.parseFloat(origionalLine[9]));
		
		//add type
		add(origionalLine[10]);
		
		//check if any null
//		for(int i = 0; i <= 10; i++)
//			if(origionalLine[i] == null)
//				System.out.println("i = " + i);
	}


	/**
	 * 
	 * @param stringArr Array of strings
	 * @return String with each object of s is seperated by comma
	 */
	public static String buffLine(String[] s) {
		String result = "";
		for(int i = 0; i < s.length; i++) {
			result += s[i];
			result += ',';
		}
		return result;
	}

	/**
	 * 
	 * @param f An enum parameter from Record.Field that represents the chosen field
	 * @return The object of the field.
	 */
	public Object get_Field(Field f) {
		return get(f.column);
	}

	/**
	 * Print record.
	 */
	public void print() {
		System.out.println(line);
	}

	
}
