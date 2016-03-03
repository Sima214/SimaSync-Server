package sima.sync.server.swing;

import javax.swing.*;

public class MainScreenBuilder implements Runnable {
    public JFrame mainFrame;

    @Override
    public void run() {
        mainFrame = new JFrame("DataSyncer Server by Sima214");
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);//Must be last statement!
    }
}
