/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.upload;

import com.bynder.sdk.query.MetapropertyAttribute;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Query with the information to upload a file.
 */
public class UploadQuery {

    /**
     * File path of the file we want to upload.
     */
    private final String filepath;

    /**
     * Brand id where we want to store the file.
     */
    private final String brandId;

    /**
     * Media id. If specified it will add the media asset file as new version of the specified
     * media. Otherwise a new media asset will be added to the asset bank.
     */
    private String mediaId;

    /**
     * Flags if the media asset should be sent to the waiting room.
     */
    private Boolean audit;

    /**
     * Tags to be set upon the asset on an upload.
     */
    private String tags;

    /**
     * list of metaproperties and options to set on the asset upon upload.
     */
    private List<MetapropertyAttribute> metaproperties;

    /**
     * Name of the file to upload
     */
    private String assetName;

    private String fileName;

    public UploadQuery(final String filepath, final String brandId) {
        this.filepath = filepath;
        this.brandId = brandId;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getFilename() {
        return (fileName == null) ? Paths.get(filepath).getFileName().toString() : fileName;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getAssetName() {
        return (assetName == null) ? getFilename() : assetName;
    }

    public UploadQuery setMediaId(final String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public Boolean isAudit() {
        return audit;
    }

    public UploadQuery setAudit(final Boolean audit) {
        this.audit = audit;
        return this;
    }

    public UploadQuery setAssetName(final String assetName) {
        this.assetName = assetName;
        return this;
    }

    public UploadQuery setFileName(final String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public UploadQuery setTags(final List<String> tags) {
        this.tags = String.join(",", tags);
        return this;
    }

    public List<MetapropertyAttribute> getMetaproperties() {
        return metaproperties;
    }

    public UploadQuery addMetaproperty(String metapropertyUuid, String optionName) {
        if(this.metaproperties == null) {
            this.metaproperties = new ArrayList<>();
        }

        this.metaproperties.add(new MetapropertyAttribute(metapropertyUuid, new String[]{optionName}));

        return this;
    }
    
    public UploadQuery addMetaproperty(String metapropertyUuid, String[] options) {
        if(this.metaproperties == null) {
            this.metaproperties = new ArrayList<>();
        }

        this.metaproperties.add(new MetapropertyAttribute(metapropertyUuid, options));

        return this;
    }
}
