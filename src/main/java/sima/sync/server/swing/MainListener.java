package sima.sync.server.swing;

import sima.sync.server.Instance;
import sima.sync.server.Main;
import sima.sync.server.allocation.DownloadElement;
import sima.sync.server.allocation.DownloadElement.Type;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainListener implements ActionListener {
    private final JFileChooser openFiles = new JFileChooser(Instance.settings.getOpenCurrentDir());

    public MainListener() {
        //Init
        openFiles.setMultiSelectionEnabled(true);
        openFiles.setFileHidingEnabled(false);
    }

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
                        def(e);
                }
            }
            break;
            case 'F': {
                switch (args[1]) {
                    case 'O': {
                        openFiles.setCurrentDirectory(Instance.settings.getOpenCurrentDir());
                        if (openFiles.showOpenDialog(Instance.screen.mainFrame) == JFileChooser.APPROVE_OPTION) {
                            File[] files = openFiles.getSelectedFiles();
                            for (File cur : files) {
                                Instance.storage.addElement(new DownloadElement(cur, Type.SEND));
                            }
                        }
                        Instance.settings.setOpenCurrentDir(openFiles.getCurrentDirectory());
                    }
                    break;
                    case 'D'://TODO
                        break;
                    default:
                        def(e);
                }
            }
            break;
            case 'A': {
                Type filter;
                switch (args[1]) {
                    case 'A':
                        filter = Type.BOTH;
                        break;
                    case 'I':
                        filter = Type.RECEIVE;
                        break;
                    case 'O':
                        filter = Type.SEND;
                        break;
                    default:
                        def(e);
                        return;
                }
                //TODO
            }
            break;
            case 'E': {
                switch (args[1]) {
                    case 'S'://TODO
                        break;
                    case 'C'://TODO
                        break;
                    default:
                        def(e);
                }

            }
            break;
            default:
                def(e);
        }
    }

    private void def(ActionEvent e) {
        Instance.log.error(e.paramString());
    }
}
