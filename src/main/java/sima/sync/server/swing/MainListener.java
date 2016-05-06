package sima.sync.server.swing;

import sima.sync.server.Instance;
import sima.sync.server.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        char[] args = e.getActionCommand().toCharArray();
        switch (args[0]) {
            case 'G': {
                switch (args[1]) {
                    case 'E':
                        Main.exit(0);
                        break;
                    default:
                        Instance.log.error(e.paramString());
                }
            }
            break;
            default:
                Instance.log.error(e.paramString());
        }
    }
}
