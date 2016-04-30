package sima.sync.server.swing;

import sima.sync.server.Constants;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainScreenBuilder implements Runnable {
    public JFrame mainFrame;
    public JLabel statusVersion;
    public JLabel statusConnection;

    @Override
    public void run() {
        long startNano = System.nanoTime();
        //TODO Change nimbus default values
        mainFrame = new JFrame("SimaSync Server");
        Box statusBox = Box.createHorizontalBox();
        statusBox.setPreferredSize(new Dimension(380, 0));
        TitledBorder statusBoxBorder = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, Color.CYAN, Color.BLACK), "Status Panel");
        statusBoxBorder.setTitleFont(statusBoxBorder.getTitleFont().deriveFont(16f));
        statusBox.setBorder(statusBoxBorder);
        {//Fill up status panel
            Box column1 = Box.createVerticalBox();
            column1.setAlignmentY(Component.TOP_ALIGNMENT);
            Box column2 = Box.createVerticalBox();
            column2.setAlignmentY(Component.TOP_ALIGNMENT);
            //Version
            statusVersion = new JLabel(Constants.VERSION);
            createStatusRow(column1, column2, "Version", statusVersion);
            //Connection
            statusConnection = new JLabel("This is getting somewhere...");
            createStatusRow(column1, column2, "Connection Status", statusConnection);
            //Finally...
            statusBox.add(column1);
            JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
            separator.setMaximumSize(new Dimension(5, Integer.MAX_VALUE));
            statusBox.add(separator);
            statusBox.add(column2);

        }
        JPanel main = new JPanel(new BorderLayout());
        //TODO DEBUG
        main.setMinimumSize(new Dimension(256, 256));
        main.setSize(256, 256);
        main.setBackground(Color.GREEN);
        //TODO DEBUG
        //Add everything to the content pane.
        Container contentPane = mainFrame.getContentPane();
        contentPane.add(statusBox, BorderLayout.LINE_END);
        contentPane.add(main, BorderLayout.CENTER);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.pack();
        mainFrame.setVisible(true);//Must be last statement!
        Constants.log.info("Gui completed (" + ((System.nanoTime() - startNano) / 1000000) + " milli secs)");
    }

    private static final float FONT_SIZE = 12.5f;
    private static final Font STATUS_BOLD = UIManager.getFont("Label.font").deriveFont(Font.BOLD, FONT_SIZE);
    private static final Font STATUS_NORMAL = UIManager.getFont("Label.font").deriveFont(Font.PLAIN, FONT_SIZE);

    private void createStatusRow(Box column1, Box column2, String keyStr, JComponent comp) {
        JLabel keyLabel = new JLabel(keyStr);
        keyLabel.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        keyLabel.setFont(STATUS_BOLD);
        comp.setFont(STATUS_NORMAL);
        column1.add(keyLabel);
        column1.add(Box.createRigidArea(new Dimension(0, 2)));
        column2.add(comp);
        column2.add(Box.createRigidArea(new Dimension(0, 2)));
    }
}