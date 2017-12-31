package CSV.Data.Basic;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;

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

//    /**
//     *
//     * @return - Returns a pair of header and its associate index line.<br>
//     *     EXAMPLE: In wigle csv, the WIGLE header is the line number 0,the FIELDS header is the line number 1<br>
//     *     Size of both arrays are always the same.
//     */
//    Pair<AbstractHeader[], Integer[]> getHeaders() throws IOException;

}
