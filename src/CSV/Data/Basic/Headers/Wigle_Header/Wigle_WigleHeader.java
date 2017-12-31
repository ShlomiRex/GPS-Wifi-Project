package CSV.Data.Basic.Headers.Wigle_Header;

import CSV.Data.Basic.Headers.Abstract.AbstractHeader;
import CSV.Enums.EWigleCSVHeader_WigleHeader;

public class Wigle_WigleHeader extends AbstractHeader {
    public Wigle_WigleHeader(String[] wigle) {
        super(wigle);
    }


    @Override
    public boolean isValid() {
        if(header[0].contains("Wigle")) {
            return true;
        }
        return false;
    }

    public String getModel() {
        return header[EWigleCSVHeader_WigleHeader.model.column];
    }

    public String getDevice() {
        return header[EWigleCSVHeader_WigleHeader.device.column];
    }
}
