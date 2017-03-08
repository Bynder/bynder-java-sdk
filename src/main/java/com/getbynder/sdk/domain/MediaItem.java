package com.getbynder.sdk.domain;

import java.util.Map;

/**
 *
 * @author daniel.sequeira
 */
public class MediaItem {

    private String id;
    private String fileName;
    private String type;
    private String dateCreated;
    private int height;
    private int width;
    private int size;
    private int version;
    private Boolean active;
    private Map<String, String> thumbnails;

    public MediaItem(final String id, final String fileName, final String type, final String dateCreated, final int height, final int width, final int size, final int version, final Boolean active,
            final Map<String, String> thumbnails) {
        super();
        this.id = id;
        this.fileName = fileName;
        this.type = type;
        this.dateCreated = dateCreated;
        this.height = height;
        this.width = width;
        this.size = size;
        this.version = version;
        this.active = active;
        this.thumbnails = thumbnails;
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getType() {
        return type;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getSize() {
        return size;
    }

    public int getVersion() {
        return version;
    }

    public Boolean getActive() {
        return active;
    }

    public Map<String, String> getThumbnails() {
        return thumbnails;
    }

    @Override
    public String toString() {
        return "MediaItem [id=" + id + ", fileName=" + fileName + ", type=" + type + ", dateCreated=" + dateCreated + ", height=" + height + ", width=" + width + ", size=" + size + ", version="
                + version + ", active=" + active + ", thumbnails=" + thumbnails + "]";
    }
}
