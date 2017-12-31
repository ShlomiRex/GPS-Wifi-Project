package CSV.Data.Basic.Headers.Wigle_Header;

import CSV.Data.Basic.Headers.Abstract.AbstractHeader;
import CSV.Enums.EWigleCSVHeader_FieldsHeader;
import CSV.Enums.EWigleCSVHeader_WigleHeader;

public class Wigle_FieldsHeader extends AbstractHeader {

    public Wigle_FieldsHeader(String[] fields) {
        super(fields);
        if(isValid() == false)
            throw new IllegalArgumentException("Fields header is not valid.");
    }

    @Override
    public boolean isValid() {
        for(int i = 0; i < header.length; i++) {
            if(header[i].toLowerCase().contains(EWigleCSVHeader_FieldsHeader.getField(i).name().toLowerCase()) == false) {
                System.out.print("ERROR: Field: " + header[i].toLowerCase() + " is doesn't contain:" + EWigleCSVHeader_FieldsHeader.getField(i).name().toLowerCase());
                return false;
            }
        }
        return true;
    }


}
