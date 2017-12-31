package CSV.Data.Basic.Headers;

import CSV.Data.Basic.Headers.Interfaces.IHeaders;
import CSV.Data.Basic.Headers.Wigle_Header.Wigle_FieldsHeader;
import CSV.Data.Basic.Headers.Wigle_Header.Wigle_WigleHeader;
import au.com.bytecode.opencsv.CSVWriter;

public class WigleHeaders implements IHeaders {
    public Wigle_WigleHeader wigleHeader;
    public Wigle_FieldsHeader fieldsHeader;

    /**
     * Can be 0, 1 or -1.<br>
     *     -1 is when header is not present.
     */
    public final int wigleHeaderIndex, fieldsHeaderIndex, dataIndex;

    public WigleHeaders(String[] wigleHeader, String[] fieldsHeader) {

        this.wigleHeader = new Wigle_WigleHeader(wigleHeader);
        this.fieldsHeader = new Wigle_FieldsHeader(fieldsHeader);

        if(this.wigleHeader.isValid()) {
            this.wigleHeaderIndex = 0;

            if(this.fieldsHeader.isValid()) {
                this.fieldsHeaderIndex = 1;
                this.dataIndex = 2;
            }
            else {
                this.fieldsHeaderIndex = -1;
                this.dataIndex = 1;
            }
        } else {
            this.wigleHeaderIndex = -1;

            if (this.fieldsHeader.isValid()) {
                this.fieldsHeaderIndex = 0;
                this.dataIndex = 1;
            }
            else {
                this.fieldsHeaderIndex = -1;
                this.dataIndex = 0;
            }
        }
    }

    /**
     *
     * @return - True if both headers are fine.
     */
    public boolean isValid() {
        return wigleHeader.isValid() && fieldsHeader.isValid();
    }

    @Override
    public void writeHeaders(CSVWriter writer) {
        wigleHeader.write(writer);
        fieldsHeader.write(writer);
    }

    @Override
    public void print() {
        wigleHeader.print();
        fieldsHeader.print();
    }
}
