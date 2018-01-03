package GUI;

import CSV.Combo.ComboCSV;
import CSV.Data.Basic.AbstractCSV;
import CSV.Wigle.WigleCSV;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class Database extends AbstractCSV implements Serializable{

    public enum Type {
        Wigle, Combo, KML;
    }

    public Type type;

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

    }

    public void append(WigleCSV wigleCSV) throws IOException {
        wigleCSV.appendToFile(this);
    }

    public void append(ComboCSV comboCSV) throws IOException {
        comboCSV.appendToFile(this);
    }


}
