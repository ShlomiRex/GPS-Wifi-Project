package CSV.Combo;

import CSV.Data.AP_WifiData;
import CSV.Data.Basic.AbstractCSV;
import CSV.Wigle.WigleCSV;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This represents a collection of CSV
 */
public class ComboCSV extends AbstractCSV{

    public ArrayList<ComboLine> wigleCSVArrayList;
    public ComboLines comboLines;

    /**
     * Takes Wigle CSV (single csv) and converts it to Combo CSV.
     * @param fileToWrite - Write to this file as combo.
     * @param wigleCSV - A signle wigle csv to read.
     * @param takeStrongest10 - True if want 10 elements in each combo line. (Hint: Put true)
     * @throws IOException
     */
    public ComboCSV(File fileToWrite ,WigleCSV wigleCSV, boolean takeStrongest10) throws IOException {
        //set this csv file as file we write to.
        super(fileToWrite.getAbsolutePath());

        System.out.println("Initializing combo csv...");
        //set combo lines
        this.comboLines = new ComboLines(wigleCSV);
        //write combo
        comboLines.write(this);
        //init csv lines
        lines = read(this);
        System.out.println("CSV Combo initialized: " + fileToWrite.getAbsolutePath());

        if(takeStrongest10)
            filterStrongestRSSI();
    }

    /**
     *
     * @param comboCSVFile - A File which looks like BM2, BM3.
     * @throws IOException
     */
    public ComboCSV(File comboCSVFile) throws IOException {
        super(comboCSVFile);
        System.out.println("Initializing combo csv...");
        try {
        this.comboLines = new ComboLines(this.getLines()); }
        catch (Exception e) {
            e.initCause(new Throwable("Error at file: " + comboCSVFile.getAbsolutePath()));
            throw e;
        }
        System.out.println("CSV Combo initialized: " + comboCSVFile.getAbsolutePath());
    }

    public ComboCSV(String outFilePath, List<WigleCSV> wigleCSVList) throws IOException {
        super(outFilePath);
        //TODO: COMBINE ALL WIGE INTO 1 COMBO
    }

    public ComboCSV(String outFilePath, WigleCSV wigleCSV) throws IOException {
        this(new File(outFilePath), wigleCSV, false);
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

    /**
     * Filters all combo lines whos size is above 10 and set line max combo data to 10 and takes only strongest RSSI.<br>
     *     Also writes changes to file.
     */
    private void filterStrongestRSSI() throws IOException {
        comboLines.filterStrongestRSSI();
        comboLines.write(this);
    }

    public ComboLine getLineBy_NoGPSMac(String mac) {
        for(ComboLine comboLine : comboLines) {
            for(AP_WifiData ap : comboLine) {
                if(ap.mac.equals(mac))
                    return comboLine;
            }
        }
        return null;
    }


    /**
     * Filters lines and returns lines containing ap_wifidata which has the same mac (any of the input)
     * @param macs
     * @return
     */
    public ComboLines filterOR_by_macs(String[] macs) {
        ComboLines comboLines = new ComboLines();
        for(ComboLine comboLine : comboLines) {
            for(AP_WifiData ap : comboLine) {
                for(String mac : macs) {
                    if(ap.mac.equals(mac)) {
                        comboLines.add(comboLine);
                    }
                }
            }
        }
        return comboLines;
    }
}
