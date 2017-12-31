package CSV.Data.Basic.Headers.Interfaces;

import au.com.bytecode.opencsv.CSVWriter;

public interface IHeaders {
    void writeHeaders(CSVWriter writer);
    void print();
}
