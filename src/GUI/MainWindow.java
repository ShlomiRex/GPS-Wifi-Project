package GUI;

import javax.swing.*;
import java.io.IOException;

public class MainWindow extends JFrame {
    private MainPanel mainPanel;
    public MainWindow() throws IOException {
        super();
        mainPanel = new MainPanel();
        add(mainPanel);

        setSize(800, 600);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
