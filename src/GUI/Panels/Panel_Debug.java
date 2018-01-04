package GUI.Panels;

import GUI.GUI;
import GUI.Logic.Selected;
import Utils.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static GUI.MainPanel.*;

public final class Panel_Debug extends JPanel {

    public JButton btn_Print, btn_Open;
    public Panel_Debug() {
        btn_Print = new JButton("Print to console selected");
        btn_Print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GUI.selected == null) {
                    System.err.println("Cannot print to console, null selected.");
                    return;
                }

                if(GUI.selected.isFile()) {
                    System.out.println("File: " + GUI.selected.getAbsolutePath());
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(GUI.selected));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                        br.close();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    File[] files = GUI.selected.listFiles();
                    for(File file : files) {
                        System.out.println(file.getName());
                    }
                }
            }
        });

        btn_Open = new JButton("Open selected");
        btn_Open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GUI.selected == null || GUI.selected.getAbsolutePath().equals("")){
                    System.err.println("Cannot open null file.");
                    return;
                }
                try {
                    FileUtils.openFile(GUI.selected);
                    System.out.println("Openning: " + panel_Path.lbl_Path.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Cannot open path: " + panel_Path.lbl_Path.getText(),
                            "Cannot open path",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(btn_Open);
        add(btn_Print);

        setBorder(BorderFactory.createTitledBorder("Debug"));
    }
}
