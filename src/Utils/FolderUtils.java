package Utils;

import CSV.Wigle.WigleCSV;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class FolderUtils {

    /**
     *
     * @param folder
     * @return - Random file from given folder. Return null if no files.
     */
    public static File getRandomFile(File folder) {
        File result = null;
        Random rnd = new Random();
        File[] files = folder.listFiles();
        for(int i = 0; i < files.length; i++) {
            if(files[i].isFile()) {
                result = files[i];
                break;
            }
        }
        return result;
    }

    public static List<WigleCSV> getWigles(File folderOfCSVs) {
        if(folderOfCSVs.isFile())
            throw new IllegalArgumentException("The file: " + folderOfCSVs + " is not a folder!");

        ArrayList<WigleCSV> result = new ArrayList<>();
        WigleCSV wigleCSV;
        List<File> allFiles = Arrays.asList(folderOfCSVs.listFiles());

        for(File f : allFiles) {
            try {
                wigleCSV = new WigleCSV(f.getAbsolutePath());
                result.add(wigleCSV);
            } catch (Throwable e) {
                //do nothing
            }
        }


        return result;
    }
}
