package GUI.Panels;

import Database.Database;
import Utils.FileUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    private File exportDestination;
    private void exportDB_Dialog() {
        int result = JOptionPane.showConfirmDialog(null,
                exportDB_Dialog_GetPanel(),
                "Choose destination",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if(result == JOptionPane.OK_OPTION) {
            try {
                File dest = new File(exportDestination.getAbsolutePath()+"/Database.obj");
                System.out.println("Exporting database to: " + dest.getAbsolutePath());
                Files.createFile(Paths.get(dest.getAbsolutePath()));
                final Database database =  Database.getINSTANCE();
                FileUtils.writeObjectToFile(exportDestination, database);
                System.out.println("Successfuly exported.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private JPanel exportDB_Dialog_GetPanel() {
        JPanel panel = new JPanel();
        JButton btn_chooseDestination = new JButton("Destination");
        panel.add(new JLabel("Choose destination:"));
        panel.add(btn_chooseDestination);
        JLabel lbl_path = new JLabel("null path");
        btn_chooseDestination.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportDestination = FileUtils.getFolderFromUser();
                lbl_path.setText(exportDestination.getAbsolutePath());
                lbl_path.updateUI();
            }
        });
        panel.add(lbl_path);
        return panel;
    }
}
