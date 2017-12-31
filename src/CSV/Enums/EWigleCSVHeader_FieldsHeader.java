package CSV.Enums;

/**
 * 	<p><b>String: </b> MAC, SSID, AuthMode, Type
 *	<br><b>Date: </b> FirstSeen
 *	<br><b>Double: </b> Channel, RSSI, Lat, Lon, Alt, Acc</p>
 */
public enum EWigleCSVHeader_FieldsHeader {

    MAC(0), SSID(1), AuthMode(2), FirstSeen(3), Channel(4), RSSI(5), Lat(6), Lon(7), Alt(8), Acc(9), Type(10);

    public int column;

    EWigleCSVHeader_FieldsHeader(int column) {
        this.column = column;
    }

    public static boolean isFieldDouble(EWigleCSVHeader_FieldsHeader field) {
        if(field == RSSI || field == Lat || field == Lon || field == Channel || field == Alt || field == Acc) {
            return true;
        }
        return false;
    }

    public static boolean isFieldString(EWigleCSVHeader_FieldsHeader field) {
        if(field == MAC || field == AuthMode || field == SSID || field == Type) {
            return true;
        }
        return false;
    }


    public static EWigleCSVHeader_FieldsHeader getField(int i) {
        switch (i) {
            case 0:
                return MAC;
            case 1:
                return SSID;
            case 2:
                return AuthMode;
            case 3:
                return FirstSeen;
            case 4:
                return Channel;
            case 5:
                return RSSI;
            case 6:
                return Lat;
            case 7:
                return Lon;
            case 8:
                return Alt;
            case 9:
                return Acc;
            case 10:
                return Type;
        }
        throw new IllegalArgumentException("Column must be 0 to 10!");
    }
}
