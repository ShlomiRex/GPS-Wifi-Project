package KML;

import CSV.Wigle.WigleCSV;
import Utils.FileUtils;
import Utils.Paths;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class KMLTest {

    @Test
    public void test() throws IOException {
        File chosenFile = FileUtils.getFileFromUser();
        String path = chosenFile.getAbsolutePath();
        WigleCSV wigleCSV = new WigleCSV(path);

        KML kml = new KML(Paths.OUT_TESTS+"/kmlTest.kml", wigleCSV);
    }

}