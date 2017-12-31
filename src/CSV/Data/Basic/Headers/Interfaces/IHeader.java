package CSV.Data.Basic.Headers.Interfaces;

import au.com.bytecode.opencsv.CSVWriter;

public interface IHeader {
    /**
     *
     * @return - True if this header is fine.(By format)
     */
    boolean isValid();

    /**
     * Writes this header.
     * @param writer
     */
    void write(CSVWriter writer);

    void print();
}
