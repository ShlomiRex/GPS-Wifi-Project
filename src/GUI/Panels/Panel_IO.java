package GUI.Panels;

import CSV.Combo.ComboCSV;
import GUI.Logic.Selected;
import GUI.Logic.SelectedFileType;
import Utils.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public final class Panel_IO extends JPanel implements ActionListener{

    public JButton btn_IOInput_ChooseFolder, btn_IOInput_ChooseFile_Combo;
    public File selected = null;
    public SelectedFileType selectedType = SelectedFileType.Null;

    private static Panel_IO INSTANCE = new Panel_IO();
    public static Panel_IO getINSTANCE() {
        return INSTANCE;
    }

    private Panel_IO() {
        btn_IOInput_ChooseFolder = new JButton("Choose folder of wigles");
        btn_IOInput_ChooseFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File folder = FileUtils.getFolderFromUser();
                if(folder == null) {
                    System.err.println("Chosen folder is null.");
                    return;
                }
                Panel_Path.getINSTANCE().lbl_Path.setText(folder.getAbsolutePath());
                selected = new Selected(folder.getAbsolutePath(), SelectedFileType.Folder);
                Panel_Path.getINSTANCE().lbl_Selected_Text_SelectedType.setText("Folder");

            }
        });

        btn_IOInput_ChooseFile_Combo = new JButton("Choose combo file");
        btn_IOInput_ChooseFile_Combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Adding file/folder to database...");
                File file = Utils.FileUtils.getFileFromUser();
                if(file == null) {
                    System.err.println("Chosen file is null.");
                    return;
                }
                Panel_Path.getINSTANCE().lbl_Path.setText(file.getAbsolutePath());
                try {
                    ComboCSV comboCSV = new ComboCSV(file);
                    Panel_Path.getINSTANCE().lbl_Selected_Text_SelectedType.setText("Combo CSV file");
                    selected = new Selected(file.getAbsolutePath(), SelectedFileType.Combo);

                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "File chosen is not valid Combo File.",
                            "Combo conversion error",
                            JOptionPane.ERROR_MESSAGE);
                }
                System.out.println("Added successfuly.");
            }
        });

        JPanel panel_btnWrapper = new JPanel();
        panel_btnWrapper.add(btn_IOInput_ChooseFolder);
        panel_btnWrapper.add(btn_IOInput_ChooseFile_Combo);
        panel_btnWrapper.setBorder(BorderFactory.createTitledBorder("Choose"));
        add(panel_btnWrapper);

        add(Panel_Path.getINSTANCE());
        add(Panel_SelectedOptions.getINSTANCE());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }
}
