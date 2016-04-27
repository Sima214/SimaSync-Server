package sima.sync.server.hash;

import sima.sync.server.Main;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MD5Dispatcher {
    private static final BlockingQueue<File> queue = new LinkedBlockingQueue<>();
    private static final MD5Thread[] threads = new MD5Thread[Main.logicalCores];

    public static void init() {
        for (int i = 0; i < Main.logicalCores; i++) {
            MD5Thread cur = new MD5Thread(i, queue);
            cur.start();
            threads[i] = cur;
        }
    }
}
