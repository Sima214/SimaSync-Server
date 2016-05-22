package sima.sync.server.swing;

import sima.sync.server.Constants;
import sima.sync.server.Instance;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;

public enum IconProvider {
    ACCEPT("accept"),
    CANCEL("cancel"),
    DENY("deny"),
    PAUSE("pause"),
    RESUME("resume");
    final String name;
    private ImageIcon icon;

    IconProvider(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public static void init() {
        Instance.log.info("Pre-loading icons...");
        File parent = new File(Constants.PATH, "icons");
        if (!parent.isDirectory()) {
            Instance.log.error("Could not find icon folder. Please check installation");
            return;
        }
        for (IconProvider cur : IconProvider.values()) {
            File iconPath = new File(parent, cur.name + ".png");
            if (iconPath.isFile()) {
                Instance.log.info("Loading icon: " + iconPath.getName());
                try {
                    cur.icon = new ImageIcon(iconPath.toURI().toURL(), cur.name);
                } catch (MalformedURLException e) {
                    Instance.log.error("Could not load icon", e);
                }
            } else {
                Instance.log.error("Could not find icon: " + iconPath.getName());
            }
        }
    }
}
