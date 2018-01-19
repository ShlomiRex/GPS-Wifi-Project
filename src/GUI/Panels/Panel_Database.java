package GUI.Panels;

import Database.Database;
import Utils.FileUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public final class Panel_Database extends JPanel{

    private static Panel_Database INSTANCE = new Panel_Database();
    public static Panel_Database getINSTANCE() {
        return INSTANCE;
    }

    public JButton btn_OpenDatabase, btn_DeleteDatabase, btn_ExportDB;
    public JLabel lbl_Statistics_LinesCount;

    private Panel_Database() {
        btn_OpenDatabase = new JButton("Open database");
        btn_OpenDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileUtils.openFile(Database.getINSTANCE());
                    Database.getINSTANCE().updateDatas();
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
                    Database.getINSTANCE().delete();
                    System.out.println("\n\nDatabase Deleted.\n\n");
                    try {
                        Database.getINSTANCE().updateDatas();
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
        add(statisticsPanel);
    }

    public void updateStatistics() {
        lbl_Statistics_LinesCount.setText("Line count: " + Database.getINSTANCE().lineCount);
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
