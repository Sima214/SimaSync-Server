package sima.sync.server.swing;

import sima.sync.server.Constants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO
        char[] args = e.getActionCommand().toCharArray();
        Constants.log.debug(e.paramString());
    }
}
