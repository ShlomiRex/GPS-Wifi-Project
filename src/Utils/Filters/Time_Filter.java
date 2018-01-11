package Utils.Filters;

import CSV.Data.AP_WifiData;

import java.util.Date;

public class Time_Filter {
    private Date dateFrom, dateTo;
    public Time_Filter(Date dateFrom, Date dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public boolean test(AP_WifiData ap_wifiData) {
        Date firstSeen = ap_wifiData.firstSeen;
        return firstSeen.compareTo(dateFrom) >= 0 && firstSeen.compareTo(dateTo) <= 0;
    }
}
