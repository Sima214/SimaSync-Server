package sima.sync.server.swing;

import sima.sync.server.allocation.DownloadElement;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JComponent {
    private final DownloadElement master;
    private JPanel parent;
    private final JButton up = new JButton(IconProvider.ACCEPT.getIcon());
    private final JButton down = new JButton(IconProvider.CANCEL.getIcon());

    public Renderer(DownloadElement e) {
        super();
        setPreferredSize(new Dimension(128, 64));
        master = e;
    }

    private void preRender(int index, boolean isSelected, boolean cellHasFocus) {

    }

    public JPanel newContainer() {
        parent = new JPanel(new BorderLayout(2, 2));
        parent.add(this, BorderLayout.CENTER);
        parent.add(new JSeparator(), BorderLayout.PAGE_END);
        JPanel buttons = new JPanel(new BorderLayout());
        buttons.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_START);
        buttons.add(up, BorderLayout.PAGE_START);
        buttons.add(down, BorderLayout.PAGE_END);
        parent.add(buttons, BorderLayout.LINE_END);
        return parent;
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