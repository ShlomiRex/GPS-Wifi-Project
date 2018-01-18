package GUI;

import Database.Database;
import GUI.Panels.*;
import Utils.Paths;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MainPanel extends JPanel {
    public static Database database;

    public static Panel_Path panel_Path;
    public static Panel_Database panel_Database;
    public static Panel_IO panel_IO;
    public static Panel_Debug panel_Debug;
    public static Panel_SelectedOptions panel_SelectedOptions;

    public static JPanel panel_wrapper;

    public MainPanel() throws IOException {
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initDatabase();

        setLayout(new GridLayout(0,2));
        panel_wrapper = new JPanel();


        panel_Database = new Panel_Database();
        panel_Database.setVisible(false);

        panel_IO = new Panel_IO();
        add(panel_IO);

        panel_Debug = new Panel_Debug();
        add(panel_Debug);
        panel_Debug.setVisible(false);

        panel_Path = new Panel_Path();
        add(panel_Path);
        panel_Path.setVisible(false);

        panel_SelectedOptions = new Panel_SelectedOptions();
        add(panel_SelectedOptions);
        panel_SelectedOptions.setVisible(false);

        add(panel_Database);

        panel_wrapper.add(this);
    }

    private void initDatabase() throws IOException {
        String defaultFileName = "Database.csv";
        File defaultFileOut = new File(Paths.OUT + defaultFileName);
        defaultFileOut.createNewFile();

        database = new Database(defaultFileOut);
    }

    public static void setAllPanelsVisible() {
        panel_Path.setVisible(true);
        panel_Database.setVisible(true);
        panel_IO.setVisible(true);
        panel_Debug.setVisible(true);
        panel_SelectedOptions.setVisible(true);
    }

}
