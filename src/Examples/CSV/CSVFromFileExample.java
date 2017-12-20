package Examples.CSV;

import java.io.File;

import CSVPckg.CSV;
import CSVPckg.CSVFactory;
import Examples.IOUser;
import Examples.Paths;

public class CSVFromFileExample {
	public static void main(String[] args) throws Throwable {
		//example file, i chose this because it has 10 lines.
		File fileToConvert = IOUser.getFileByUser();
		CSV csv = CSVFactory.getCSVFromFile(fileToConvert);
		File outFile = (new File(Paths.OUT+"/myCoolCSV.csv"));
		csv.print();
		csv.writeToFile(outFile);
		IOUser.openFile(outFile);
	}


}
