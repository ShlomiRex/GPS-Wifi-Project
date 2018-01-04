package GUI.Panels;

import CSV.Combo.ComboCSV;
import GUI.GUI;
import GUI.Logic.Selected;
import GUI.Logic.SelectedFileType;
import Utils.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static GUI.MainPanel.*;
import static GUI.MainPanel.setAllPanelsVisible;

public final class Panel_IO extends JPanel implements ActionListener{
    public JButton btn_IOInput_ChooseFolder, btn_IOInput_ChooseFile_Combo;
    public Panel_IO() {
        btn_IOInput_ChooseFolder = new JButton("Choose folder of wigles");
        btn_IOInput_ChooseFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File folder = FileUtils.getFolderFromUser();
                if(folder == null) {
                    System.err.println("Chosen folder is null.");
                    return;
                }
                panel_Path.lbl_Path.setText(folder.getAbsolutePath());
                GUI.selected = new Selected(folder.getAbsolutePath(), SelectedFileType.Folder);
                panel_Path.lbl_Selected_Text_SelectedType.setText("Folder");

                setAllPanelsVisible();
            }
        });

        btn_IOInput_ChooseFile_Combo = new JButton("Choose combo file");
        btn_IOInput_ChooseFile_Combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = Utils.FileUtils.getFileFromUser();
                if(file == null) {
                    System.err.println("Chosen file is null.");
                    return;
                }
                panel_Path.lbl_Path.setText(file.getAbsolutePath());
                try {
                    ComboCSV comboCSV = new ComboCSV(file);
                    panel_Path.lbl_Selected_Text_SelectedType.setText("Combo CSV file");
                    GUI.selected = new Selected(file.getAbsolutePath(), SelectedFileType.Combo);

                    setAllPanelsVisible();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "File chosen is not valid Combo File.",
                            "Combo conversion error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(btn_IOInput_ChooseFolder);
        add(btn_IOInput_ChooseFile_Combo);
        setBorder(BorderFactory.createTitledBorder("I/O Input"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }
}
