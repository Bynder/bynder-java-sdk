/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.upload;

import com.google.gson.annotations.SerializedName;

/**
 * Parameters needed to upload a part to Amazon S3. This model is only and should be only used when
 * uploading a file.
 */
public class MultipartParameters {
    public MultipartParameters() {
    }

    public MultipartParameters(String awsAccessKeyId, String policy, String successActionStatus, String key, String signature, String contentType, String acl, String algorithm, String date) {
        this.awsAccessKeyId = awsAccessKeyId;
        this.policy = policy;
        this.successActionStatus = successActionStatus;
        this.key = key;
        this.signature = signature;
        this.contentType = contentType;
        this.acl = acl;
        this.algorithm = algorithm;
        this.date = date;
    }

    /**
     * Amz credentials.
     */
    @SerializedName("x-amz-credential")
    private String awsAccessKeyId;
    /**
     * Policy.
     */
    @SerializedName("Policy")
    private String policy;
    /**
     * Success status.
     */
    @SerializedName("success_action_status")
    private String successActionStatus;
    /**
     * Key.
     */
    @SerializedName("key")
    private String key;
    /**
     * Amz signature.
     */
    @SerializedName("X-Amz-Signature")
    private String signature;
    /**
     * Content type.
     */
    @SerializedName("Content-Type")
    private String contentType;
    /**
     * Acl.
     */
    @SerializedName("acl")
    private String acl;
    /**
     * Amz algorithm.
     */
    @SerializedName("x-amz-algorithm")
    private String algorithm;
    /**
     * Amz date.
     */
    @SerializedName("x-amz-date")
    private String date;

    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    public String getPolicy() {
        return policy;
    }

    public String getSuccessActionStatus() {
        return successActionStatus;
    }

    public String getKey() {
        return key;
    }

    public String getSignature() {
        return signature;
    }

    public String getContentType() {
        return contentType;
    }

    public String getAcl() {
        return acl;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getDate() {
        return date;
    }
}
