package GUI.Panels;

import CSV.Combo.ComboCSV;
import CSV.Wigle.WigleCSV;
import GUI.GUI;
import GUI.Logic.Database;
import GUI.Logic.SelectedFileType;
import GUI.MainPanel;
import Utils.FileUtils;
import Utils.FolderUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static GUI.MainPanel.*;
import static GUI.GUI.selected;

public final class Panel_SelectedOptions extends JPanel{
    public JButton btn_SelectedOptions__AppendTo;

    public Panel_SelectedOptions() {
        btn_SelectedOptions__AppendTo = new JButton("Add Selected To Database");
        btn_SelectedOptions__AppendTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(selected.type.name());
                if(selected.type == SelectedFileType.Wigle) {
                    try {
                        System.out.println("Appending wigle file...");
                        database.append(new WigleCSV(selected.getAbsolutePath()));
                        database.updateDatas();
                        panel_Database.updateStatistics();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else if(selected.type == SelectedFileType.Combo) {
                    try {
                        System.out.println("Appending combo file...");
                        database.append(new ComboCSV(selected.getAbsoluteFile()));
                        database.updateDatas();
                        panel_Database.updateStatistics();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else if(selected.type == SelectedFileType.Folder) {
                    try {
                        System.out.println("Appending folder...");
                        database.append(FolderUtils.getWigles(selected));
                        database.updateDatas();
                        panel_Database.updateStatistics();
                    } catch (Exception e1) {
                        //e1.printStackTrace();
                    }
                }
            }
        });

        add(btn_SelectedOptions__AppendTo);
        setBorder(BorderFactory.createTitledBorder("Selected Options"));
    }

}
