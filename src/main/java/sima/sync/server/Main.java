package sima.sync.server;

import sima.sync.server.md5.MD5Dispatcher;
import sima.sync.server.swing.MainScreenBuilder;

import javax.swing.*;

/**
 * Main entrypoint for the app
 */
public class Main {
    public static final MainScreenBuilder SCREEN_BUILDER = new MainScreenBuilder();
    public static final int logicalCores = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        MD5Dispatcher.init();
        SwingUtilities.invokeLater(SCREEN_BUILDER);
    }
}