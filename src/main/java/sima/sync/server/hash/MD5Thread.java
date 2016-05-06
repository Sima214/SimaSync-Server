package sima.sync.server.hash;


import sima.sync.server.Instance;
import sima.sync.server.Main;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;

public class MD5Thread extends Thread {
    public final int threadID;
    private final BlockingQueue<File> queue;

    public MD5Thread(int threadIndex, BlockingQueue<File> queue) {
        super("HashCalculator #" + threadIndex);
        setDaemon(true);
        this.queue = queue;
        threadID = threadIndex;
        Instance.log.debug("Starting thread named: " + getName());
        start();
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();//TODO temp

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        byte[] cache = new byte[1024 * 64];
        MessageDigest digest = generateDigest();
        int lastRead;
        while (true) {
            try {
                File job = queue.take();
                MD5Dispatcher.updateCount();
                setRunningState(job);
                try (FileInputStream stream = new FileInputStream(job)) {
                    do {
                        lastRead = stream.read(cache);
                        //noinspection ConstantConditions
                        digest.update(cache, 0, lastRead);
                    } while (lastRead == cache.length);
                } catch (IOException e) {
                    Instance.log.error(e);
                }
                //noinspection ConstantConditions
                byte[] hash = digest.digest();
                //TODO publish results
                char[] hexChars = new char[hash.length * 2];
                for (int j = 0; j < hash.length; j++) {
                    int v = hash[j] & 0xFF;
                    hexChars[j * 2] = hexArray[v >>> 4];
                    hexChars[j * 2 + 1] = hexArray[v & 0x0F];
                }
                String d = new String(hexChars);
                Instance.log.debug(getName() + " generated hash of " + job.getName() + "with value" + d);
                setSleepingState();
            } catch (InterruptedException e) {/*Do nothing if interrupted*/}
        }
    }

    private static MessageDigest generateDigest() {
        try {
            return MessageDigest.getInstance("hash");
        } catch (NoSuchAlgorithmException e) {
            Instance.log.fatal(e);
            Main.exit(-1);
            return null;
        }
    }

    private void setRunningState(File f) {
        SwingUtilities.invokeLater(() -> Instance.screen.threadState[threadID].setText("Calculating hash of " + f.getName()));
    }

    private void setSleepingState() {
        SwingUtilities.invokeLater(() -> Instance.screen.threadState[threadID].setText("Sleeping..."));
    }
}
