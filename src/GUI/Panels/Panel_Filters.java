package GUI.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import CSV.Enums.DateFormats;
import Filters.Base.AbstractFilter;
import Filters.ComboLineFilters.*;
import Filters.OperationFilters.And_Filter;
import Filters.OperationFilters.Not_Filter;
import Filters.OperationFilters.Or_Filter;
import Utils.FileUtils;
import Utils.Paths;

public final class Panel_Filters extends JPanel {
    private static Panel_Filters INSTANCE = new Panel_Filters();

    public static Panel_Filters getINSTANCE() {
        return INSTANCE;
    }

    private AbstractFilter and_filter1, and_filter2,
        or_filter1, or_filter2,
        not_filter1;
    private String and_filter1_String, and_filter2_String,
        or_filter1_String, or_filter2_String,
        not_filter_String;

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












/** And filter**/
        JPanel panel_filterCreate_AndFilter = new JPanel();
        JButton btn_filterCreate_And_ChooseFilter_First = new JButton("Choose Filter 1");
        JButton btn_filterCreate_And_ChooseFilter_Second = new JButton("Choose Filter 2");
        JButton btn_filterCreate_AndFilter = new JButton("Create");
        JLabel lbl_and_firstFilter = new JLabel("null");
        JLabel lbl_and_secondFilter = new JLabel("null");
        panel_filterCreate_AndFilter.add(btn_filterCreate_AndFilter);

        panel_filterCreate_AndFilter.add(new JLabel("Name:"));
        panel_filterCreate_AndFilter.add(lbl_and_firstFilter);
        panel_filterCreate_AndFilter.add(btn_filterCreate_And_ChooseFilter_First);

        panel_filterCreate_AndFilter.add(new JLabel("Name:"));
        panel_filterCreate_AndFilter.add(lbl_and_secondFilter);
        panel_filterCreate_AndFilter.add(btn_filterCreate_And_ChooseFilter_Second);

        panel_filterCreate_AndFilter.setBorder(BorderFactory.createTitledBorder("AND Filter"));

        btn_filterCreate_AndFilter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                And_Filter and_filter = new And_Filter(and_filter1, and_filter2);
                File filterFile = new File(Paths.SAVE_FILTERS+"AND_Filter_" + and_filter1_String + "__" + and_filter2_String);
                try {
                    filterFile.createNewFile();
                    FileUtils.writeObjectToFile(filterFile, and_filter);
                    JOptionPane.showMessageDialog(null,"Success! \nLocation: " + filterFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        btn_filterCreate_And_ChooseFilter_First.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File filterFile = FileUtils.getFileFromUser();
                try {
                    and_filter1 = (AbstractFilter) FileUtils.readObjectFromFile(filterFile);
                    and_filter1_String = filterFile.getName();
                    lbl_and_firstFilter.setText(and_filter1_String);
                    lbl_and_firstFilter.updateUI();
                } catch (Throwable e1) {
                    e1.printStackTrace();
                }

            }
        });

        btn_filterCreate_And_ChooseFilter_Second.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File filterFile = FileUtils.getFileFromUser();
                try {
                    and_filter2 = (AbstractFilter) FileUtils.readObjectFromFile(filterFile);
                    and_filter2_String = filterFile.getName();
                    lbl_and_secondFilter.setText(and_filter2_String);
                    lbl_and_secondFilter.updateUI();
                } catch (Throwable e1) {
                    e1.printStackTrace();
                }
            }
        });

        add(panel_filterCreate_AndFilter);













