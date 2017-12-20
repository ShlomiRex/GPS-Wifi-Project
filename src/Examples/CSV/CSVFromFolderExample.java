package Examples.CSV;

import java.io.File;
import java.util.List;

import CSVPckg.CSV;
import CSVPckg.CSVFactory;
import Examples.IOUser;
import Examples.Paths;

public class CSVFromFolderExample {
	public static void main(String[] args) throws Throwable {
		File folder = IOUser.getFolderByUser();
		CSVFactory factory = new CSVFactory(folder);
		List<CSV> csvList = factory.csvList; //get the csv list.
		
		
		File outputFolder = new File(Paths.OUT+"/");
		File outFile;
		//write each file in folder to spesific place with names.
		for(int i = 0; i < csvList.size(); i++) {
			System.out.println();
			outFile = new File(outputFolder.getAbsolutePath()+"/csvWrite_num_"+i+".csv");
			System.out.println("Creating: " + outFile.getAbsolutePath());
			csvList.get(i).writeToFile(outFile);
		}
	}
}
