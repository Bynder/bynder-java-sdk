/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query with the information to initialise an upload.
 */
public class RequestUploadQuery {

    /**
     * Filename of the file for which we want to initialise the upload.
     */
    @ApiField(name = "filename")
    private final String filename;

    public RequestUploadQuery(final String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
