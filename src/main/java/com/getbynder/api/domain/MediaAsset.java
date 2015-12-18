package com.getbynder.api.domain;

import java.io.Serializable;

/**
 *
 * @author daniel.sequeira
 */
//TODO create a superclass Asset
public class MediaAsset implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;
    private String copyright;
    private Boolean archive;
    private String publicationDate;

    public MediaAsset(final String id, final String name, final String description, final String copyright, final Boolean archive, final String publicationDate) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.copyright = copyright;
        this.archive = archive;
        this.publicationDate = publicationDate;
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
        return publicationDate;
    }

    public void setPublicationDate(final String publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "MediaAsset [id=" + id + ", name=" + name + ", description=" + description + ", copyright="
                + copyright + ", archive=" + archive + ", publicationDate=" + publicationDate + "]";
    }

}
