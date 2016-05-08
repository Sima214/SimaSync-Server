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
        int index = data.size() - 1;
        e.index = index;
        fireIntervalAdded(this, index, index);
    }

    public synchronized void addElement(int index, DownloadElement e) {
        data.add(index, e);
        updateAllIndexes();
        fireIntervalAdded(this, index, index);
    }

    public synchronized void removeElement(int index) {
        data.remove(index);
        updateAllIndexes();
        fireIntervalRemoved(this, index, index);
    }

    public void repaint(int index) {
        fireContentsChanged(this, index, index);
    }

    public void repaintAll() {
        fireContentsChanged(this, 0, data.size());
    }

    private void updateAllIndexes() {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).index = i;
        }
    }
}
