package GUI;

import GUI.Logic.Selected;

import java.io.IOException;

public class GUI {
    public MainWindow mainWindow;
    public static Selected selected;
    public GUI() throws IOException {
        mainWindow = new MainWindow();
    }

    public void setVisible(boolean option) {
        mainWindow.setVisible(option);
    }
}
