package com.bynder.sdk.model;

import com.google.gson.annotations.SerializedName;

public class UploadRequest {

    @SerializedName("s3_filename")
    private String s3Filename;
    @SerializedName("s3file")
    private S3File s3File;
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
