package GUI.Panels;

import Algorithems.Algo1;
import CSV.Data.GeoPoint;
import Database.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Panel_Algorithems1 extends JPanel {

    private static Panel_Algorithems1 INSTANCE = new Panel_Algorithems1();
    private JButton btn_algo1;
    private JTextField txt_mac;
    private Panel_Algorithems1() {
        super();
        btn_algo1 = new JButton("Algorithem 1");
        btn_algo1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txt_mac == null || txt_mac.getText() == "" || Database.getINSTANCE() == null || Database.getINSTANCE().data == null) {
                    JOptionPane.showMessageDialog(null, "Please choose mac,");
                    return;
                }
                String mac = txt_mac.getText();
                GeoPoint loc = Algo1.algo1(Database.getINSTANCE().data, mac, 3);
                JOptionPane.showMessageDialog(null, "Location: \n" + loc.toString());
            }
        });

        add(btn_algo1);
        add(new JLabel("MAC:"));
        txt_mac = new JTextField(10);
        add(txt_mac);
    }

    public static Panel_Algorithems1 getINSTANCE() {
        return INSTANCE;
    }
}
