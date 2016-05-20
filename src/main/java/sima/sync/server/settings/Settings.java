package sima.sync.server.settings;

import sima.sync.server.Constants;
import sima.sync.server.Instance;

import java.io.*;
import java.util.concurrent.locks.LockSupport;

import static sima.sync.server.Instance.gson;

public class Settings {

    private String openCurrentDir = System.getProperty("user.home");
    private transient ASyncSave async = new ASyncSave();
    private transient static File cfg = new File(Constants.PATH, "SimaSync-Server.json");
    private transient final Object saveLock = new Object();

    public Settings() {/*No-arg constructor*/}

    public File getOpenCurrentDir() {
        File f = new File(openCurrentDir);
        if (!f.isDirectory()) {
            openCurrentDir = System.getProperty("user.home");
            save();
        }
        return f;
    }

    public void setOpenCurrentDir(File openCurrentDir) {
        if (!this.openCurrentDir.equals(openCurrentDir.getPath())) {
            this.openCurrentDir = openCurrentDir.getPath();
            save();
        }
    }

    public void save() {
        async.schedule();
    }

    public static Settings init() {
        if (cfg.exists()) {
            Instance.log.info("Loading saved config file...");
            try (BufferedReader json = new BufferedReader(new FileReader(cfg))) {
                return gson.fromJson(json, Settings.class);
            } catch (IOException e) {
                Instance.log.fatal(e);
                System.exit(-1);
            }
        } else {
            Instance.log.info("Did not find a config file, so creating a new one...");
            Settings s = new Settings();
            internalSave(s);
            return s;
        }
        return null;
    }

    public void onShutdown() {
        async.loop = false;
        LockSupport.unpark(async);
        try {
            async.join();
        } catch (InterruptedException ignored) {
        }
    }

    private static void internalSave(Settings s) {
        synchronized (s.saveLock) {
            Instance.log.debug("Saving settings...");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(cfg))) {
                gson.toJson(s, writer);
                writer.flush();
            } catch (IOException e) {
                Instance.log.fatal(e);
                System.exit(-1);
            }
        }
    }

    private class ASyncSave extends Thread {
        private boolean loop = true;
        private boolean work = false;

        public ASyncSave() {
            super("ASyncSave");
            setDaemon(true);
            Instance.log.debug("Staring ASyncSave thread.");
            start();
        }

        protected void schedule() {
            if (work) return;
            work = true;
            LockSupport.unpark(this);
        }

        @SuppressWarnings("InfiniteLoopStatement")
        @Override
        public void run() {
            while (loop) {
                LockSupport.park();
                if (work) {
                    work = false;
                    internalSave(Settings.this);
                }
            }
            Instance.log.info("ASyncSave thread exiting...");
        }
    }
}
