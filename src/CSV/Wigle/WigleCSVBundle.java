package CSV.Wigle;

import Utils.FolderUtils;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This represents a bundle of CSV combined into 1 huge CSV. <br>
 *     Do not get confused by "Bundle" and "Combo". <br>
 */
public class WigleCSVBundle extends ArrayList<WigleCSV> {

    /**
     *
     * @param appendThese CSV to append.
     * @param toFile Append to this file.
     * @throws IOException
     */
    public WigleCSVBundle(List<WigleCSV> appendThese, File toFile) throws IOException {
        addAll(appendThese);
        appendToFile(toFile);
    }
//
//    public WigleCSVBundle(File folderOfCSVs, File toFile) throws IOException {
//
//        ArrayList<WigleCSV> wigleCSVS = (ArrayList<WigleCSV>) FolderUtils.getWigles(folderOfCSVs);
//
//        appendToFile(toFile);
//    }

    private void appendToFile(File file) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(file, true));
        for(WigleCSV wigleCSV : this) {
            wigleCSV.appendToWriter(writer);
        }
        writer.close();
    }


}
