package sima.sync.server.swing;

import sima.sync.server.Instance;

import javax.swing.*;

public enum IconProvider {
    ACCEPT("accept"),
    CANCEL("cancel"),
    DENY("deny"),
    PAUSE("pause"),
    RESUME("resume");
    final String name;
    private Icon icon;

    IconProvider(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public static void init() {
        Instance.log.info("Preloading icons...");
        for (IconProvider cur : IconProvider.values()) {
            cur.icon = new ImageIcon("icons/" + cur.name + ".png", cur.name);
        }
    }
}
