package KML.Helper;
import java.io.PrintWriter;
import java.util.ArrayList;

import CSV.Helper.Record;
import CSV.Helper.Records;

public class Placemarks extends ArrayList<Placemark>{

	public Placemarks(Records records) {
		for(Record record : records) {
			add(new Placemark(record));
		}
	}
	
	public void printPlacemarks(PrintWriter printWriter) {
		for(Placemark placemark : this) {
			placemark.print(printWriter);
		}
	}

}
