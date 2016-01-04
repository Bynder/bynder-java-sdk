package com.getbynder.api.domain;

import java.io.Serializable;

/**
 *
 * @author daniel.sequeira
 */
public class ImageAsset implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;
    private Thumbnails thumbnails;

    public ImageAsset(final String id, final String name, final String description, final Thumbnails thumbnails) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnails = thumbnails;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    @Override
    public String toString() {
        return "ImageAsset [id=" + id + ", name=" + name + ", description=" + description + ", webimage=" + thumbnails.getWebimage()
        + ", thumbnail=" + thumbnails.getThul() + "]";
    }

    public class Thumbnails {

        private String webimage;
        private String thul;

        public String getWebimage() {
            return webimage;
        }

        public void setWebimage(final String webimage) {
            this.webimage = webimage;
        }

        public String getThul() {
            return thul;
        }

        public void setThul(final String thul) {
            this.thul = thul;
        }
    }

}
