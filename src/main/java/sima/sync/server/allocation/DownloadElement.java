package sima.sync.server.allocation;

import sima.sync.server.Instance;
import sima.sync.server.hash.Hash;
import sima.sync.server.hash.MD5Dispatcher;
import sima.sync.server.swing.Renderer;

import javax.swing.*;
import java.io.File;

public class DownloadElement {
    public enum Type {RECEIVE, SEND, BOTH}

    public enum Status {PENDING, CONFIRMED, PAUSED}

    public final File file;
    public final Type type;
    public Status status = Status.CONFIRMED;
    public int index;
    public float hashBar = 0.0f;
    public float syncBar = 0.0f;
    public final JPanel container;
    public final Renderer renderer;
    public long lastHashUpdate;
    public Hash hash;

    public DownloadElement(File file, Type type) {
        this.file = file;
        this.type = type;
        renderer = new Renderer(this);
        container = renderer.newContainer();
        if (type == Type.SEND) {
            MD5Dispatcher.addToQueue(this);
        } else if (type == Type.RECEIVE) {
            status = Status.PENDING;
        } else {
            throw new RuntimeException("Invalid DownloadElement.Type in constructor");
        }
    }

    public void repaint() {
        Instance.storage.repaint(index);
    }

    public void deliverHash(Hash h) {
        boolean firstUpdate = hash == null;
        boolean dupe = false;
        synchronized (Instance.hashStore) {
            if (!Instance.hashStore.add(h)) {
                dupe = true;//Use booleans so we escape the synchronized block as soon as possible.
            }
        }
        if (dupe) {
            Instance.log.info("Removing dupe element: " + file.getName());
            Instance.storage.removeElement(index);
        } else {
            hash = h;
            lastHashUpdate = System.currentTimeMillis();
            repaint();
            //TODO Inform the networking module.
        }
    }

    public void onRemove() {
        if (hash != null) synchronized (Instance.hashStore) {
            Instance.hashStore.remove(hash);
        }
    }

}
