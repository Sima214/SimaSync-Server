package sima.sync.server.swing;

import sima.sync.server.allocation.DownloadElement;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JComponent {
    private final DownloadElement master;
    private static final Color receiveUnpressed = new Color(0x33EB081E, true);
    private static final Color receivePressed = new Color(0xFFEB081E, true);
    private static final Color sendUnpressed = new Color(0x33AFEB0C, true);
    private static final Color sendPressed = new Color(0xFFAFEB0C, true);
    private JPanel parent;

    public Renderer(DownloadElement e) {
        super();
        setPreferredSize(new Dimension(128, 64));
        setOpaque(false);
        master = e;
    }

    public JPanel newContainer() {
        parent = new JPanel(new BorderLayout(2, 2));
        parent.add(this, BorderLayout.CENTER);
        parent.add(new JSeparator(), BorderLayout.PAGE_END);
        return parent;
    }


    private void preRender(int index, boolean isSelected, boolean cellHasFocus) {
        if (master.type == DownloadElement.Type.RECEIVE) {
            if (isSelected) {
                parent.setBackground(receivePressed);
            } else {
                parent.setBackground(receiveUnpressed);
            }
        } else if (master.type == DownloadElement.Type.SEND) {
            if (isSelected) {
                parent.setBackground(sendPressed);
            } else {
                parent.setBackground(sendUnpressed);
            }
        }
    }

    static class DownloadListRenderer implements ListCellRenderer<DownloadElement> {
        private static final JLabel INVALID = new JLabel("INVALID");

        @Override
        public Component getListCellRendererComponent(JList<? extends DownloadElement> list, DownloadElement value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                value.renderer.preRender(index, isSelected, cellHasFocus);
                return value.container;
            }
            return INVALID;
        }
    }
}