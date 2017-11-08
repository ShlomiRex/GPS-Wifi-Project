package Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Helper.Record;
import au.com.bytecode.opencsv.CSVReader;

public class TestCSVReaderLibrary {
	
	public TestCSVReaderLibrary() throws IOException {
		String path = "C:/Users/ShlomiPC/Desktop/wigle/WigleWifi_20171102120619.csv";
		File f = new File(path);
		CSVReader reader = new CSVReader(new FileReader(f), ',');
		String[] s1 = reader.readNext();
		String[] s2 = reader.readNext();
		String[] s3 = reader.readNext();
		String[] s4 = reader.readNext();
		
		System.out.println(Record.buffLine(s1));
		System.out.println(Record.buffLine(s2));
		System.out.println(Record.buffLine(s3));
		System.out.println(Record.buffLine(s4));
		
		PrintWriter w = new PrintWriter("C:/Users/ShlomiPC/Desktop/out.csv" , "UTF-8");
		w.println(Record.buffLine(s1));
		w.println(Record.buffLine(s2));
		w.println(Record.buffLine(s3));
		w.println(Record.buffLine(s4));
		
		w.println("LOL");
	}
	
	public static void main(String[] args) throws IOException {
		new TestCSVReaderLibrary();
	}

}
