package sima.sync.server.allocation;

import sima.sync.server.Instance;
import sima.sync.server.hash.MD5Dispatcher;
import sima.sync.server.swing.Renderer;

import java.io.File;

public class DownloadElement {
    public enum Type {RECEIVE, SEND, BOTH}

    public enum Status {PENDING, CONFIRMED}

    public final File file;
    public final Type type;
    public Status status = Status.CONFIRMED;
    public int index;
    public float hashBar = 0.0f;
    public float syncBar = 0.0f;
    public final Renderer renderer;
    public long lastHashUpdate;
    public byte[] hash;

    public DownloadElement(File file, Type type) {
        this.file = file;
        this.type = type;
        renderer = new Renderer(this);
        if (type == Type.SEND) {
            MD5Dispatcher.addToQueue(this);
        } else {
            status = Status.PENDING;
        }
    }

    public void repaint() {
        Instance.storage.repaint(index, this);
    }

    public void deliverHash(byte[] digest) {
        hash = digest;
        lastHashUpdate = System.currentTimeMillis();
        Instance.log.info(MD5Dispatcher.genHashHex(hash));
        repaint();
    }

}
