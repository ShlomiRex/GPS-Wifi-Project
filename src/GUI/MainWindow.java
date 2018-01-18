package GUI;

import GUI.Panels.Panel_Login;

import javax.swing.*;
import java.io.IOException;

public class MainWindow extends JFrame {
    private MainPanel mainPanel;
    private Panel_Login login;
    public MainWindow() throws IOException {
        super();
        mainPanel = new MainPanel();
        login = new Panel_Login();

        JTabbedPane main = new JTabbedPane();
        main.addTab("Main",mainPanel);
        main.addTab("Login", login);

        add(main);

        setSize(800, 600);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
