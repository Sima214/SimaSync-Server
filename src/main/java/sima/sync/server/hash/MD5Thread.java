package sima.sync.server.hash;


import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;

public class MD5Thread extends Thread {
    private final BlockingQueue<File> queue;

    public MD5Thread(int threadIndex, BlockingQueue<File> queue) {
        super("HashCalculator #" + threadIndex);
        setDaemon(true);
        this.queue = queue;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        MessageDigest digest = generateDigest();
        while (true) {
            try {
                File job = queue.take();
                //TODO generate hash from file and then added to the list.
            } catch (InterruptedException e) {/*Do nothing if interrupted*/}
        }
    }

    private static MessageDigest generateDigest() {
        try {
            return MessageDigest.getInstance("hash");
        } catch (NoSuchAlgorithmException e) {/*TODO Exit gracefully if this happens*/}
        return null;
    }
}
