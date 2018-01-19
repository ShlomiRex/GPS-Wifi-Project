package Utils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;

public abstract class FileUtils {
    public static String getExtension(File file) {
        String extension = "";

        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i+1);
        }
        return extension;
    }

    /**
     * May return null.
     * @return
     */
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

    /**
     * Opens in explorer the file / folder.
     * @param file
     */
    public static void openFile(File file) throws IOException {
        if(file == null)
            return;

        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
    }

    public static void writeObjectToFile(File file, Object object) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static Object readObjectFromFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return ois.readObject();
    }
}
