import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import CSV.Record;
import au.com.bytecode.opencsv.CSVReader;

public class CSVFactory {
	enum TypeOfFile {
		//BSSID;LAT;LON;SSID;Crypt;Beacon Interval;Connection Mode;Channel;RXL;Date;Time
		//11 variables.
		GMON_Export_Text;
	}
	
	//Writer to cvs text file.
	private PrintWriter writer;
	
	/**
	 * 
	 * @param file A file to get data from.
	 * @param outPath Absolute path to generated CSV file.
	 * @param outName Name of CSV file.
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public CSVFactory(File file, String outPath) throws IOException, ParseException {
		System.out.println("Reading file: " + file.getAbsolutePath());
		TypeOfFile type = TypeOfFile.GMON_Export_Text; //TODO: Change?
		writer = new PrintWriter(outPath, "UTF-8");
		
		if(type == TypeOfFile.GMON_Export_Text) {
			
			
			

			String fileName = "out.txt";
			CSVReader reader = new CSVReader(new FileReader(fileName), ';') ;
			String[] s = reader.readNext();
			ArrayList<Record> records = new ArrayList<>();

				while(s != null) {
					records.add(new Record(s));
					s=reader.readNext();
				}
				reader.close();
				
				for(Record r : records) {
					r.print();
				}
	        
			
			
			
			
			/*
			Scanner scanner = new Scanner(file);
			String line;
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				//System.out.println(s);
				writer.println(line);
			}
			scanner.close();
			writer.close();
			System.out.println("Output file: " +outPath);
			*/
		}
		
	}

	public static void main(String[] args) throws IOException, ParseException {
		//TODO: Change dynamically
		String path = "C:/Users/ShlomiPC/Desktop/gmon3/gmon/gmon_wlan.txt";
		String outPath = "C:/Users/ShlomiPC/Desktop/out.txt";
		File file = new File(path);
		new CSVFactory(file, outPath);
	}

}
