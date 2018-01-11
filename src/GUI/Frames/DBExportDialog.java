package GUI.Frames;

import javax.swing.*;
import java.awt.*;

public class DBExportDialog {

    public static void show() {
        JPanel whatToShow = new JPanel();
        whatToShow.setLayout(new BorderLayout());
        whatToShow.add(new JLabel("What format to export?"));
        whatToShow.add(new JButton("What format to asdadsasdadsadasd?"));

        JOptionPane optionPane = new JOptionPane("What format to export?", JOptionPane.QUESTION_MESSAGE);
    }
}
