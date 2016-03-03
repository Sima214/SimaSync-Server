package sima.sync.server.md5;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;

public class MD5Thread extends Thread {
    private final BlockingQueue<IDigestable> queue;

    public MD5Thread(int threadIndex, BlockingQueue<IDigestable> queue) {
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
                queue.take().perform(digest);

            } catch (InterruptedException e) {/*Do nothing if interrupted*/}
        }
    }

    private static MessageDigest generateDigest() {
        try {
            return MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {/*TODO Exit gracefully if this happens*/}
        return null;
    }
}
