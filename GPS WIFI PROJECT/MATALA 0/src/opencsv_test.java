import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class opencsv_test {

	public static void main(String[] args) throws IOException {
		
		String fileName = "out.txt";
		/*
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName),';');
			
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
			    
			    for (String e: nextLine) {
			        System.out.println(e);
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		try (CSVReader reader = new CSVReader(new FileReader(fileName), ';')) {
			/*
            List<String[]> rows = reader.readAll();
            
            for (String[] row: rows) {
                
                for (String e: row) {
                   // System.out.format("%s ", e);
                }
                
                System.out.println();
            }
            */
			String[] s = reader.readNext();
			while(s != null) {
				for(String s2 : s)
					System.out.print(s2 + " ");
				System.out.println();
				s=reader.readNext();
			}
			reader.close();
        }
	}

}
