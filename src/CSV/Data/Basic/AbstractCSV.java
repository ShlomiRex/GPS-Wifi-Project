package CSV.Data.Basic;

import Utils.FileUtils;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.sun.istack.NotNull;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract CSV represents a single CSV file. <b>This file/file lines must not be changed!</b>
 */
public abstract class AbstractCSV extends File implements IAbstractCSV {

    protected List<String[]> lines;

    /**
     * Reads the file and init lines. <br> Doesn't check anything. <br> If file doesn't exists, creates new.
     * @param filePath Path to CSV file.
     * @throws IOException - If problem reading file or file doesn't exists.
     */
    public AbstractCSV(String filePath) throws IOException {
        super(filePath);
        File f = new File(filePath);
        if(f.exists() == false)
            f.createNewFile();
        lines = read(this);
    }

    public AbstractCSV(@NotNull  File fileOfCSV) throws IOException {
        super(fileOfCSV.getAbsolutePath());
        if(fileOfCSV.exists() == false)
            throw new IllegalArgumentException("No such file:" + fileOfCSV.getAbsolutePath());
        lines = read(this);
    }

    @Override
    public int writeToFile(File file) throws IOException {
        System.out.println("Writing to file: " + file.getAbsolutePath());
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        writer.writeAll(lines);
        writer.close();
        return lines.size();
    }

    @Override
    public int appendToFile(File file) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(file, true));
        for(String[] line : lines)
            writer.writeNext(line);
        writer.close();
        return lines.size();
    }

    @Override
    public int appendToWriter(CSVWriter writer) {
        for(String[] line : lines)
            writer.writeNext(line);
        return lines.size();
    }

    @Override
    public void print()  {
        for(String[] line : lines) {
            System.out.println(Arrays.toString(line));
        }
    }

    /**
     *
     * @param fileToRead
     * @return - List of lines of CSV.
     * @throws IOException
     */
    public static List<String[]> read(File fileToRead) throws IOException {
        if(fileToRead == null)
            return null;
        FileReader fileReader = new FileReader(fileToRead);
        CSVReader reader = new CSVReader(fileReader);
        List<String[]> result = reader.readAll();
        reader.close();
        return result;
    }

    /**
     *
     * @return - True if file extention is csv.
     */
    public boolean isFileExtentionCSV() {
        String extention = FileUtils.getExtension(this);
        return extention.equals("csv");
    }

    public List<String[]> getLines() {
        return lines;
    }

    /**
     *
     * @param index
     * @return - The line at spesific line index.
     */
    public String[] getLineAt(int index) {
        return lines.get(index);
    }

    /**
     * Check if CSV is valid. NOTE: ALWAYS TRUE , AS THERE NO CONDITIONS
     * @return
     */
    @Override
    public boolean checkValidCSV() {
        return true;
    }
}
