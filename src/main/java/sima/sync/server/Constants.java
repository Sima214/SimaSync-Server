package sima.sync.server;

import java.nio.file.Paths;

/**
 * Access to constants used throughout the app.
 */
public class Constants {
    public static final String VERSION = "alpha";
    public static final int LOGICAL_CORES = Runtime.getRuntime().availableProcessors();
    public static final String PATH = Paths.get(".").toAbsolutePath().normalize().toString();
    public static final int DATA_PACKET_SIZE = 1024;
}
