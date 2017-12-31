package CSV.Data.Basic.Headers.Abstract;

import CSV.Data.Basic.Headers.Interfaces.IHeader;
import au.com.bytecode.opencsv.CSVWriter;

import java.util.Arrays;

public abstract class AbstractHeader implements IHeader {
    public String[] header;
    public AbstractHeader(String[] header) {
        this.header = header;
    }

    @Override
    public void write(CSVWriter writer) {
        writer.writeNext(header);
    }

    @Override
    public void print() {
        System.out.println(Arrays.toString(header));
    }


}
