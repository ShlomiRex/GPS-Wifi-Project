package CSVPckg;

import au.com.bytecode.opencsv.CSVWriter;

import java.util.ArrayList;
import java.util.Arrays;

public class CSVHeaders {
    public CSV csvWhithThisHeaders;
    public String[] wigleHeader, fieldHeader;

    public CSVHeaders(String[] wigleHeader, String[] fieldHeader) {
        this(wigleHeader, fieldHeader, null);
    }

    public enum WigleField {
        Wigle(0), appRelease(1), model(2), release(3), device(4), display(5), board(6), brand(7);

        public int column;
        WigleField(int column) {
            this.column = column;
        }
    }

    public CSVHeaders(String[] wigleHeader, String[] fieldHeader, CSV csvOfThisHeader) {
        this.fieldHeader = fieldHeader;
        this.wigleHeader = wigleHeader;
        this.csvWhithThisHeaders = csvOfThisHeader;
    }

    public void print() {
        if(csvWhithThisHeaders != null)
            System.out.println("CSV: " + csvWhithThisHeaders.toString() + " Wigle: " + Arrays.toString(wigleHeader) + " Field: " + Arrays.toString(fieldHeader));
        else
            System.out.println("Wigle: " + Arrays.toString(wigleHeader) + " Field: " + Arrays.toString(fieldHeader));
    }

    public void setWigle(String[] strings) {
        this.wigleHeader = strings;
    }

    public void setHeader(String[] strings) {
        this.fieldHeader = strings;
    }

    public void writeData(CSVWriter writer) {
        writer.writeNext(wigleHeader);
        writer.writeNext(fieldHeader);
    }

    public String wigle_getField(WigleField field) {
        return wigleHeader[field.column];
    }
}
