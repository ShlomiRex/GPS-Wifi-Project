package Database.Concurrency;

import CSV.Combo.ComboLines;

public class ComboLinesRunner extends  Thread{

    private ComboLines comboLines;
    private int indexFrom, indexTo;
    private IMyTask task;

    /**
     *
     * @param indexFrom
     * @param indexTo
     * @param comboLines
     */
    public ComboLinesRunner(int indexFrom, int indexTo, ComboLines comboLines, IMyTask task) {
        this.indexFrom = indexFrom;
        this.indexTo = indexTo;
        this.comboLines = comboLines;
        this.task = task;
    }

    /**
     * For each combo line in the given index range, do the task.
     */
    @Override
    public void run() {
        super.run();

        for(int i = indexFrom; i < indexTo; i++) {
            task.run(comboLines.get(i));
        }
    }

}