/** Or Filter**/
        JPanel panel_filterCreate_OrFilter = new JPanel();
        JButton btn_filterCreate_Or_ChooseFilter_First = new JButton("Choose Filter 1");
        JButton btn_filterCreate_Or_ChooseFilter_Second = new JButton("Choose Filter 2");
        JButton btn_filterCreate_OrFilter = new JButton("Create");
        JLabel lbl_firstFilter_Or = new JLabel("null");
        JLabel lbl_secondFilter_Or = new JLabel("null");
        panel_filterCreate_OrFilter.add(btn_filterCreate_OrFilter);

        panel_filterCreate_OrFilter.add(new JLabel("Name:"));
        panel_filterCreate_OrFilter.add(lbl_firstFilter_Or);
        panel_filterCreate_OrFilter.add(btn_filterCreate_Or_ChooseFilter_First);

        panel_filterCreate_OrFilter.add(new JLabel("Name:"));
        panel_filterCreate_OrFilter.add(lbl_secondFilter_Or);
        panel_filterCreate_OrFilter.add(btn_filterCreate_Or_ChooseFilter_Second);

        panel_filterCreate_OrFilter.setBorder(BorderFactory.createTitledBorder("OR Filter"));

        btn_filterCreate_OrFilter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Or_Filter or_filter = new Or_Filter(or_filter1, or_filter2);
                File filterFile = new File(Paths.SAVE_FILTERS+"OR_Filter_" + or_filter1_String + "__" + or_filter2_String);
                try {
                    filterFile.createNewFile();
                    FileUtils.writeObjectToFile(filterFile, or_filter);
                    JOptionPane.showMessageDialog(null,"Success! \nLocation: " + filterFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        btn_filterCreate_Or_ChooseFilter_First.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File filterFile = FileUtils.getFileFromUser();
                try {
                    or_filter1 = (AbstractFilter) FileUtils.readObjectFromFile(filterFile);
                    or_filter1_String = filterFile.getName();
                    lbl_firstFilter_Or.setText(or_filter1_String);
                    lbl_firstFilter_Or.updateUI();
                } catch (Throwable e1) {
                    e1.printStackTrace();
                }

            }
        });

        btn_filterCreate_Or_ChooseFilter_Second.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File filterFile = FileUtils.getFileFromUser();
                try {
                    or_filter2 = (AbstractFilter) FileUtils.readObjectFromFile(filterFile);
                    or_filter2_String = filterFile.getName();
                    lbl_secondFilter_Or.setText(or_filter2_String);
                    lbl_secondFilter_Or.updateUI();
                } catch (Throwable e1) {
                    e1.printStackTrace();
                }
            }
        });

        add(panel_filterCreate_OrFilter);



        JButton btn_open_filters_folder = new JButton("Open Filters Folder");
        add(btn_open_filters_folder);
        btn_open_filters_folder.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileUtils.openFile(new File(Paths.SAVE_FILTERS+"/"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });



/** Not Filter **/
        JPanel panel_filterCreate_NotFilter = new JPanel();
        JButton btn_filterCreate_Not_ChooseFilter_First = new JButton("Choose Filter");
        JButton btn_filterCreate_NotFilter = new JButton("Create");
        JLabel lbl_notFilter_FileName = new JLabel("null");

        btn_filterCreate_Not_ChooseFilter_First.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File filterFile = FileUtils.getFileFromUser();
                try {
                    not_filter1 = (AbstractFilter) FileUtils.readObjectFromFile(filterFile);
                    not_filter_String = filterFile.getName();
                    lbl_notFilter_FileName.setText(not_filter_String);
                    lbl_notFilter_FileName.updateUI();
                } catch (Throwable e1) {
                    e1.printStackTrace();
                }
            }
        });

        btn_filterCreate_NotFilter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Not_Filter not_filter = new Not_Filter(not_filter1);
                File filterFile = new File(Paths.SAVE_FILTERS+"NOT_Filter_" + not_filter_String);
                try {
                    filterFile.createNewFile();
                    FileUtils.writeObjectToFile(filterFile, not_filter);
                    JOptionPane.showMessageDialog(null,"Success! \nLocation: " + filterFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        panel_filterCreate_NotFilter.add(btn_filterCreate_NotFilter);
        panel_filterCreate_NotFilter.add(lbl_notFilter_FileName);
        panel_filterCreate_NotFilter.add(btn_filterCreate_Not_ChooseFilter_First);
        panel_filterCreate_NotFilter.setBorder(BorderFactory.createTitledBorder("NOT Filter"));

        add(panel_filterCreate_NotFilter);
    } //cons



}
