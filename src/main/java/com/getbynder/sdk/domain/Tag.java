package com.getbynder.sdk.domain;

/**
 *
 * @author daniel.sequeira
 */
public class Tag {

    private String id;
    private String tag;
    private int mediaCount;

    public Tag(final String id, final String tag, final int mediaCount) {
        this.id = id;
        this.tag = tag;
        this.mediaCount = mediaCount;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public int getMediaCount() {
        return mediaCount;
    }

    public void setMediaCount(final int mediaCount) {
        this.mediaCount = mediaCount;
    }

    @Override
    public String toString() {
        return "Tag [id=" + id + ", tag=" + tag + ", mediaCount=" + mediaCount + "]";
    }
}
