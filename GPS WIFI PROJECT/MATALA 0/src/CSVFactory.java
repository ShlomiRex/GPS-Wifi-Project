import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import Helper.DirectoryAndFileHelper;
import Helper.Record;
import Helper.Records;
import au.com.bytecode.opencsv.CSVReader;

public class CSVFactory {

	public final static String OUTNAME = "out.csv";
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
	private PrintWriter writer;
	private Records records;
	/**
	 * Result of factory
	 */
	public CSV csv;
	
	/**
	 * Header of the entier csv
	 */
	private String header;

	/**
	 * 
	 * @param file
	 *            Read this CSV/Text file
	 * @throws IOException
	 * @throws ParseException
	 */
	public CSVFactory(String folder, String outFolder) {
		File[] potentialFiles = DirectoryAndFileHelper.filesInFolder(folder);
		File[] validFiles = DirectoryAndFileHelper.findWigelFiles(potentialFiles);
		records = new Records();

		File out = new File(outFolder + OUTNAME);
		try {
			writer = new PrintWriter(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Problem with writer. Maybe check output path?");
			return;
		}
		readFiles(validFiles);
		System.out.println("Output path: " + outFolder + OUTNAME);
		System.out.println("Records size = " + records.size());
		csv = new CSV(out, records);
	}

	/**
	 * 
	 * @param reader
	 *            Reader of specific file
	 * @param records
	 *            To add data to records while merging
	 * @throws IOException
	 *             Problem reading the file
	 */
	private void mergeFiles(CSVReader reader) throws IOException {
		String[] s;
		// pass
		reader.readNext(); // Start line
		// pass
		reader.readNext(); // Header lines

		s = reader.readNext(); // Field lines
		Record r;
		while (s != null) {
			// write to merge file
			r = new Record(s);
			writer.println(r.line);
			records.add(new Record(s));

			s = reader.readNext();
		}
	}

	/**
	 * Read files one by one and merge data into 1 big file
	 * 
	 * @param files
	 *            Files to read
	 * @throws FileNotFoundException
	 *             Writer problem
	 * @throws UnsupportedEncodingException
	 *             Writer problem
	 */
	private void readFiles(File[] files) {
		File f = null;
		CSVReader reader;
		try {
			f = files[0];
			writeHeader(f);
		} catch (IOException e1) {
			System.out.println("Problem reading " + f.getAbsolutePath());
			System.out.println("Cannot read header! Exiting");
			return;
		} // Write header only once
		for (int i = 0; i < files.length; i++) {
			try {
				f = files[i];
				System.out.println("Reading file: " + f.getAbsolutePath());
				reader = new CSVReader(new FileReader(f), SEPERATOR);
				System.out.println("Merging...");
				mergeFiles(reader);

			} catch (IOException e) {
				System.out.println("Problem reading " + f.getAbsolutePath());
			}
		}
	}

	/**
	 * This function is called once, when the readFiles() is called, it adds the
	 * header line to csv
	 * 
	 * @param file
	 *            First file
	 * @param reader
	 * @throws IOException
	 */
	private final void writeHeader(File file) throws IOException {
		if (file == null)
			return;
		CSVReader reader = new CSVReader(new FileReader(file), SEPERATOR);
		reader.readNext();
		String[] s = reader.readNext();
		header = Record.buffLine(s);
		writer.println(header);
		reader.close();
	}

}
