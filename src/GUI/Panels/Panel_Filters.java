package GUI.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import CSV.Enums.DateFormats;
import Database.Filters.ComboLineFilters.*;
import Utils.FileUtils;
import Utils.Paths;

public final class Panel_Filters extends JPanel {
    private static Panel_Filters INSTANCE = new Panel_Filters();

    public static Panel_Filters getINSTANCE() {
        return INSTANCE;
    }

    private JButton btn_filter_ID, btn_filter_Location, btn_filter_Time;
    private Panel_Filters() {
        super();

        btn_filter_ID = new JButton("Create ID filter");
        btn_filter_Location = new JButton("Create Location Filter");
        btn_filter_Time = new JButton("Create Time Filter");


        JTextField txt_cLat, txt_cLon, txt_rad;
        txt_cLat = new JTextField(10);
        txt_cLon = new JTextField(10);
        txt_rad = new JTextField(10);
        JPanel panel_filterCreate_Location = new JPanel();
        panel_filterCreate_Location.add(new JLabel("Center Lat:"));
        panel_filterCreate_Location.add(txt_cLat);
        panel_filterCreate_Location.add(new JLabel("Center Lon:"));
        panel_filterCreate_Location.add(txt_cLon);
        panel_filterCreate_Location.add(new JLabel("Radius:"));
        panel_filterCreate_Location.add(txt_rad);
        panel_filterCreate_Location.setBorder(BorderFactory.createTitledBorder("Location Filter"));
        btn_filter_Location.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double centerLat, centerLon, radius;
                try {
                    centerLat = Double.parseDouble(txt_cLat.getText());
                    centerLon = Double.parseDouble(txt_cLon.getText());
                    radius = Double.parseDouble(txt_rad.getText());
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null,"Error parsing input.");
                    return;
                }
                Location_Filter loc_filter = new Location_Filter(centerLat, centerLon, radius);
                File filterFile = new File(Paths.SAVE_FILTERS+"Location_Filter__"
                        + (int)centerLat +"_"+(int)centerLon+"_"+(int)radius);
                try {
                    filterFile.createNewFile();
                    FileUtils.writeObjectToFile(filterFile, loc_filter);
                    JOptionPane.showMessageDialog(null,"Success! \nLocation: " + filterFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        panel_filterCreate_Location.add(btn_filter_Location);

        JPanel panel_filterCreate_Time = new JPanel();
        JTextField txt_dateFrom = new JTextField(10);
        JTextField txt_dateTo = new JTextField(10);
        panel_filterCreate_Time.add(new JLabel("Date from:"));
        panel_filterCreate_Time.add(txt_dateFrom);
        panel_filterCreate_Time.add(new JLabel("Date to:"));
        panel_filterCreate_Time.add(txt_dateTo);
        btn_filter_Time.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dateFrom, dateTo;
                try {
                    dateFrom = DateFormats.parse(txt_dateFrom.getText());
                    dateTo = DateFormats.parse(txt_dateTo.getText());
                    if(dateFrom == null || dateTo == null) {
                        JOptionPane.showMessageDialog(null,"Error parsing date.\n"+
                                "Corrent date formats available:\n\n"+
                                DateFormats.format1.toPattern()+"\n"+DateFormats.format2.toPattern()+"\n"+DateFormats.format3.toPattern());
                        return;
                    }
                } catch (Exception e123) {
                    JOptionPane.showMessageDialog(null,"Error parsing date.\n"+
                            "Corrent date formats available:\n\n"+
                            DateFormats.format1.toPattern()+"\n"+DateFormats.format2.toPattern()+"\n"+DateFormats.format3.toPattern());
                    return;
                }

                Time_Filter time_filter = new Time_Filter(dateFrom, dateTo);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy__HH_mm_ss");
                String fileNameDateString = simpleDateFormat.format(dateFrom)+"___"+simpleDateFormat.format(dateTo);
                File filterFile = new File(Paths.SAVE_FILTERS+"Time_Filter__"+fileNameDateString);
                try {
                    filterFile.createNewFile();
                    FileUtils.writeObjectToFile(filterFile,time_filter);
                    JOptionPane.showMessageDialog(null,"Success! \nLocation: " + filterFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel_filterCreate_Time.add(btn_filter_Time);
        panel_filterCreate_Time.setBorder(BorderFactory.createTitledBorder("Time Filter"));

        JTextField txt_substring = new JTextField(10);
        btn_filter_ID.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ID_Filter id_filter = new ID_Filter(txt_substring.getText());
                File filterFile = new File(Paths.SAVE_FILTERS+"ID_Filter__" + txt_substring.getText());
                try {
                    filterFile.createNewFile();
                    FileUtils.writeObjectToFile(filterFile, id_filter);
                    JOptionPane.showMessageDialog(null,"Success! \nLocation: " + filterFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        JPanel panel_filterCreate_ID = new JPanel();

        panel_filterCreate_ID.add(new JLabel("ID substring: "));
        panel_filterCreate_ID.add(txt_substring);

        panel_filterCreate_ID.add(btn_filter_ID);
        panel_filterCreate_ID.setBorder(BorderFactory.createTitledBorder("ID Filter"));

        add(panel_filterCreate_ID);
        add(panel_filterCreate_Location);
        add(panel_filterCreate_Time);













        JPanel panel_filterCreate_
    }
}
