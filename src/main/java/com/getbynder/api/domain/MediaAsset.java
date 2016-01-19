package com.getbynder.api.domain;

import java.io.Serializable;

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
    private Thumbnails thumbnails;

    public MediaAsset(final String id, final String name, final String description, final String copyright, final Boolean archive, final String datePublished, final String type, final Thumbnails thumbnails) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.copyright = copyright;
        this.archive = archive;
        this.datePublished = datePublished;
        this.type = type;
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

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(final String copyright) {
        this.copyright = copyright;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(final Boolean archive) {
        this.archive = archive;
    }

    public String getPublicationDate() {
        return datePublished;
    }

    public void setPublicationDate(final String datePublished) {
        this.datePublished = datePublished;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    @Override
    public String toString() {
        return "MediaAsset [id=" + id + ", name=" + name + ", description=" + description + ", copyright=" + copyright
                + ", archive=" + archive + ", datePublished=" + datePublished + ", type=" + type + ", thumbnails="
                + thumbnails + "]";
    }

    public class Thumbnails {

        private String thul;
        private String mini;
        private String webimage;

        public String getMini() {
            return mini;
        }

        public void setMini(final String mini) {
            this.mini = mini;
        }

        public String getThul() {
            return thul;
        }

        public void setThul(final String thul) {
            this.thul = thul;
        }

        public String getWebimage() {
            return webimage;
        }

        public void setWebimage(final String webimage) {
            this.webimage = webimage;
        }
    }

}
