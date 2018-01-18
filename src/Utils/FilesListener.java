package Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class FilesListener extends Thread {
    private boolean running;
    private ArrayList<File> files;
    public static final long SLEEP = 1000;
    public FilesListener(List<File> filesToListenTo) {
        files.addAll(filesToListenTo);
    }

    @Override
    public synchronized void start() {
        super.start();
        while(running) {
            try {
                sleep(SLEEP);
            } catch (InterruptedException e) {
            }


        }
    }
}
