package sima.sync.server;

import sima.sync.server.hash.MD5Dispatcher;
import sima.sync.server.swing.MainScreenBuilder;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main entrypoint for the app
 */
public class Main {
    static Logger log = Logger.getLogger("QuickLibTest");
    public static final MainScreenBuilder SCREEN_BUILDER = new MainScreenBuilder();
    public static final int logicalCores = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        log.log(Level.ALL, "Will log4j work?");
        //First initialize all required classes
        MD5Dispatcher.init();
        //And finally create the gui.
        SwingUtilities.invokeLater(SCREEN_BUILDER);
    }
}