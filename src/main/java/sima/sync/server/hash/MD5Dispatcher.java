package sima.sync.server.hash;

import sima.sync.server.Constants;
import sima.sync.server.Instance;
import sima.sync.server.allocation.DownloadElement;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MD5Dispatcher {
    private static final BlockingQueue<DownloadElement> queue = new LinkedBlockingQueue<>();
    private static final MD5Thread[] threads = new MD5Thread[Constants.LOGICAL_CORES];
    private static boolean updated = true;

    public static void init() {
        for (int i = 0; i < Constants.LOGICAL_CORES; i++) {
            threads[i] = new MD5Thread(i, queue);
        }
    }

    public static void addToQueue(DownloadElement add) {
        queue.add(add);
        updateCount();
    }

    /*package*/
    static void updateCount() {
        if (updated) {
            updated = false;
            SwingUtilities.invokeLater(() -> {
                Instance.screen.fileBacklog.setText(queue.size() + " file(s) pending");
                updated = true;
            });
        }
    }

    public static String genHashHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash).toLowerCase();
    }
}
