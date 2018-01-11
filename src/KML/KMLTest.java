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
        String path = Paths.DATA_MOODLE_DATA_LENOVO + "/WigleWifi_20171027162929.csv";
        WigleCSV wigleCSV = new WigleCSV(path);
        System.out.println(wigleCSV.csvData.size());

        KML kml = new KML(Paths.OUT_TESTS+"/kmlTest.kml", wigleCSV);
        kml.generateKML();
    }

}