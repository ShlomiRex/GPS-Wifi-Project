package Helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import Helper.Record.Field;

public class Records extends ArrayList<Record> {

	public Records() {
		super();
	}
	
	public String[] getLine(int i) {
		return get(i).origionalLine;
	}
	
//	public void sort_ByFirstSeen() {
//		get(0).get_Field(Field.FirstSeen);
//		super.sort(new Comparator<Record>() {
//			@Override
//			public int compare(Record o1, Record o2) {
//				Date date1 = (Date) o1.get_Field(Field.FirstSeen);
//				
//			}
//		});
//		Date date = new Date();
//	}

}
