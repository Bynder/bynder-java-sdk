package com.getbynder.sdk.domain;

import java.util.List;

/**
 *
 * @author daniel.sequeira
 */
public class MediaCount {

    private Count count;
    private List<MediaAsset> media;

    public MediaCount(final Count count, final List<MediaAsset> media) {
        this.count = count;
        this.media = media;
    }

    public Count getCount() {
        return count;
    }

    public List<MediaAsset> getMedia() {
        return media;
    }
}
