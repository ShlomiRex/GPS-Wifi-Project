package GUI.Panels;

import CSV.Combo.ComboCSV;
import CSV.Wigle.WigleCSV;
import Database.Database;
import GUI.Logic.SelectedFileType;
import Utils.FolderUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public final class Panel_SelectedOptions extends JPanel{
    public JButton btn_SelectedOptions__AppendTo;
    private static Panel_SelectedOptions INSTANCE = new Panel_SelectedOptions();

    public static Panel_SelectedOptions getINSTANCE() {
        return INSTANCE;
    }

    private Panel_SelectedOptions() {
        Database database = Database.getINSTANCE();
        btn_SelectedOptions__AppendTo = new JButton("Add Selected To Database");
        btn_SelectedOptions__AppendTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ASD");
                File selected = Panel_IO.getINSTANCE().selected;
                SelectedFileType selectedFileType = Panel_IO.getINSTANCE().selectedType;
                if(selected == null)
                    return;;
                System.out.println(selectedFileType.name());
                if(selectedFileType == SelectedFileType.Wigle) {
                    try {
                        System.out.println("Appending wigle file...");
                        database.append(new WigleCSV(selected.getAbsolutePath()));
                        database.updateDatas();
                        Panel_Database.getINSTANCE().updateStatistics();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else if(selectedFileType == SelectedFileType.Combo) {
                    try {
                        System.out.println("Appending combo file...");
                        database.append(new ComboCSV(selected.getAbsoluteFile()));
                        database.updateDatas();
                        Panel_Database.getINSTANCE().updateStatistics();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else if(selectedFileType == SelectedFileType.Folder) {
                    try {
                        System.out.println("Appending folder...");
                        database.append(FolderUtils.getWigles(selected));
                        database.updateDatas();
                        Panel_Database.getINSTANCE().updateStatistics();
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
