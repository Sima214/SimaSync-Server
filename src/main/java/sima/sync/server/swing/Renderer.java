package sima.sync.server.swing;

import sima.sync.server.allocation.DownloadElement;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Renderer extends JPanel {
    public Renderer(DownloadElement e) {
        super(new BorderLayout());
        //TODO temporary for testing
        add(new JLabel(e.file.getName()));
        setBorder(new LineBorder(Color.RED));
    }

    private void preRender(int index, boolean isSelected, boolean cellHasFocus) {

    }

    /*package*/
    static class DownloadListRenderer implements ListCellRenderer<DownloadElement> {
        @Override
        public Component getListCellRendererComponent(JList<? extends DownloadElement> list, DownloadElement value, int index, boolean isSelected, boolean cellHasFocus) {
            value.renderer.preRender(index, isSelected, cellHasFocus);
            return value.renderer;
        }

    }
}
