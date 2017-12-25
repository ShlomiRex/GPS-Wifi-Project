package Examples.CSV;

import java.io.File;
import java.util.List;

import CSVPckg.CSV;
import CSVPckg.CSVFactory;

public class SortExample {
	public static void main(String[] args) throws Throwable {
		File folder = new File("/");
		List<CSV> csvList = csvList = CSVFactory.getCSVsFromFolder(folder);

		CSV csv = csvList.get(0);
		if(csv == null) 
			throw new Throwable("Please check file.");
		
		csv.sortBy_RSSI("/", false);
	}
}
