package CSV.Data.Basic;

import Utils.FileUtils;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract CSV represents a single CSV file. <b>This file/file lines must not be changed!</b>
 */
public abstract class AbstractCSV extends File implements IAbstractCSV {

    protected List<String[]> lines;

    /**
     * Reads from CSV file. <br> Doesn't check if the file is actually CSV.
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

    @Override
    public void writeToFile(File file) throws IOException {
        System.out.println("Writing to file: " + file.getAbsolutePath());
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        writer.writeAll(lines);
        writer.close();
    }

    @Override
    public void appendToFile(File file) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(file, true));
        for(String[] line : lines)
            writer.writeNext(line);
        writer.close();
    }

    @Override
    public void appendToWriter(CSVWriter writer) {
        for(String[] line : lines)
            writer.writeNext(line);
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
        CSVReader reader = new CSVReader(new FileReader(fileToRead));
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


}
