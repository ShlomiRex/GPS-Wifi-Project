package GUI.Panels;

import CSV.Combo.ComboCSV;
import CSV.Wigle.WigleCSV;
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

public final class Panel_SelectedOptions extends JPanel{
    public JButton btn_SelectedOptions__AppendTo;
    private File selectedFile;
    private SelectedFileType selectedFileType;

    public Panel_SelectedOptions() {
        btn_SelectedOptions__AppendTo = new JButton("Add Selected To Database");
        btn_SelectedOptions__AppendTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ASDSA");
                if(selectedFileType == SelectedFileType.Wigle) {
                    try {
                        System.out.println("Appending wigle file...");
                        database.append(new WigleCSV(selectedFile.getAbsolutePath()));

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else if(selectedFileType == SelectedFileType.Combo) {
                    try {
                        System.out.println("Appending combo file...");
                        database.append(new ComboCSV(selectedFile.getAbsoluteFile()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else if(selectedFileType == SelectedFileType.Folder) {
                    try {
                        System.out.println("Appending folder...");
                        database.append(FolderUtils.getWigles(selectedFile));
                    } catch (IOException e1) {
                        //e1.printStackTrace();
                    }
                }
            }
        });

        add(btn_SelectedOptions__AppendTo);
        setBorder(BorderFactory.createTitledBorder("Selected Options"));
    }

}
