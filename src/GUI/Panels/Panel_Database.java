package GUI.Panels;

import GUI.MainPanel;
import Utils.FileUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static GUI.MainPanel.*;

public final class Panel_Database extends JPanel{

    public JButton btn_OpenDatabase, btn_DeleteDatabase, btn_ExportDB;
    public JLabel lbl_Statistics_LinesCount;

    public Panel_Database() {
        btn_OpenDatabase = new JButton("Open database");
        btn_OpenDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileUtils.openFile((File)database);
                    MainPanel.database.updateDatas();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
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
                    try {
                        MainPanel.database.updateDatas();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });

        btn_ExportDB = new JButton("EXport DB");
        btn_ExportDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportDB_Dialog();
            }
        });


        add(btn_OpenDatabase);
        add(btn_DeleteDatabase);
        add(btn_ExportDB);
        setBorder(BorderFactory.createTitledBorder("Database"));

        JPanel statisticsPanel = new JPanel();
        lbl_Statistics_LinesCount = new JLabel("");
        updateStatistics();
        statisticsPanel.add(lbl_Statistics_LinesCount);
        statisticsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        add(statisticsPanel);
    }

    public void updateStatistics() {
        lbl_Statistics_LinesCount.setText("Line count: " + MainPanel.database.lineCount);
    }

    private void exportDB_Dialog() {
        JOptionPane.showConfirmDialog(null,
                exportDB_Dialog_GetPanel(),
                "JOptionPane Example : ",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    private JPanel exportDB_Dialog_GetPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Java Technology Dive Log");
        ImageIcon image = null;
        try {
            image = new ImageIcon(ImageIO.read(
                    new URL("http://i.imgur.com/6mbHZRU.png")));
        } catch(MalformedURLException mue) {
            mue.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        label.setIcon(image);
        panel.add(label);

        return panel;
    }
}
