package GUI;

import CSV.Combo.ComboCSV;
import GUI.Logic.Database;
import GUI.Logic.Selected;
import GUI.Logic.SelectedFileType;
import GUI.Panels.*;
import Utils.FileUtils;
import Utils.Paths;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainPanel extends JPanel {
    public static Database database;

    public static Panel_Path panel_Path;
    public static Panel_Database panel_Database;
    public static Panel_IO panel_IO;
    public static Panel_Debug panel_Debug;
    public static Panel_SelectedOptions panel_SelectedOptions;

    public MainPanel() throws IOException {
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initDatabase();

        panel_IO = new Panel_IO();
        add(panel_IO);

        panel_Debug = new Panel_Debug();
        add(panel_Debug);
        panel_Debug.setVisible(false);

        panel_Path = new Panel_Path();
        add(panel_Path);
        panel_Path.setVisible(false);

        panel_Database = new Panel_Database();
        add(panel_Database);
        panel_Database.setVisible(false);

        panel_SelectedOptions = new Panel_SelectedOptions();
        add(panel_SelectedOptions);
        panel_SelectedOptions.setVisible(false);

    }

    private void initDatabase() throws IOException {
        String defaultFileName = "Database.csv";
        File defaultFileOut = new File(Paths.OUT + defaultFileName);
        defaultFileOut.createNewFile();

        database = new Database(defaultFileOut, Database.Type.Wigle);
    }

    public static void setAllPanelsVisible() {
        panel_Path.setVisible(true);
        panel_Database.setVisible(true);
        panel_IO.setVisible(true);
        panel_Debug.setVisible(true);
        panel_SelectedOptions.setVisible(true);
    }

}
