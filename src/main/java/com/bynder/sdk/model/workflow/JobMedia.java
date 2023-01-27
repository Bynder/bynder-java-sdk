/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobMedia {

    private String id;

    @SerializedName(value = "filename")
    private String fileName;

    @SerializedName(value = "uploader_name")
    private String uploaderMame;

    @SerializedName(value = "responsible_name")
    private String responsibleName;

    private String dateCreated;

    private String dateModified;

    private String orientation;

    @SerializedName(value = "original_url")
    private String originalUrl;

    private JobMediaStatus status;

    @SerializedName(value = "versionParentID")
    private String versionParentId;

    private Integer version;

    private List<JobMediaPreview> previews;

    @SerializedName(value = "sub_versions")
    private List<JobMedia> subVersions;

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUploaderMame() {
        return uploaderMame;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getOrientation() {
        return orientation;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public JobMediaStatus getStatus() {
        return status;
    }

    public String getVersionParentId() {
        return versionParentId;
    }

    public Integer getVersion() {
        return version;
    }

    public List<JobMediaPreview> getPreviews() {
        return previews;
    }

    public List<JobMedia> getSubVersions() {
        return subVersions;
    }
}
