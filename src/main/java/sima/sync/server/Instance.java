package sima.sync.server;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sima.sync.server.allocation.DownloadStorage;
import sima.sync.server.hash.Hash;
import sima.sync.server.settings.Settings;
import sima.sync.server.swing.MainScreenBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Class holding instance based variables.
 */
public class Instance {
    public static final Logger log = LogManager.getLogger("MAIN");
    public static final Gson gson = new Gson();
    public static final MainScreenBuilder screen = new MainScreenBuilder();
    public static final DownloadStorage storage = new DownloadStorage();
    public static final Set<Hash> hashStore = new HashSet<>();
    public static Settings settings = null;
}
