package com.getbynder.api.domain;

import java.io.Serializable;

/**
 *
 * @author daniel.sequeira
 */
public class ImageAsset implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String description;
    private String url;
    private String thumbnailUrl;

    public ImageAsset(final String id, final String title, final String description, final String url, final String thumbnailUrl) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(final String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return "ImageAsset [id=" + id + ", title=" + title + ", description=" + description + ", url=" + url
                + ", thumbnailUrl=" + thumbnailUrl + "]";
    }

}
