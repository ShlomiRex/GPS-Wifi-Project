package Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Record extends ArrayList<Object>{


	public enum Field {
		//TODO: Complete
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
		convertStringArrayToObjectArray();
	}
	
	private void convertStringArrayToObjectArray() {
		//MAC(0) , SSID(1) , Auth(2), Type(10) is string
		//FirstSeen(3) is date
		//Channel(4) to Acc(9) are float
		//Type(10) is string
		
		//add mac, ssid, auth first(strings)
		add(origionalLine[0]);
		add(origionalLine[1]);
		add(origionalLine[2]);
		
		//add first seen (date)
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd HH:MM");
		Date date;
		try {
			date = formatter.parse(origionalLine[3]);
			add(date);
		} catch (ParseException e) {
			try {
				date = formatter2.parse(origionalLine[3]);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Failed to parse " + origionalLine[3] + " to date");
				add(""); //add nothing
			}

		}
		//add 4 to 9
		add(Float.parseFloat(origionalLine[4]));
		add(Float.parseFloat(origionalLine[5]));
		add(Float.parseFloat(origionalLine[6]));
		add(Float.parseFloat(origionalLine[7]));
		add(Float.parseFloat(origionalLine[8]));
		add(Float.parseFloat(origionalLine[9]));
		
		//add type
		add(origionalLine[10]);
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

	public void print() {
		System.out.println(line);
	}
	
}
