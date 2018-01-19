package Filters.Base;

import java.io.*;

public abstract class AbstractFilter implements IFilter{
    /**
     * Saves the children of AbstractFilter into a file.
     * @param fileToWriteTo
     * @throws IOException
     */
    @Override
    public void saveToFile(File fileToWriteTo) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileToWriteTo);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this); //writes children's classes's objects
        oos.close();
        fos.close();
    }
}