import java.io.IOException;
import java.text.ParseException;

public class Start {

	public static void main(String[] args) throws IOException, ParseException {
		String folder = "C:/Users/ShlomiPC/Desktop/wigle/";
		String outFolder = "C:/Users/ShlomiPC/Desktop/";
		new CSVFactory(folder, outFolder);
	}

}
