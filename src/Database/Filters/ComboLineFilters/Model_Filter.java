package Database.Filters.ComboLineFilters;

import CSV.Combo.ComboLine;
import Database.Filters.Base.AbstractFilter;

public class Model_Filter extends AbstractFilter {
    private String subString;
    public Model_Filter(String substring) {
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
