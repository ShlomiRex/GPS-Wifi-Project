package Database.Concurrency;

import CSV.Combo.ComboLine;

public interface IMyTask {
    void run(ComboLine comboLine);
}
