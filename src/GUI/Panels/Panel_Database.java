package GUI.Panels;

import CSV.Combo.ComboCSV;
import CSV.Wigle.WigleCSV;
import GUI.Logic.Database;
import Utils.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static GUI.MainPanel.*;

public final class Panel_Database extends JPanel{

    public JButton btn_OpenDatabase, btn_DeleteDatabase;
    public JLabel lbl_Statistics_LinesCount;

    public Panel_Database() {
        btn_OpenDatabase = new JButton("Open database");
        btn_OpenDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileUtils.openFile((File)database);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        btn_DeleteDatabase = new JButton("Delete database");
        btn_DeleteDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog (null,
                        "Are you sure you want to delete database? This can't be undone!");

                if(dialogResult == JOptionPane.YES_OPTION){
                    database.delete();
                    System.out.println("\n\nDatabase Deleted.\n\n");
                }

            }
        });


        add(btn_OpenDatabase);
        add(btn_DeleteDatabase);
        setBorder(BorderFactory.createTitledBorder("Database"));

        JPanel statisticsPanel = new JPanel();
        lbl_Statistics_LinesCount = new JLabel("Line count: " + database.lineCount());
        statisticsPanel.add(lbl_Statistics_LinesCount);
        statisticsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        add(statisticsPanel);
    }

}
