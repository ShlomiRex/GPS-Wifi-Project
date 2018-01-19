package GUI;

import Database.Database;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.IOException;

public class OpenGUI {
    public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        MainWindow.getINSTANCE().setVisible(true);
    }
}
