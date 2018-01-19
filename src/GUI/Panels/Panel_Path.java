package GUI.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class Panel_Path extends JPanel{
    public static JLabel lbl_Path_Label, lbl_Path, lbl_Selected_Label, lbl_Selected_Text_SelectedType;
    public static JButton btn_Path_CopyToClipboard;
    private static Panel_Path INSTANCE = new Panel_Path();
    public static Panel_Path getINSTANCE() {
        return INSTANCE;
    }

    private Panel_Path() {

        lbl_Path_Label = new JLabel("Path: ");
        lbl_Path = new JLabel("");


        btn_Path_CopyToClipboard = new JButton("Copy path");

        btn_Path_CopyToClipboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String whatToCopy = lbl_Path.getText();
                StringSelection stringSelection = new StringSelection(whatToCopy);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
        });

        add(lbl_Path_Label);
        add(lbl_Path);
        add(btn_Path_CopyToClipboard);

        lbl_Selected_Label = new JLabel("Type: ");
        lbl_Selected_Text_SelectedType = new JLabel("null");

        add(lbl_Selected_Label);
        add(lbl_Selected_Text_SelectedType);

        setBorder(BorderFactory.createTitledBorder("Path"));
    }
}
