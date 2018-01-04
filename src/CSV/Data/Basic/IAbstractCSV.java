package CSV.Data.Basic;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IAbstractCSV {

    /**
     * Format file and write to it the CSV.
     * @param file
     * @return Count of lines written.
     * @throws IOException
     */
    int writeToFile(File file) throws IOException;

    /**
     * Append CSV lines to another file, at the end of the file.
     * @param file
     * @return Count of lines written.
     * @throws IOException
     */
    int appendToFile(File file) throws IOException;

    /**
     * Write to file with existing writer.
     * @return Count of lines written.
     * @param writer
     */
    int appendToWriter(CSVWriter writer);

    /**
     *
     * @return - True if this CSV meets the CSV format, or CSV type. (Combo, Wigle, etc...)
     */
    boolean checkValidCSV();


    /**
     * Print CSV.
     * @throws IOException
     */
    void print();

    List<String[]> getLines();

}
