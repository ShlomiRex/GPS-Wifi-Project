package Tests;

import CSV.Combo.ComboCSV;
import CSV.Combo.ComboLinesFilter;
import Utils.Paths;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class ComboLinesFilterTest {

    @Test
    public void filterBy_Date() throws IOException {
        String path = Paths.DATA_MOODLE_TESTING + "/";
        File file = new File(path);
        ComboCSV comboCSV = new ComboCSV(file);

        ComboLinesFilter filter = new ComboLinesFilter(comboCSV.comboLines);
        //TODO: Continue
    }

}