package GUI.Panels;

import Utils.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public final class Panel_Debug extends JPanel {

    private static Panel_Debug INSTANCE = new Panel_Debug();
    public static Panel_Debug getINSTANCE() {
        return INSTANCE;
    }
    public JButton btn_Print, btn_Open;
    public Panel_Debug() {
        btn_Print = new JButton("Print to console selected");
        btn_Print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File selected = Panel_IO.getINSTANCE().selected;
                if(selected == null) {
                    System.err.println("Cannot print to console, null selected.");
                    return;
                }

                if(selected.isFile()) {
                    System.out.println("File: " + selected.getAbsolutePath());
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(selected));
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
                    File[] files = selected.listFiles();
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
                File selected = Panel_IO.getINSTANCE().selected;
                if(selected == null || selected.getAbsolutePath().equals("")){
                    System.err.println("Cannot open null file.");
                    return;
                }
                try {
                    FileUtils.openFile(selected);
                    System.out.println("Openning: " + Panel_Path.getINSTANCE().lbl_Path.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Cannot open path: " + Panel_Path.getINSTANCE().lbl_Path.getText(),
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
