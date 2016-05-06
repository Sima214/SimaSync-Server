package sima.sync.server.allocation;

import javax.swing.*;
import java.util.ArrayList;

public class DownloadStorage extends AbstractListModel<DownloadElement> {
    private ArrayList<DownloadElement> data = new ArrayList<>();

    @Override
    public synchronized int getSize() {
        return data.size();
    }

    @Override
    public synchronized DownloadElement getElementAt(int index) {
        return data.get(index);
    }

    public synchronized void addElement(DownloadElement e) {
        data.add(e);

    }

    public synchronized void addElement(int index, DownloadElement e) {
        data.add(index, e);
    }

    public synchronized void removeElement(int index) {
        data.remove(index);
    }
}
