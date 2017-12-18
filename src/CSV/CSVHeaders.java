package CSV;

import au.com.bytecode.opencsv.CSVWriter;

import java.util.Collections;

public class CSVHeaders {
    public CSV csvWhithThisHeaders;
    public String[] wigleHeader, fieldHeader;

    public CSVHeaders(String[] wigleHeader, String[] fieldHeader) {
        this(wigleHeader, fieldHeader, null);
    }

    public CSVHeaders(String[] wigleHeader, String[] fieldHeader, CSV csvOfThisHeader) {
        this.fieldHeader = fieldHeader;
        this.wigleHeader = wigleHeader;
        this.csvWhithThisHeaders = csvOfThisHeader;
    }

    public void print() {
        if(csvWhithThisHeaders != null)
            System.out.println("CSV: " + csvWhithThisHeaders.toString() + " Wigle: " + wigleHeader + " Field: " + fieldHeader);
        else
            System.out.println("Wigle: " + wigleHeader + " Field: " + fieldHeader);
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
}
