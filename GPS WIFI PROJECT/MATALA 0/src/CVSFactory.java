import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class CVSFactory {
	enum TypeOfFile {
		//BSSID;LAT;LON;SSID;Crypt;Beacon Interval;Connection Mode;Channel;RXL;Date;Time
		//11 variables.
		GMON_Export_Text;
	}
	
	//Writer to cvs text file.
	private PrintWriter writer;
	//hello
	public CVSFactory(File file) throws FileNotFoundException, UnsupportedEncodingException {
		System.out.println("Reading file: " + file.getAbsolutePath());
		TypeOfFile type = TypeOfFile.GMON_Export_Text; //TODO: Change
		
		String outPath = "";
		String outName = "out.txt";
		
		writer = new PrintWriter(outPath + outName, "UTF-8");
		
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
		}
		
	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		String path = "C:/Users/ShlomiPC/Desktop/gmon3/gmon";
		String fileName = "gmon_wlan.txt"; //TODO: Dynamically change
		File file = new File(path+"/"+fileName);
		new CVSFactory(file);
	}
}
