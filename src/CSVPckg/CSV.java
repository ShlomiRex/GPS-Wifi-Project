package CSVPckg;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import CSVPckg.Record.Field;
import CSVPckg.Record.GeoPoint;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CSV {

	public Records records;
	public File csvFile;
	public CSVHeaders headers = null;

	public CSV(File fileToConvert) throws Throwable {
		System.out.println("Creating CSV : Reading file: " + fileToConvert.getAbsolutePath());
		if(fileToConvert == null)
			throw new NullPointerException("File to convert is null.");

		csvFile = fileToConvert;
		
		//read file
		CSVReader csvReader = new CSVReader(new FileReader(fileToConvert));
		List<String[]> lines = csvReader.readAll();
		csvReader.close();
		
		checkValidCSV(fileToConvert, lines);

		
		records = new Records();

		setup(lines);

		System.out.println("Reading done. CSV created.");
	}

	/**
	 * Initialize headers and data.
	 */
	private void setup(List<String[]> lines) throws ParseException {
		String[] wigle = null, field = null;
		if(lines.get(0)[0].contains("Wigle")) {
			wigle = lines.get(0);
		}
		if(lines.get(1)[0].toUpperCase().contains("MAC")) {
			field = lines.get(1);
		}
		headers = new CSVHeaders(wigle, field, this);

		int lineOfDataStart;
		if(wigle == null && field == null)
			lineOfDataStart = 0;
		else if( (wigle == null && field != null) || (wigle != null && field == null))
			lineOfDataStart = 1;
		else
			lineOfDataStart = 2;

		for(int i = lineOfDataStart; i < lines.size(); i++) {
			records.add(new Record(lines.get(i)));
		}
	}

	private void checkValidCSV(File fileToConvert, List<String[]> lines) throws Throwable {
		checkIfFileIsWigleCSV: {
			String fileName = fileToConvert.getName();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			if(! extension.equals("csv")) {
				throw new Throwable("File is not csv. File: " + fileName);
			}
		}

//		checkIfFileHasWigleHeader: {
//			if(! lines.get(0)[0].contains("Wigle")) {
//				System.out.println("Not wigle");
//				throw new Throwable("This is not valid wigle file.");
//			}
//		}

		otherChecks: {
			if(lines.size() <= 1)
				throw new Throwable("File has only 1 line!");
		}

	}

	/**
	 * 
	 * @param outFolder
	 *            Folder to output
	 * @throws IOException 
	 */
	public void sortBy_FirstSeen(String outFolder) throws IOException {
		System.out.println("Sorting: " + this.csvFile.getAbsolutePath());
		System.out.println("Sorting by Date and Time...");
		File outFile = new File(outFolder + "sortedBy_FirstSeen.csv");
		CSVWriter writer = new CSVWriter(new FileWriter(outFile));
		Records.Sort.By_FirstSeen(writer, records);
		System.out.println("Sorted file at: " + outFile.getAbsolutePath());
	}

	/**
	 * Sort by RSSI (wifi signal)
	 * 
	 * @param outFolder
	 * @param sortBest10
	 *            If true, sort and print best 10. 
	 *            If false, sort a
	 * @throws IOException 
	 */
	public List<Float> sortBy_RSSI(String outFolder, boolean sortBest10) throws IOException {

		File outFile;
		if (sortBest10) 
			outFile = new File(outFolder + "sortedBy_RSSI_Best10.csv");
		 else 
			outFile = new File(outFolder + "sortedBy_RSSI.csv");
		
		CSVWriter writer = new CSVWriter(new FileWriter(outFile));
		System.out.println("Sorting by RSSI...");
		Records.Sort.By_RSSI(writer, records, sortBest10);
		System.out.println("Sorted file at: " + outFile.getAbsolutePath());
		
		List<Float> best10RSSI = new ArrayList<>();
		for(int i = 0; i < records.size(); i++) {
			best10RSSI.add(new Float((float) records.get(i).get_Field(Field.RSSI)));
		}
		return best10RSSI;
	}
	

	public void sortBy_Location(String outFolder, GeoPoint topLeft, GeoPoint bottomRight) throws IOException {
		String pointsString = topLeft.toString() + bottomRight.toString();
		System.out.println("Sorting by location: " + pointsString);

		File outFile = new File(outFolder + "sortedBy_Location" + pointsString + ".csv");
		Records.Sort.By_Location(outFile, records, topLeft, bottomRight);

		System.out.println("Sorted file at: " + outFile.getAbsolutePath());
	}

	public void print() {
		records.print();
		System.out.println("Total lines: " + records.size());
	}
	
	public void writeToFile(File file) throws IOException {
		System.out.println("Writing CSVPckg to file: " + file.getAbsolutePath());
		CSVWriter writer = new CSVWriter(new FileWriter(file));
		headers.writeData(writer);
		records.writeData(writer);
		System.out.println("Writing done.");
	}
	/**
	 * Writes to file 2 lines: Wigle and Header.
	 *
	 * @param writer
	 *            PrintWriter stream.
	 * @param recordsToReadFrom
	 *            Records class to read data from.
	 */
	public void writeWigleAndHeader(CSVWriter writer, Records recordsToReadFrom) {
		writer.writeNext(this.headers.wigleHeader);
		writer.writeNext(this.headers.fieldHeader);
	}

	public void Matala0_Question2_Write(File fileToWriteTo) {

	}

	public ArrayList< ArrayList<Record> > Matala0_Question2_GetComboRecords() {
		//SORT BY:
		//TIME, ID, LAT, LON, ALT

		//TIME is already sorted
		//ID is the same for file
		//LAT , LON should be same for next line. If not, then don't add.

		ArrayList<Record> sameLineRecords = new ArrayList<>();
		ArrayList<ArrayList<Record>> result = new ArrayList<>();

		Record r1, r2;
		for(int i = 0; i < records.size()-1; i++) {

		    r1 = records.get(i);
		    r2 = records.get(i+1);

			sameLineRecords.add(r1);

			while(isSameLine(r1,r2) == true) {
				sameLineRecords.add(r2);
				i++;
				r2 = records.get(i+1);
			}
			result.add(sameLineRecords);
			sameLineRecords = new ArrayList<>();
		}

		return result;
	}


	/**
	 * @since Matala 0 Question 2
	 * @param r1
	 * @param r2
	 * @return True if r2 and r1 should be in the same line as described
	 * in Matala 0 Question 2 (Same TIME, ID, Lat, Lon, Alt)
	 */
	public static boolean isSameLine(Record r1, Record r2) {
		//TIME is already sorted
		//ID is the same for file
		//LAT , LON should be same for next line. If not, then don't add.
		//System.out.println("Comparing:");
		//r1.print();
		//r2.print();

		if(Field.compareFields(r1, r2, Field.FirstSeen) != 0) {
			//System.out.println("First seen incorrect.");
			return false;
		}
		if(Field.compareFields(r1, r2, Field.Lat) != 0) {

			//System.out.println("Lat incorrect.");
			return false;
		}
		if(Field.compareFields(r1, r2, Field.Lon) != 0) {
			//System.err.println("Lon incorrect.");
			return false;
		}
		if(Field.compareFields(r1, r2, Field.Alt) != 0) {
			//System.out.println("Alt incorrect.");
			return false;
		}
        //System.out.println("SAME LINE");
		return true;
	}

	@Override
    public String toString() {
	    return "[File, Lines] [" + csvFile.getName() + " , " + records.size() +"]";
    }


    public void printHeaders() {
	    headers.print();
    }
}
