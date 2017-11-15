package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

import Helper.DirectoryAndFileHelper;
import Helper.Record;
import Helper.Records;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CSVFactory {

	/**
	 * Inside wigle file, sepearator can be ; or , and ect
	 */
	public final static char SEPERATOR = ',';

	/**
	 * Starting line is Wigle and stuff
	 */
	public final static int STARTING_LINE = 0;
	/**
	 * Header line is the fields names
	 */
	public final static int HEADER_LINE = 1;
	/**
	 * From this index including, the actual information of the csv
	 */
	public final static int FIELDS_LINE = 2;

	// Writer to cvs text file.
	//private PrintWriter writer;
	private Records records;
	/**
	 * Result of factory
	 */
	public CSV csv;

	/**
	 * 
	 * @param file
	 *            Read this CSV/Text file
	 * @throws IOException
	 * @throws ParseException
	 */
	public CSVFactory(String folder, String outFolder) throws IOException {
		//Potential files to read from directory
		File[] potentialFiles = DirectoryAndFileHelper.filesInFolder(folder);
		//Actual files that will be read from directory
		File[] validFiles = DirectoryAndFileHelper.findWigelFiles(potentialFiles);
		records = new Records();
		
		//File out_MergedFile = new File(outFolder + OUTNAME);
		//writer = new PrintWriter(out_MergedFile);
		readFiles(validFiles);
		System.out.println("Records size = " + records.size());
		csv = new CSV(records);
	}
	
	public void foo() throws FileNotFoundException {
		String filePath = "";
		File file = new File(filePath);
		PrintWriter pw = new PrintWriter(file);
		CSVWriter csvWriter = new CSVWriter(pw, SEPERATOR);
		//TODO: Use CSVWriter insted of @ShlomiPC code
	}


	/**
	 * Read files one by one and put data in records
	 * 
	 * @param files
	 *            Files to read
	 * @throws IOException 
	 * @throws FileNotFoundException
	 *             Writer problem
	 * @throws UnsupportedEncodingException
	 *             Writer problem
	 */
	private void readFiles(File[] files) throws IOException {
		File f = files[0];
		CSVReader reader;
		ArrayList<String[]> wigle_And_header = getWigleAndHeaderLines(f);
		records.wigle = wigle_And_header.get(0);
		records.header = wigle_And_header.get(1);
		
		for (int i = 0; i < files.length; i++) {
			try {
				f = files[i];
				System.out.println("Reading file: " + f.getAbsolutePath());
				reader = new CSVReader(new FileReader(f), SEPERATOR);
				
				
				String[] s;
				// pass
				reader.readNext(); // Start line
				// pass
				reader.readNext(); // Header lines

				s = reader.readNext(); // Field lines
				//for each field line
				while (s != null) {
					records.add(new Record(s));
					s = reader.readNext();
				}
				
				reader.close();
				//next file
			} catch (IOException e) {
				System.out.println("Problem reading " + f.getAbsolutePath());
				//moving to next file..
			}
		}
	}
	
	/**
	 * 
	 * @param f Not null file
	 * @return [0] is line of wigle, [1] is line of header,
	 * @throws IOException
	 */
	private ArrayList<String[]> getWigleAndHeaderLines(File f) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(f), SEPERATOR);
		String[] wigle = reader.readNext(); 
		String[] header = reader.readNext();
		reader.close();
		ArrayList<String[]> result = new ArrayList<>();
		result.add(wigle);
		result.add(header);
		return result;
	}
}