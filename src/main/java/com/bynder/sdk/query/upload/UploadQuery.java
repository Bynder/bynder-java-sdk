/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.upload;

import java.nio.file.Paths;

/**
 * Query with the information to upload a file.
 */
public abstract class UploadQuery {

    /**
     * File path of the file we want to upload.
     */
    private final String filepath;

    public UploadQuery(final String filepath) {
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getFilename() {
        return Paths.get(filepath).getFileName().toString();
    }

}
