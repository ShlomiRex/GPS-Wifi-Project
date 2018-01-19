package GUI;

import GUI.Panels.*;

import javax.swing.*;
import java.awt.*;

public final class MainWindow extends JFrame {
    private static MainWindow INSTANCE = new MainWindow();
    public static MainWindow getINSTANCE() {
        return INSTANCE;
    }

    public MainWindow() {
        super();
        //mainPanel = new MainPanel();

        JTabbedPane mainPanel = new JTabbedPane();

        JPanel panel_io_wrapper = new JPanel();
        panel_io_wrapper.setLayout(new GridLayout(0, 1));
        panel_io_wrapper.add(Panel_IO.getINSTANCE());

        mainPanel.addTab("Database", Panel_Database.getINSTANCE());
        mainPanel.addTab("I/O", panel_io_wrapper);
        mainPanel.addTab("Login", Panel_Login.getInstance());
        mainPanel.addTab("Debug", Panel_Debug.getINSTANCE());
        mainPanel.addTab("Algorithem 1", Panel_Algorithems1.getINSTANCE());
        mainPanel.addTab("Algorithem 2", Panel_Algorithems2.getINSTANCE());
        mainPanel.addTab("Filters", Panel_Filters.getINSTANCE());

        add(mainPanel);

        setSize(800, 600);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
