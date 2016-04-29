package sima.sync.server.swing;

import sima.sync.server.Constants;
import thirdparty.SpringUtilities;

import javax.swing.*;
import java.awt.*;

public class MainScreenBuilder implements Runnable {
    public JFrame mainFrame;
    public JLabel statusVersion;
    public JLabel statusConnection;

    @Override
    public void run() {
        mainFrame = new JFrame("DataSyncer Server by Sima214");
        JPanel statusPanel = new JPanel(new SpringLayout());
        //Version
        statusPanel.add(new JLabel("App version: "));
        statusVersion = new JLabel(Constants.VERSION);
        statusPanel.add(statusVersion);
        //Connection
        statusPanel.add(new JLabel("Connection status: "));
        statusConnection = new JLabel("This is probably next...");
        statusPanel.add(statusConnection);
        SpringUtilities.makeCompactGrid(statusPanel, 2, 2, 1, 1, 5, 5);
        Container contentPane = mainFrame.getContentPane();
        contentPane.add(statusPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.pack();
        mainFrame.setVisible(true);//Must be last statement!
    }
}
