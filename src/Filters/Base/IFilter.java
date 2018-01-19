package Filters.Base;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.function.Predicate;

public interface IFilter extends Predicate, Serializable {
    void saveToFile(File fileToWriteTo) throws IOException;
}
