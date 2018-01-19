package GUI.Panels;

import Algorithems.Algo1;
import Algorithems.Algo2;
import Algorithems.Data.Algo2UserInput;
import CSV.Combo.ComboLine;
import CSV.Data.GeoPoint;
import Database.Database;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Panel_Algorithems2 extends JPanel {

    private static Panel_Algorithems2 INSTANCE = new Panel_Algorithems2();
    private JButton btn_algo2;
    private JTextField txt_no_gps_line;
    private JTextField input_mac1, input_mac2, input_mac3, input_rssi1, input_rssi2, input_rssi3;
    private JRadioButton rbtn_noGps, rbtn_manual;
    private Panel_Algorithems2() {
        super();
        btn_algo2 = new JButton("Algorithem 2");
        btn_algo2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rbtn_noGps.isSelected())
                    this.noGpsAlgo2();
                else
                    this.manualAlgo2();
            }

            private void manualAlgo2() {
                String mac1 = input_mac1.getText();
                String mac2 = input_mac2.getText();
                String mac3 = input_mac3.getText();
                try {
                    double rssi1 = Double.parseDouble(input_rssi1.getText());
                    double rssi2 = Double.parseDouble(input_rssi2.getText());
                    double rssi3 = Double.parseDouble(input_rssi3.getText());

                    Algo2UserInput input1 = new Algo2UserInput(mac1, rssi1);
                    Algo2UserInput input2 = new Algo2UserInput(mac2, rssi2);
                    Algo2UserInput input3 = new Algo2UserInput(mac3, rssi3);
                    GeoPoint loc = Algo2.algo2(Database.getINSTANCE().data, input1, input2, input3);
                    JOptionPane.showMessageDialog(null, "Location: \n" + loc.toString());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Cant parse rssi input.");
                    return;
                }
            }


            private void noGpsAlgo2() {

                if(txt_no_gps_line == null || txt_no_gps_line.getText() == "" || Database.getINSTANCE() == null || Database.getINSTANCE().data == null) {
                    JOptionPane.showMessageDialog(null, "Please choose no gps line,");
                    return;
                }
                try {
                    String line = txt_no_gps_line.getText();
                    ComboLine comboLine = new ComboLine(line.split(","));
                    if(comboLine.size() <= 2) {
                        JOptionPane.showMessageDialog(null, "No gps line must be size above 2.");
                        return;
                    }
                    Algo2UserInput input1, input2, input3;
                    input1 = new Algo2UserInput(comboLine.get(0).mac, comboLine.get(0).wifiSpectrum.rssi);
                    input2 = new Algo2UserInput(comboLine.get(1).mac, comboLine.get(1).wifiSpectrum.rssi);
                    input3 = new Algo2UserInput(comboLine.get(2).mac, comboLine.get(2).wifiSpectrum.rssi);

                    GeoPoint loc = Algo2.algo2(Database.getINSTANCE().data, input1, input2, input3);
                    JOptionPane.showMessageDialog(null, "Location: \n" + loc.toString());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

        });

        txt_no_gps_line = new JTextField(10);
        JPanel panel_input_manual_wrapper = new JPanel();
        panel_input_manual_wrapper.setLayout(new GridLayout(0, 4, 25, 5));
        input_mac1 = new JTextField(10);
        input_mac2 = new JTextField(10);
        input_mac3 = new JTextField(10);
        input_rssi1 = new JTextField(10);
        input_rssi2 = new JTextField(10);
        input_rssi3 = new JTextField(10);

        panel_input_manual_wrapper.add(new JLabel("MAC 1:"));
        panel_input_manual_wrapper.add(input_mac1);
        panel_input_manual_wrapper.add(new JLabel("RSSI 1:"));
        panel_input_manual_wrapper.add(input_rssi1);

        panel_input_manual_wrapper.add(new JLabel("MAC 2:"));
        panel_input_manual_wrapper.add(input_mac2);
        panel_input_manual_wrapper.add(new JLabel("RSSI 2:"));
        panel_input_manual_wrapper.add(input_rssi2);

        panel_input_manual_wrapper.add(new JLabel("MAC 3:"));
        panel_input_manual_wrapper.add(input_mac3);
        panel_input_manual_wrapper.add(new JLabel("RSSI 3:"));
        panel_input_manual_wrapper.add(input_rssi3);

        rbtn_manual = new JRadioButton();
        JPanel panel_input_manual_wrapper_wrapper = new JPanel();
        panel_input_manual_wrapper_wrapper.add(panel_input_manual_wrapper);
        panel_input_manual_wrapper_wrapper.add(rbtn_manual);
        panel_input_manual_wrapper_wrapper.setBorder(BorderFactory.createEtchedBorder());

        JPanel panel_input_noGpsLine_wrapper = new JPanel();
        rbtn_noGps = new JRadioButton();
        panel_input_noGpsLine_wrapper.add(rbtn_noGps);
        panel_input_noGpsLine_wrapper.add(new JLabel("NO GPS Line:"));
        panel_input_noGpsLine_wrapper.add(txt_no_gps_line);
        panel_input_noGpsLine_wrapper.setBorder(BorderFactory.createEtchedBorder());

        JPanel panel_all_wrapper = new JPanel();
        panel_all_wrapper.setLayout(new GridLayout(0,1));
        panel_all_wrapper.add(panel_input_noGpsLine_wrapper);
        panel_all_wrapper.add(panel_input_manual_wrapper_wrapper);
        add(panel_all_wrapper);
        add(btn_algo2);

        txt_no_gps_line = new JTextField(20);

        rbtn_noGps.setSelected(true);
        rbtn_noGps.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rbtn_noGps.setSelected(true);
                rbtn_manual.setSelected(false);
            }
        });

        rbtn_manual.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rbtn_manual.setSelected(true);
                rbtn_noGps.setSelected(false);
            }
        });
    }

    public static Panel_Algorithems2 getINSTANCE() {
        return INSTANCE;
    }
}
