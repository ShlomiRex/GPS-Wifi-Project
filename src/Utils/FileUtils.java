package Utils;

import javax.swing.*;
import java.io.File;

public abstract class FileUtils {
    public static String getExtension(File file) {
        String extension = "";

        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i+1);
        }
        return extension;
    }

    public static File getFileFromUser() {
        File result;
        //Create a file chooser
        final JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("Choose a file");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        chooser.showOpenDialog(null);
        //In response to a button click:
        return chooser.getSelectedFile();
    }

    public static File getFolderFromUser() {
        File result;
        //Create a file chooser
        final JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("Choose a folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        chooser.showOpenDialog(null);
        //In response to a button click:
        return chooser.getSelectedFile();
    }

}
