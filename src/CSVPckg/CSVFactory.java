package CSVPckg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CSVFactory {
	
	public File folder;
	public ArrayList<File> allFilesInFolder;
	public List<CSV> csvList;
	
	/**
	 * Read all files in folder and subfolders, and put them in csvList.
	 * @param folder Read from this folder and all subfolders - all files.
	 * @throws Throwable
	 */
	public CSVFactory(String folder) throws Throwable {
		this.folder = new File(folder);
		System.out.println("Reading from folder: " + this.folder.getAbsolutePath());
		if(this.folder == null || this.folder.exists() == false)
			throw new Throwable("Folder is not valid! : " + this.folder.getAbsolutePath() );
		allFilesInFolder = new ArrayList<>();
		listf(folder, allFilesInFolder);
		csvList = getCSVFiles(allFilesInFolder);
		System.out.println("Done reading.");
		System.out.println("Total files in folder: " + allFilesInFolder.size());
		System.out.println("Total valid csv files: " + csvList.size());
	}

	public CSVFactory(File folder) throws Throwable {
		this(folder.getAbsolutePath());
	}

	/**
	 *
	 * @param file
	 * @return csv object by given file. Returns null if file is not valid wigle csv.
	 */
	public static CSV getCSVFromFile(File file) {
		List<File> lst = new ArrayList<>();
		lst.add(file);
		List<CSV> lstNew = getCSVFiles(lst);
		if(lstNew == null || lstNew.isEmpty())
			return null;
		return lstNew.get(0);
	}
	
	/**
	 * List all files and put them in files argument.
	 * @param directoryName
	 * @param files
	 */
	public void listf(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files);
	        }
	    }
	}
	
	/**
	 * Returns all valid CSVPckg files from list of files.
	 * @return
	 */
	private static List<CSV> getCSVFiles(List<File> filesToFilter) {
		List<CSV> result = new ArrayList<>();
		CSV csv;
		for(File file : filesToFilter) {
			try {
				csv = new CSV(file);
				result.add(csv);
			} catch (Throwable e) {
				//e.printStackTrace();
				continue;
			}
		}
		return result;
	}
}
