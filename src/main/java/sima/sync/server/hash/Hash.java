package sima.sync.server.hash;

import sima.sync.server.allocation.DownloadElement;

import java.util.Arrays;

/**
 * Immutable containers, containg a hash.
 */
public class Hash {
    private final DownloadElement master;
    private final byte[] hash;
    private final String hex;

    public Hash(DownloadElement master, byte[] hash) {
        this.hash = hash;
        hex = MD5Dispatcher.genHashHex(hash);
        this.master = master;
    }

    public byte[] getHash() {
        return hash;
    }

    public String getHex() {
        return hex;
    }

    public DownloadElement getMaster() {
        return master;
    }

    @Override
    public String toString() {
        return hex;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hash) {
            return Arrays.equals(hash, ((Hash) obj).hash);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return hex.hashCode();
    }

}
