package Database.Concurrency;

import CSV.Combo.ComboLine;

/**
 * Only does 1 thing.
 */
public class ComboLineTaskHolder {
    private ComboLine comboLine;
    private Runnable task;
    public ComboLineTaskHolder(ComboLine comboLine) {
        this.comboLine = comboLine;
    }
}
