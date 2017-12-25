package CSVPckg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class CSVFactory {
	
	public File folder;

	/**
	 * Read all files in folder and subfolders, and put them in csvList.
	 * @param folder Read from this folder and all subfolders - all files.
	 * @throws Throwable
	 */
	public static List<CSV> getCSVsFromFolder(File folder) throws Throwable {
		System.out.println("Reading from folder: " + folder.getAbsolutePath());
		if(folder == null || folder.exists() == false)
			throw new Throwable("Folder is not valid! : " + folder.getAbsolutePath() );
		ArrayList<File> allFilesInFolder = new ArrayList<>();
		listf(folder, allFilesInFolder);
		List<CSV> csvList = getCSVFiles(allFilesInFolder);
		System.out.println("Done reading.");
		System.out.println("Total files in folder: " + allFilesInFolder.size());
		System.out.println("Total valid csv files: " + csvList.size());
		return csvList;
	}
	/**
	 *
	 * @param file
	 * @return csv object by given file. Returns null if file is not valid wigle csv.
	 */
	public static CSV getWigleCSVFromFile(File file) {
		List<File> lst = new ArrayList<>();
		lst.add(file);
		List<CSV> lstNew = getCSVFiles(lst);
		if(lstNew == null || lstNew.isEmpty())
			return null;
		return lstNew.get(0);
	}
	
	/**
	 * List all files and put them in files argument.
	 * @param files
	 */
	public static void listf(File folder, ArrayList<File> files) {
	    // get all the files from a directory
	    File[] fList = folder.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file, files);
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
