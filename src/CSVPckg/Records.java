package CSVPckg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import CSVPckg.Record.Field;
import CSVPckg.Record.GeoPoint;
import au.com.bytecode.opencsv.CSVWriter;

public class Records extends ArrayList<Record> {
	public Records() {
		super();
	}

	/**
	 * Prints the records as text of CSVPckg nice formatted.
	 */
	public void print() {
		for (Record r : this) {
			r.print();
		}
	}

	/**
	 * Writes to this class data. Writes: Wigle, Headers, Records.
	 * @throws IOException 
	 */
	public void writeData(CSVWriter writer) throws IOException {
		Writer.writeRecordsLines(writer, this);
		writer.close();
	}

	/**
	 * Contains general methods to sort records.
	 * 
	 * @author ShlomiPC
	 *
	 */
	public static abstract class Sort {
		/**
		 * Sort records by given rectangle. Each point inside that rectangle area will
		 * be written, any points outside that rectangle area will be ignored.
		 * 
		 * @param topLeft
		 *            Top left geo point.
		 * @param bottomRight
		 *            Bottom right geo point.
		 * @throws IOException 
		 */
		public static void By_Location(File csvFile, Records records, GeoPoint topLeft, GeoPoint bottomRight)throws IOException {
			//ArrayList<String[]> wigleAndHeader = CSVFactory.getWigleAndHeaderLines(csvFile);
			Records recordsContainingOnlyPointsInsideRectangle = new Records();
			//recordsContainingOnlyPointsInsideRectangle.wigle = wigleAndHeader.get(0);
			//recordsContainingOnlyPointsInsideRectangle.header = wigleAndHeader.get(1);
			
			//System.out.println(recordsContainingOnlyPointsInsideRectangle.wigle);;
			
			CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
			
			//TODO: Create class of wigle and header 
			Float recordLat, recordLon;
			for (Record record : records) {
				recordLat = (Float) record.get_Field(Field.Lat);
				recordLon = (Float) record.get_Field(Field.Lon);
				if (recordLat >= topLeft.latitude && recordLat <= bottomRight.latitude
						&& recordLon >= topLeft.longtitude && recordLon <= bottomRight.longtitude) {
					recordsContainingOnlyPointsInsideRectangle.add(record);
				}
			}

			// write all records
			Writer.writeRecords(writer, recordsContainingOnlyPointsInsideRectangle);
			writer.close();
		}

		public static void By_FirstSeen(CSVWriter writer, Records records) throws IOException {
			records.sort(new Comparator<Record>() {

				@Override
				public int compare(Record o1, Record o2) {
					Date o1Date = (Date) o1.get_Field(Field.FirstSeen);
					Date o2Date = (Date) o2.get_Field(Field.FirstSeen);

					return o2Date.compareTo(o1Date);
				}
			});
			// write all records
			Writer.writeRecords(writer, records);
			writer.close();
		}

		/**
		 * Sort by RSSI.
		 * @param writer
		 * @param records
		 * @param sortBest10 
		 * @throws IOException
		 */
		public static void By_RSSI(CSVWriter writer, Records records, boolean sortBest10) throws IOException {
			// actual sort
			records.sort(new Comparator<Record>() {

				@Override
				public int compare(Record o1, Record o2) {
					Float o1Speed = (Float) o1.get_Field(Field.RSSI);
					Float o2Speed = (Float) o2.get_Field(Field.RSSI);
					return o2Speed.compareTo(o1Speed);
				}
			});

			// writeToFile_WigleAndHeader(pw);

			if (sortBest10) {
				// write 10 best records
				for (int i = 0; i < 10; i++) {
					writer.writeNext(records.get(i).lineAsArrayOfStrings);
				}
			} else {
				// write ALL records
				Writer.writeRecordsLines(writer, records);
			}
			writer.close();
		} // end sort rssi

	} // Sorted class

	/**
	 * Contains general methods to write to file.
	 * 
	 * @author ShlomiPC
	 *
	 */
	public static abstract class Writer {
		/**
		 * Writes to file this record class data. <br>
		 * Writes: Wigle, Headers, Records.
		 * 
		 *            A writer to write to.
		 * @param recordsToReadFrom
		 *            Records class to write from.
		 */
		public static void writeRecords(CSVWriter writer, Records recordsToReadFrom) throws FileNotFoundException {
			writeRecordsLines(writer, recordsToReadFrom);
		}



		/**
		 * Writes to file this record class data. <br>
		 * Writes: Records.
		 * 
		 * @param writer
		 *            A writer to write to.
		 */
		public static void writeRecordsLines(CSVWriter writer, Records records) {
			for (Record record : records) {
				writer.writeNext(record.lineAsArrayOfStrings);
			}
		}

	}// Writer class

} // Records class
