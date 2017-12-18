package Examples.CSV;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import CSV.CSV;
import CSV.CSVFactory;
import Examples.IOUser;
import Examples.Paths;

import javax.swing.*;

public class CSVFromFileExample {
	public static void main(String[] args) throws Throwable {
		//example file, i chose this because it has 10 lines.
		File fileToConvert = IOUser.getFileByUser();
		CSV csv = CSVFactory.getCSVFromFile(fileToConvert);
		File outFile = (new File(Paths.OUT+"/myCoolCSV.csv"));

		csv.writeToFile(outFile);
		IOUser.openFile(outFile);
	}


}
