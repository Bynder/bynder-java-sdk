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
     * Filename the uploaded file will get after uploading
     * Optional: will be taken from the filepath if not specified.
     */
    private final String filename;
    /**
     * Brand id where we want to store the file.
     */
    private String brandId;
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
     * list of metaproperties and options to set on the asset upon upload.
     */
    private List<MetapropertyAttribute> metaproperties;

    public UploadQuery(final String filepath) {
        this(filepath, Paths.get(filepath).getFileName().toString());
    }

    public UploadQuery(final String filepath, final String filename) {
        this.filepath = filepath;
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getFilename() {
        return filename;
    }

    public String getBrandId() {
        return brandId;
    }

    public UploadQuery setBrandId(final String brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getMediaId() {
        return mediaId;
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

}
