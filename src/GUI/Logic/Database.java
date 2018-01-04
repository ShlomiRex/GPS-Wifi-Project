package GUI.Logic;

import CSV.Combo.ComboCSV;
import CSV.Combo.ComboLines;
import CSV.Data.Basic.AbstractCSV;
import CSV.Wigle.WigleCSV;
import GUI.GUI;
import Utils.FileUtils;
import Utils.Paths;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.List;

public class Database extends AbstractCSV implements Serializable{

    public enum Type {
        Wigle, Combo, KML;
    }

    public Type type;
    public ComboLines datas;
    /**
     * Creates empty Database wich represents a csv file with bunch of information. This file
     * can change it's type: Wigle, Combo, OR KML.
     * @param fileOfDatabase
     * @throws IOException
     */
    public Database(File fileOfDatabase, Type type) throws IOException {
        super(fileOfDatabase);
        System.out.println("Initialized database: " + this.getAbsolutePath());
        this.type = type;
        datas = new ComboLines();

    }

    public void append(WigleCSV wigleCSV) throws IOException {
        wigleCSV.appendToFile(this);
    }

    public void append(ComboCSV comboCSV) throws IOException {
        comboCSV.appendToFile(this);
    }

    public void append(List<WigleCSV> wigles) throws IOException {
        System.out.println("Appending " + wigles.size()+ " wigles...");
        int numOfSuccess = 0;
        for(WigleCSV wigleCSV : wigles) {
            try {
                append(wigleCSV);
                numOfSuccess++;
            }catch (Throwable e) {
                e.printStackTrace();
            }
        }
        System.out.println("Successfully added: " + numOfSuccess + " wigles to database.");
    }

    @Override
    public boolean delete() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(this);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        datas.clear();
        return true;
    }

    public int lineCount() {
        return datas.size();
    }

    public void add(List<WigleCSV> wigleCSVList) throws IOException {
        String outFilePath = Paths.OUT + "/tmpComboCSV.csv";
        ComboCSV comboCSV = new ComboCSV(outFilePath,wigleCSVList);
        add(comboCSV);
        File file = new File(outFilePath);
        file.delete();
    }

    public void add(ComboCSV comboCSV) throws IOException {
        comboCSV.appendToFile(this);
    }
}
