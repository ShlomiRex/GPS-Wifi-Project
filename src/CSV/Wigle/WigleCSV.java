package CSV.Wigle;

import CSV.Data.Basic.AbstractCSV;
import CSV.Data.Basic.Headers.WigleHeaders;
import CSV.Wigle.Data.WigleCSVData;
import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;

public class WigleCSV extends AbstractCSV {

    public WigleHeaders headers;
    public WigleCSVData csvData;


    public WigleCSV(String filePath) throws IOException {
        super(filePath);
        if(this.exists() == false) {
            System.out.println("File " + getAbsolutePath() + " doesn't exists. \n Creating file...");
            this.createNewFile();
        }
        System.out.println("Initializing wigle CSV... ");
        headers = getHeaders();

        if(checkValidCSV() == false)
            System.out.println("ERROR: This CSV is not valid!");
        System.out.println("Wigle CSV Initialized: "  + filePath);

        csvData = new WigleCSVData(lines, headers.dataIndex);
    }


    @Override
    public boolean checkValidCSV() {
        return isFileExtentionCSV() && headers.isValid();
    }

    /**
     * Reads file, return wigle headers.(Doesn't check if headers are valid)
     * @return
     * @throws IOException
     */
    private WigleHeaders getHeaders() throws IOException {
        FileReader fr = new FileReader(this);
        CSVReader reader = new CSVReader(fr);

        String[] line1 = reader.readNext();
        String[] line2 = reader.readNext();

        WigleHeaders headers = new WigleHeaders(line1, line2);
        reader.close();

        return headers;
    }

    public void getSortedBy_FirstSeen() {
        csvData.sortBy_FirstSeen();
    }


}
