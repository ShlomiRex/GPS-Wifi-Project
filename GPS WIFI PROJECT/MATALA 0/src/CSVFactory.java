import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class CSVFactory {
	enum TypeOfFile {
		//BSSID;LAT;LON;SSID;Crypt;Beacon Interval;Connection Mode;Channel;RXL;Date;Time
		//11 variables.
		GMON_Export_Text;
	}
	
	//Writer to cvs text file.
	private PrintWriter writer;
	/** 
	 * Path to CSV generated file
	 */
	private String outPath;
	
	/**
	 * 
	 * @param file A file to get data from.
	 * @param outPath Absolute path to generated CSV file.
	 * @param outName Name of CSV file.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public CSVFactory(File file, String outPath) throws FileNotFoundException, UnsupportedEncodingException {
		System.out.println("Reading file: " + file.getAbsolutePath());
		TypeOfFile type = TypeOfFile.GMON_Export_Text; //TODO: Change
		writer = new PrintWriter(outPath, "UTF-8");
		
		if(type == TypeOfFile.GMON_Export_Text) {
			//find the first number in the text
			Scanner scanner = new Scanner(file);
			String line;
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				//System.out.println(s);
				writer.println(line);
			}
			scanner.close();
			writer.close();
			System.out.println("Output file: " +outPath);
		}
		
	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		//TODO: Change dynamically
		String path = "C:/Users/ShlomiPC/Desktop/gmon3/gmon/gmon_wlan.txt";
		
		String outPath = "C:/Users/ShlomiPC/Desktop/out.txt";
		File file = new File(path);
		new CSVFactory(file, outPath);
	}

}
