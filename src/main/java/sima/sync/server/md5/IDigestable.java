package sima.sync.server.md5;

import java.security.MessageDigest;

public interface IDigestable {
    /**
     * @param dig A "clean" message digest provided by the thread to update with new data
     */
    void perform(MessageDigest dig);
}
