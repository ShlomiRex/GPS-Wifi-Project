package Filters.ComboLineFilters;

import CSV.Combo.ComboLine;
import Filters.Base.AbstractFilter;

public class Location_Filter extends AbstractFilter {

    private double centerLat, centerLon, radius;

    public Location_Filter(double centerLat, double centerLon, double radius) {
        this.centerLat = centerLat;
        this.centerLon = centerLon;
        this.radius = radius;
    }

    /**
     * Must be instance of ComboLine in order to work.
     * @param comboLine
     * @return
     */
    @Override
    public boolean test(Object comboLine) {
        if(comboLine instanceof ComboLine == false) {
            return false;
        }
        ComboLine myComboLine = (ComboLine)comboLine;
        double x = myComboLine.getLocation().lat;
        double y = myComboLine.getLocation().lon;
        return distance(centerLat, x, centerLon, y) <= radius;
    }


    /**
     *
     * @param x1 - Center point
     * @param x2
     * @param y1 - Center point
     * @param y2
     * @return Distance between 2 points.
     */
    private double distance(double x1, double x2, double y1, double y2) {
        return Math.hypot(x2-x1, y2-y1);
    }

}
