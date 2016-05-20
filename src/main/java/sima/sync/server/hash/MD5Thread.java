package sima.sync.server.hash;


import sima.sync.server.Instance;
import sima.sync.server.allocation.DownloadElement;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;

public class MD5Thread extends Thread {
    public final int threadID;
    private final BlockingQueue<DownloadElement> queue;

    public MD5Thread(int threadIndex, BlockingQueue<DownloadElement> queue) {
        super("HashCalculator #" + threadIndex);
        setDaemon(true);
        this.queue = queue;
        threadID = threadIndex;
        Instance.log.debug("Starting thread named: " + getName());
        start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        byte[] cache = new byte[1024 * 64];
        MessageDigest digest = generateDigest();
        int lastRead;
        float curHashBar;
        while (true) try {
            if (queue.size() == 0) {
                setSleepingState();
            }
            DownloadElement job = queue.take();
            File file = job.file;
            setRunningState(file);
            MD5Dispatcher.updateCount();
            final double fileLength = file.length();
            double position = 0;
            try (FileInputStream stream = new FileInputStream(file)) {
                do {
                    lastRead = stream.read(cache);
                    if (lastRead > 0) {
                        digest.update(cache, 0, lastRead);
                        position += lastRead;
                        curHashBar = (float) (position / fileLength);
                        if ((curHashBar - job.hashBar) >= 0.05) {
                            job.hashBar = curHashBar;
                            job.repaint();
                        }
                    }
                } while (lastRead == cache.length);
            } catch (IOException e) {
                Instance.log.error(e);
            }
            job.deliverHash(new Hash(job, digest.digest()));
        } catch (InterruptedException e) {/*Do nothing if interrupted*/}
    }

    private static MessageDigest generateDigest() {
        try {
            return MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            Instance.log.fatal(e);
            System.exit(-1);
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
