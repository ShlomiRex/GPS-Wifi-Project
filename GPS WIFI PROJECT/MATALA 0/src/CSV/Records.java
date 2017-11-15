package CSV;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import CSV.Record.Field;
import CSV.Record.GeoPoint;

public class Records extends ArrayList<Record> {

	public String[] wigle;
	public String[] header;

	public Records() {
		super();
	}

	/**
	 * Prints the records as text of CSV nice formatted.
	 */
	public void print() {
		System.out.println(Record.getFullString(wigle));
		System.out.println(Record.getFullString(header));
		for (Record r : this) {
			r.print();
		}
	}

	/**
	 * Writes to this class data. Writes: Wigle, Headers, Records.
	 */
	public void writeData(PrintWriter pw) throws FileNotFoundException {
		Writer.writeWigleAndHeader(pw, this);
		Writer.writeRecordsLines(pw, this);
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
		 */
		public static void By_Location(PrintWriter pw, Records records, GeoPoint topLeft, GeoPoint bottomRight)
				throws FileNotFoundException {
			Records recordsContainingOnlyPointsInsideRectangle = new Records();
			Float recordLat, recordLon;
			for (Record record : records) {
				recordLat = (Float) record.get_Field(Field.Lat);
				recordLon = (Float) record.get_Field(Field.Lon);
				if (recordLat >= topLeft.latitude && recordLat <= bottomRight.latitude
						&& recordLon >= topLeft.longtitude && recordLon <= bottomRight.longtitude) {
					recordsContainingOnlyPointsInsideRectangle.add(record);
					System.out.println("Adding");
				}
			}

			// write wigle and header
			Writer.writeWigleAndHeader(pw, recordsContainingOnlyPointsInsideRectangle);
			// write all records
			Writer.writeRecords(pw, recordsContainingOnlyPointsInsideRectangle);
			pw.close();
		}

		public static void By_FirstSeen(PrintWriter pw, Records records) throws FileNotFoundException {
			records.sort(new Comparator<Record>() {

				@Override
				public int compare(Record o1, Record o2) {
					Date o1Date = (Date) o1.get_Field(Record.Field.FirstSeen);
					Date o2Date = (Date) o2.get_Field(Record.Field.FirstSeen);

					return o2Date.compareTo(o1Date);
				}
			});
			// write all records
			Writer.writeRecords(pw, records);
			pw.close();
		}

		public static void By_RSSI(PrintWriter pw, Records records, boolean sortBest10) {
			System.out.println("Sorting by RSSI...");

			// actual sort
			records.sort(new Comparator<Record>() {

				@Override
				public int compare(Record o1, Record o2) {
					Float o1Speed = (Float) o1.get_Field(Record.Field.RSSI);
					Float o2Speed = (Float) o2.get_Field(Record.Field.RSSI);
					return o2Speed.compareTo(o1Speed);
				}
			});

			// write wigle and header
			Writer.writeWigleAndHeader(pw, records);
			// writeToFile_WigleAndHeader(pw);

			if (sortBest10) {
				// write 10 best records
				for (int i = 0; i < 10; i++) {
					pw.println(records.get(i).line);
				}
			} else {
				// write ALL records
				Writer.writeRecordsLines(pw, records);
			}
			pw.close();
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
		 * @param pw
		 *            A writer to write to.
		 * @param recordsToReadFrom
		 *            Records class to write from.
		 */
		public static void writeRecords(PrintWriter pw, Records recordsToReadFrom) throws FileNotFoundException {
			writeWigleAndHeader(pw, recordsToReadFrom);
			writeRecordsLines(pw, recordsToReadFrom);
		}

		/**
		 * Writes to file 2 lines: Wigle and Header.
		 * 
		 * @param pw
		 *            PrintWriter stream.
		 * @param recordsToReadFrom
		 *            Records class to read data from.
		 */
		public static void writeWigleAndHeader(PrintWriter pw, Records recordsToReadFrom) {
			System.out.println("Wigle = " + Arrays.toString(recordsToReadFrom.wigle));
			String w = Record.getFullString(recordsToReadFrom.wigle);
			String h = Record.getFullString(recordsToReadFrom.header);
			pw.println(w);
			pw.println(h);
		}

		/**
		 * Writes to file this record class data. <br>
		 * Writes: Records.
		 * 
		 * @param pw
		 *            A writer to write to.
		 * @param recordsToReadFrom
		 *            Records class to write from.
		 */
		public static void writeRecordsLines(PrintWriter pw, Records records) {
			for (Record record : records) {
				pw.println(record.line);
			}
		}

	}// Writer class

} // Records class
