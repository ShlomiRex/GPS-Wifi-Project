package Helper;

import java.io.File;
import java.util.ArrayList;

/**
 * This class will handle all input (directories, fiels...)
 * 
 * @author ShlomiPC
 *
 */
public abstract class DirectoryAndFileHelper {

	/**
	 * Find valid wigle files
	 * 
	 * @param potentialFiles
	 *            All files to check
	 * @return Wigle csv files
	 */
	public static File[] findWigelFiles(File[] potentialFiles) {
		// if contains WigleWifi at the start of file
		ArrayList<File> result = new ArrayList<>();
		String extension;
		for (File f : potentialFiles) {
			if(f == null)
				continue;
			extension = getFileExtension(f);
			//check extension
			if (extension.equals("csv"))
				result.add(f);

			if(! isHeaderWigle(f))
				continue;
		}
		File[] result2 = new File[result.size()];
		for(int i = 0; i < result2.length; i++) {
			result2[i] = result.get(i);
		}
		return result2;
	}

	/**
	 * Check if file starts with Wigle string
	 * @param f
	 * @return
	 */
	private static boolean isHeaderWigle(File f) {
		//TODO: Complete
		return true;
	}

	/**
	 * 
	 * @param file
	 *            A file
	 * @return The extension of the file (.exe, .csv, .txt)
	 */
	public static String getFileExtension(File file) {
		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
	 * @param path
	 *            Folder to check files
	 * @return All files in the folder
	 */
	public static File[] filesInFolder(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		return listOfFiles;
	}

}
