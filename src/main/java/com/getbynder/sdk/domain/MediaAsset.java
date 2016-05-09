package com.getbynder.sdk.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author daniel.sequeira
 */
public class MediaAsset implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;
    private String copyright;
    private Boolean archive;
    private String datePublished;
    private String type;
    private List<String> propertyOptions;
    private Map<String, String> thumbnails;
    private List<MediaItem> mediaItems;

    public MediaAsset() { }

    public MediaAsset(final String id, final String name, final String description, final String copyright, final Boolean archive, final String datePublished, final String type, final List<String> propertyOptions, final Map<String, String> thumbnails, final List<MediaItem> mediaItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.copyright = copyright;
        this.archive = archive;
        this.datePublished = datePublished;
        this.type = type;
        this.propertyOptions = propertyOptions;
        this.thumbnails = thumbnails;
        this.mediaItems = mediaItems;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCopyright() {
        return copyright;
    }

    public Boolean getArchive() {
        return archive;
    }

    public String getPublicationDate() {
        return datePublished;
    }

    public String getType() {
        return type;
    }

    public List<String> getPropertyOptions() {
        return propertyOptions;
    }

    public Map<String, String> getThumbnails() {
        return thumbnails;
    }

    public List<MediaItem> getMediaItems() {
        return mediaItems;
    }
}
