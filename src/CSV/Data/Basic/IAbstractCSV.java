package CSV.Data.Basic;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IAbstractCSV {

    /**
     * Format file and write to it the CSV.
     * @param file
     * @throws IOException
     */
    void writeToFile(File file) throws IOException;

    /**
     * Append CSV lines to another file, at the end of the file.
     * @param file
     * @throws IOException
     */
    void appendToFile(File file) throws IOException;

    /**
     * Write to file with existing writer.
     * @param writer
     */
    void appendToWriter(CSVWriter writer);

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
