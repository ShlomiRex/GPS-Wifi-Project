package Filters.ComboLineFilters;

import CSV.Data.AP_WifiData;
import Filters.Base.AbstractFilter;

import java.util.Date;

public class Time_Filter extends AbstractFilter {
    private Date dateFrom, dateTo;
    public Time_Filter(Date dateFrom, Date dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public boolean test(Object ap_wifiData) {
        Date firstSeen = ((AP_WifiData)(ap_wifiData)).firstSeen;
        return firstSeen.compareTo(dateFrom) >= 0 && firstSeen.compareTo(dateTo) <= 0;
    }
}
