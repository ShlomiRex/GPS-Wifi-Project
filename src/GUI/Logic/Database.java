package GUI.Logic;

import CSV.Combo.ComboCSV;
import CSV.Combo.ComboLine;
import CSV.Combo.ComboLines;
import CSV.Data.Basic.AbstractCSV;
import CSV.Wigle.WigleCSV;
import GUI.GUI;
import GUI.MainPanel;
import Utils.FileUtils;
import Utils.Paths;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database extends AbstractCSV implements Serializable{

    public enum Type {
        Wigle, Combo, KML;
    }

    public Type type;
    public ComboLines datas;
    public int lineCount = 0;

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
        try {
            updateDatas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void append(WigleCSV wigleCSV) throws IOException {
        String outPath = Paths.OUT + "/tmp.csv";
        ComboCSV comboCSV = new ComboCSV(outPath, wigleCSV);
        lineCount += comboCSV.appendToFile(this);
    }

    public void append(ComboCSV comboCSV) throws IOException {
        lineCount += comboCSV.appendToFile(this);
    }

    public void append(List<WigleCSV> wigles) throws IOException {
        if(wigles == null || wigles.size() ==0)
            return;
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
        return lineCount;
    }

    /**
     * Reads from CSV (After insertions, deletions... after all that) and puts that in RAM. (Init datas)
     */
    public void updateDatas() throws Exception {
        datas = new ComboLines();
        List<String[]> lines = read(this);
        //TODO: This is so strange! It reads more lines than there actually are!
        System.out.println("[UpdateDatas] Lines: " + lines.size());
        int i = 0;
        for(String[] line : lines) {
            try {
                datas.add(new ComboLine(line));
            } catch (Exception e) {
                System.out.println("ERROR LINE: " + i + "AT :" + Arrays.toString(line));
            }
            i++;
        }
        if(MainPanel.panel_Database != null) {
            MainPanel.panel_Database.updateStatistics();
        }
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
