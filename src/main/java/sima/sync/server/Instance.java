package sima.sync.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sima.sync.server.allocation.DownloadStorage;
import sima.sync.server.settings.Settings;
import sima.sync.server.swing.MainScreenBuilder;

/**
 * Class holding instance based variables.
 */
public class Instance {
    public static final Logger log = LogManager.getLogger("MAIN");
    public static final MainScreenBuilder screen = new MainScreenBuilder();
    public static final DownloadStorage storage = new DownloadStorage();
    public static Settings settings = null;
}
