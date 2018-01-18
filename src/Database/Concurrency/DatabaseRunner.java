package Database.Concurrency;

import Database.Database;

public abstract class DatabaseRunner extends Thread {
    private Runnable task;
    public DatabaseRunner(Runnable task) {
        this.task = task;
    }
}
