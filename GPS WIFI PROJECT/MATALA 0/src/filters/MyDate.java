package Filterx.Time;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import au.com.bytecode.opencsv.CSVReader;

public class MyDate {
	private Date date;
	public MyDate(String dateString) throws ParseException {
		String string = "2017/10/2";
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		date = format.parse(string);
		System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010
	}
	
	public static void main(String[] args) throws ParseException {
		new MyDate("");

		
	}
}
