package Examples;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class IOUser {
    public static void openFile(File outFile) throws IOException {
        System.out.println("Openning file...");
        Desktop.getDesktop().open(outFile);
    }

    public static File getFileByUser() {
        File workingDirectory = new File(System.getProperty("user.dir"));

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.showOpenDialog(null);
        return fileChooser.getSelectedFile();

    }

    public static File getFolderByUser() {

        File workingDirectory = new File(System.getProperty("user.dir"));

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showOpenDialog(null);
        return fileChooser.getSelectedFile();
    }
}
