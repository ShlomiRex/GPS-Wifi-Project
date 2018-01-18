package Database;

import CSV.Combo.ComboCSV;
import CSV.Combo.ComboLine;
import CSV.Combo.ComboLines;
import CSV.Data.Basic.AbstractCSV;
import CSV.Wigle.WigleCSV;
import Database.Concurrency.ComboLineTask;
import Database.Concurrency.ComboLinesRunner;
import GUI.MainPanel;
import Utils.Paths;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Database extends AbstractCSV implements Serializable{

    public ComboLines data;
    public int lineCount = 0;
    //private ArrayList<Pair<File, FileType>> filesInDatabase;

    /**
     * Creates empty Database wich represents a csv file with bunch of information. This file
     * can change it's type: Wigle, Combo, OR KML.
     * @param fileOfDatabase
     * @throws IOException
     */
    public Database(final File fileOfDatabase) throws IOException {
        super(fileOfDatabase);
        System.out.println("Initialized database: " + this.getAbsolutePath());
        try {
            updateDatas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Database initialized successfuly.");
    }

    /**
     * Append wigleCSV into database.
     * @param wigleCSV
     * @throws IOException
     */
    public void append(WigleCSV wigleCSV) throws IOException {
        String outPath = Paths.OUT + "/tmp.csv";
        ComboCSV comboCSV = new ComboCSV(outPath, wigleCSV);
        lineCount += comboCSV.appendToFile(this);
    }

    /**
     * Append comboCSV into the database without checking duplicates.
     * @param comboCSV
     * @throws IOException
     */
    public void append(ComboCSV comboCSV) throws IOException {
        lineCount += comboCSV.appendToFile(this);
    }

    /**
     * Append list of wigle csv.
     * @param wigles
     * @throws IOException
     */
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

    /**
     * Delete this database's data. Empty file.
     * @return
     */
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

        data.clear();
        return true;
    }

    /**
     *
     * @return Number of lines in the database.
     */
    public int lineCount() {
        return lineCount;
    }

    /**
     * Reads from CSV (After insertions, deletions... after all that) and puts that in RAM. (Init data)
     */
    public void updateDatas() throws Exception {
        data = new ComboLines();
        List<String[]> lines = read(this);
        //TODO: This is so strange! It reads more lines than there actually are!
        System.out.println("[UpdateDatas] Lines: " + lines.size());
        int i = 0;
        for(String[] line : lines) {
            try {
                data.add(new ComboLine(line));
            } catch (Exception e) {
                System.out.println("ERROR LINE: " + i + "AT :" + Arrays.toString(line));
            }
            i++;
        }
        updateDatabaseUI();
    }

    /**
     * Updates the GUI for user of the database.
     */
    private void updateDatabaseUI() {
        if(MainPanel.panel_Database != null) {
            MainPanel.panel_Database.updateStatistics();
        }
    }
//
//    public void add(List<WigleCSV> wigleCSVList) throws IOException {
//        for(WigleCSV wigleCSV : wigleCSVList) {
//            filesInDatabase.add(new Pair<>(wigleCSV, FileType.Wigle));
//        }
//        String outFilePath = Paths.OUT + "/tmpComboCSV.csv";
//        ComboCSV comboCSV = new ComboCSV(outFilePath,wigleCSVList);
//        add(comboCSV);
//    }

    /**
     * Appends entire comboCSV to database without checking duplicates. (For fast database)
     * @param comboCSV
     * @throws IOException
     */
    public void appendToDatabase(ComboCSV comboCSV) throws IOException {
        comboCSV.appendToFile(this);
    }

    /**
     * Applies filter.
     * @param filter
     * @return Returns list of lines which are true to the filter
     */
    public List<ComboLine> applyFilter(Predicate filter) {
        return (List<ComboLine>) data
                .stream()
                .parallel()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public String searchRouter_ByMac(String mac) {
        final int indexFrom = 0;
        final int indexTo = data.size();

        ComboLineTask searchMacTask = new ComboLineTask() {
            @Override
            public void run(ComboLine comboLine) {
                
            }
        };

        ComboLinesRunner runner1 = new ComboLinesRunner(indexFrom, (indexFrom/4), data, searchMacTask);
        ComboLinesRunner runner2 = new ComboLinesRunner((indexFrom/4)+1, (indexFrom/2), data, searchMacTask);
        ComboLinesRunner runner3 = new ComboLinesRunner((indexFrom/2)+1, (3*(indexFrom/4)), data, searchMacTask);
        ComboLinesRunner runner4 = new ComboLinesRunner((3*(indexFrom/4))+1, indexTo, data, searchMacTask);

        runner1.sta
    }
}
