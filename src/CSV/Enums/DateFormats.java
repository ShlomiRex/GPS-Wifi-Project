package CSV.Enums;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormats {
    public static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public static SimpleDateFormat format3 = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    /**
     * May return null if parsing is failure.
     * @param str
     * @return
     */
    public static Date parse(String str) {
        Date date = null;
        try {
            date = DateFormats.format2.parse(str);
        } catch (Exception e) {
            try {
                date = DateFormats.format1.parse(str);
            } catch (Exception e1) {
                try {
                    date = DateFormats.format3.parse(str);
                } catch (Exception e2) {
                    e1.printStackTrace();
                    System.err.println("Error parsing: " + str);
                }
            }
        }
        return date;
    }
}
