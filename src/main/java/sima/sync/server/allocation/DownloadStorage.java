package sima.sync.server.allocation;

import javax.swing.*;
import java.util.ArrayList;

public class DownloadStorage extends AbstractListModel<DownloadElement> {
    private final ArrayList<DownloadElement> data = new ArrayList<>();


    @Override
    public int getSize() {
        synchronized (data) {
            return data.size();
        }
    }

    @Override
    public DownloadElement getElementAt(int index) {
        synchronized (data) {
            try {
                return data.get(index);
            } catch (IndexOutOfBoundsException ignored) {//A hacky workaround. Not a clean solution at all.
            }
            return null;
        }
    }

    public void addElement(DownloadElement e) {
        synchronized (data) {
            data.add(e);
            int index = data.size() - 1;
            e.index = index;
            fireIntervalAdded(this, index, index);
        }
    }

    public void addElement(int index, DownloadElement e) {
        synchronized (data) {
            data.add(index, e);
            updateIndexCache(index, data.size() - 1);
            fireIntervalAdded(this, index, index);
        }
    }

    public void removeElement(int index) {
        synchronized (data) {
            data.get(index).onRemove();
            data.remove(index);
            updateIndexCache(index, data.size() - 1);
            fireIntervalRemoved(this, index, index);
        }
    }

    public void repaint(int index) {
        synchronized (data) {
            fireContentsChanged(this, index, index);
        }
    }

    public void repaintAll() {
        fireContentsChanged(this, 0, data.size() - 1);
    }

    private void updateIndexCache(int start, int end) {
        for (int i = start; i <= end; i++) {
            data.get(i).index = i;
        }
    }
}
