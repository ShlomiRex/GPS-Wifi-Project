package GUI;

import CSV.Combo.ComboCSV;
import CSV.Wigle.WigleCSV;
import Utils.FileUtils;
import Utils.Paths;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainPanel extends JPanel {
    private JButton
            btn_IOInput_ChooseFolder, btn_IOInput_ChooseFile_Combo,
            btn_Debug_Print, btn_Debug_Open, btn_Debug_OpenDatabase,
            btn_Path_CopyToClipboard,
            btn_ComboOptions__AppendTo;

    private JLabel lbl_Path_Label, lbl_Path_Text, lbl_Chosen_Label, lbl_Chosen_Text;
    private File selectedFile;
    private FileType selectedFileType;
    enum FileType {
        Wigle, Combo, Folder;
    }

    private Database database;
    private JPanel panel_Debug, panel_Input, panel_ComboOptions, panel_Path;

    public MainPanel() throws IOException {
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initDatabase();

        panel_Input = initPanel_IOInput();
        add(panel_Input);

        panel_Path = initPanel_Path();
        add(panel_Path);
        panel_Path.setVisible(false);

        panel_Debug = initPanel_Debug();
        add(panel_Debug);
        panel_Debug.setVisible(false);

        panel_ComboOptions = initPanel_ComboOptions();
        add(panel_ComboOptions);
        panel_ComboOptions.setVisible(false);
    }

    private void initDatabase() throws IOException {
        String defaultFileName = "Database.csv";
        File defaultFileOut = new File(Paths.OUT + defaultFileName);
        defaultFileOut.createNewFile();

        database = new Database(defaultFileOut, Database.Type.Wigle);
    }

    private JPanel initPanel_ComboOptions() {
        JPanel panel = new JPanel();
        btn_ComboOptions__AppendTo = new JButton("Append To Database");
        btn_ComboOptions__AppendTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedFileType == FileType.Wigle) {
                    try {
                        database.append(new WigleCSV(selectedFile.getAbsolutePath()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else if(selectedFileType == FileType.Combo) {
                    try {
                        database.append(new ComboCSV(selectedFile.getAbsoluteFile()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        panel.add(btn_ComboOptions__AppendTo);
        panel.setBorder(BorderFactory.createTitledBorder("CSV Combo Options"));

        return panel;
    }

    private JPanel initPanel_Debug() {
        JPanel panel = new JPanel();
        btn_Debug_Print = new JButton("Print to console selected");
        btn_Debug_Print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedFile == null) {
                    System.err.println("Cannot print to console, null selected.");
                    return;
                }

                if(selectedFile.isFile()) {
                    System.out.println("File: " + selectedFile.getAbsolutePath());
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(selectedFile));
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
                    File[] files = selectedFile.listFiles();
                    for(File file : files) {
                        System.out.println(file.getName());
                    }
                }
            }
        });

        lbl_Chosen_Label = new JLabel("Chosen: ");
        lbl_Chosen_Text = new JLabel("null");

        btn_Debug_Open = new JButton("Open selected");
        btn_Debug_Open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedFile == null || selectedFile.getAbsolutePath().equals("")){
                    System.err.println("Cannot open null file.");
                    return;
                }
                try {
                    FileUtils.openFile(selectedFile);
                    System.out.println("Openning: " + lbl_Path_Text.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Cannot open path: " + lbl_Path_Text.getText(),
                            "Cannot open path",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btn_Debug_OpenDatabase = new JButton("Open database");
        btn_Debug_OpenDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileUtils.openFile((File)database);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        panel.add(btn_Debug_OpenDatabase);
        panel.add(new JToolBar.Separator(new Dimension(25,5)));
        panel.add(btn_Debug_Open);
        panel.add(btn_Debug_Print);
        panel.add(lbl_Chosen_Label);
        panel.add(lbl_Chosen_Text);


        panel.setBorder(BorderFactory.createTitledBorder("Debug"));
        return panel;
    }

    private JPanel initPanel_IOInput() {
        JPanel panel = new JPanel();
        btn_IOInput_ChooseFolder = new JButton("Choose folder");
        btn_IOInput_ChooseFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File folder = Utils.FileUtils.getFolderFromUser();
                if(folder == null) {
                    System.err.println("Chosen folder is null.");
                    return;
                }
                lbl_Path_Text.setText(folder.getAbsolutePath());
                selectedFile = folder;
                lbl_Chosen_Text.setText("Folder");
                selectedFileType = FileType.Folder;

                setPanelsVisible();
                panel_ComboOptions.setVisible(false);
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
                lbl_Path_Text.setText(file.getAbsolutePath());
                try {
                    ComboCSV comboCSV = new ComboCSV(file);
                    lbl_Chosen_Text.setText("Combo CSV");
                    selectedFile = comboCSV;
                    selectedFileType = FileType.Combo;

                    setPanelsVisible();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "File chosen is not valid Combo File.",
                            "Combo conversion error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(btn_IOInput_ChooseFolder);
        panel.add(btn_IOInput_ChooseFile_Combo);
        panel.setBorder(BorderFactory.createTitledBorder("I/O Input"));
        return panel;
    }

    private void setPanelsVisible() {
        panel_Debug.setVisible(true);
        panel_ComboOptions.setVisible(true);
        panel_Path.setVisible(true);
    }

    private JPanel initPanel_Path() {
        JPanel panel = new JPanel();

        lbl_Path_Label = new JLabel("Path: ");
        lbl_Path_Text = new JLabel("");


        btn_Path_CopyToClipboard = new JButton("Copy path");

        btn_Path_CopyToClipboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String whatToCopy = lbl_Path_Text.getText();
                StringSelection stringSelection = new StringSelection(whatToCopy);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
        });

        panel.add(lbl_Path_Label);
        panel.add(lbl_Path_Text);
        panel.add(btn_Path_CopyToClipboard);

        panel.setBorder(BorderFactory.createTitledBorder("Chosen Path"));
        return panel;
    }
}
