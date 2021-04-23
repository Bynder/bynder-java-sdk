/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.upload;

import com.bynder.sdk.api.BynderApi;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Model to represent the authorisation information to start an upload returned by
 * {@link BynderApi#getUploadInformation(Map)}. This model is only and should be only used when
 * uploading a file.
 */
public class UploadRequest {
    public UploadRequest() {
    }

    public UploadRequest(String s3Filename, S3File s3File, MultipartParameters multipartParams) {
        this.s3Filename = s3Filename;
        this.s3File = s3File;
        this.multipartParams = multipartParams;
    }

    /**
     * S3 file name.
     */
    @SerializedName("s3_filename")
    private String s3Filename;
    /**
     * S3 file information.
     */
    @SerializedName("s3file")
    private S3File s3File;
    /**
     * Amazon parameters information {@link MultipartParameters}.
     */
    @SerializedName("multipart_params")
    private MultipartParameters multipartParams;

    public String getS3Filename() {
        return s3Filename;
    }

    public S3File getS3File() {
        return s3File;
    }

    public MultipartParameters getMultipartParams() {
        return multipartParams;
    }
}
