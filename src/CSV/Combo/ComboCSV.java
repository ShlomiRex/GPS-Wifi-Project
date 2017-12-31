package CSV.Combo;

import CSV.Data.Basic.AbstractCSV;
import CSV.Wigle.WigleCSV;
import Utils.FolderUtils;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This represents a collection of CSV
 */
public class ComboCSV extends AbstractCSV{

    public ArrayList<ComboLine> wigleCSVArrayList;
    public ComboLines comboLines;

//    /**
//     *
//     * @param csvsFolder - Folder containing wigle csvs to be converted.
//     * @param fileToWrite - Write to this file.
//     * @throws IOException - If problem reading file or file doesn't exists.
//     */
//    public ComboCSV(File fileToWrite, File csvsFolder) throws IOException {
//        this(fileToWrite, FolderUtils.getWigles(csvsFolder));
//    }

//    /**
//     *
//     * @param fileToWrite - Write to this file.
//     * @param wigleCSVList - List of wigle csv to write.
//     * @throws IOException
//     */
//    public ComboCSV(File fileToWrite ,List<WigleCSV> wigleCSVList) throws IOException {
//        super(fileToWrite.getAbsolutePath());
//        this.wigleCSVArrayList = new ArrayList<>();
//        this.comboLines = new ComboLines(wigleCSVList);
//        comboLines.write(this);
//        lines = read(this);
//    }

    /**
     *
     * @param fileToWrite - Write to this file.
     * @param wigleCSVList - List of wigle csv to write.
     * @throws IOException
     */
    public ComboCSV(File fileToWrite ,WigleCSV wigleCSVList) throws IOException {
        //set this csv file as file we write to.
        super(fileToWrite.getAbsolutePath());

        System.out.println("Initializing combo csv...");
        //set combo lines
        this.comboLines = new ComboLines(wigleCSVList);
        //write combo
        comboLines.write(this);
        //init csv lines
        lines = read(this);
        System.out.println("CSV Combo initialized: " + fileToWrite.getAbsolutePath());
    }

    /**
     * This csv is always valid. (For now)
     * @return
     */
    @Override
    public boolean checkValidCSV() {
        //tODO: Decide validation of combo.
        return true;
    }



}
