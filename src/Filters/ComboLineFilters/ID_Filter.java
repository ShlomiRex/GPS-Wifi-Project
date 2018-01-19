package Filters.ComboLineFilters;

import CSV.Combo.ComboLine;
import Filters.Base.AbstractFilter;

public class ID_Filter extends AbstractFilter {
    public String subString;
    public ID_Filter(String substring) {
        this.subString = substring;
    }

    /**
     * Must be instace of combo line
     * @param comboLine
     * @return
     */
    @Override
    public boolean test(Object comboLine) {
        if(comboLine instanceof ComboLine == false)
            return false;
        ComboLine myCombo = (ComboLine) comboLine;
        return myCombo.getId().contains(subString);
    }
}
