package sima.sync.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sima.sync.server.swing.MainScreenBuilder;

import java.nio.file.Paths;

/**
 * Access to constants used throughout the app.
 */
public class Constants {
    public static final Logger log = LogManager.getLogger("MAIN");
    public static final String VERSION = "alpha";
    public static final MainScreenBuilder SCREEN_BUILDER = new MainScreenBuilder();
    public static final int logicalCores = Runtime.getRuntime().availableProcessors();
    public static final String PATH = Paths.get(".").toAbsolutePath().normalize().toString();
}
