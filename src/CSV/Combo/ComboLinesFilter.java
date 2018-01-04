package CSV.Combo;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ComboLinesFilter {
    private ComboLines comboLines;
    public ComboLinesFilter(ComboLines comboLines) {
        this.comboLines = comboLines;
    }

    public void filterBy_Date(Date dateFrom, Date dateTo) {
        if(dateFrom.compareTo(dateFrom) > 1) {
            return;
        }
        List<ComboLine> new_lines = comboLines.stream().parallel().filter(
                (comboLine) -> {
                    Date firstSeen = comboLine.getFirstSeen();
                    return firstSeen.compareTo(dateFrom) > 0 && firstSeen.compareTo(dateTo) < 0;
                }
        ).collect(Collectors.toList());

        ComboLines new_comboLines = new ComboLines();
        for(ComboLine comboLine : new_lines)
            new_comboLines.add(comboLine);

        comboLines = new_comboLines;
    }

    public void filterBy_Date(Date excactDate) {
        List<ComboLine> new_lines = comboLines.stream().parallel().filter(
                (comboLine) -> {
                    Date firstSeen = comboLine.getFirstSeen();
                    return firstSeen.compareTo(excactDate) == 0;
                }
        ).collect(Collectors.toList());

        ComboLines new_comboLines = new ComboLines();
        for(ComboLine comboLine : new_lines)
            new_comboLines.add(comboLine);

        comboLines = new_comboLines;
    }
}
