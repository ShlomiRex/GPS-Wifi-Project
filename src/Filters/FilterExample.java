package Filters;

import CSV.Combo.ComboLine;
import Filters.ComboLineFilters.Location_Filter;
import Filters.OperationFilters.And_Filter;
import Filters.ComboLineFilters.Time_Filter;

import java.util.Date;

public class FilterExample {
    public static void main(String[] args) throws Exception {
        Location_Filter location_filter = new Location_Filter(1,1,1);
        Time_Filter time_filter = new Time_Filter(new Date("2017"),new Date("2018"));
        And_Filter and_filter = new And_Filter(location_filter, time_filter);
        and_filter.test(new ComboLine(new String[3]));
    }
}
