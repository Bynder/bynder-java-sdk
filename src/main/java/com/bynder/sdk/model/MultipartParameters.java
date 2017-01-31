/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import com.google.gson.annotations.SerializedName;

public class MultipartParameters {

    @SerializedName("x-amz-credential")
    private String awsAccessKeyId;
    @SerializedName("Policy")
    private String policy;
    @SerializedName("success_action_status")
    private String successActionStatus;
    @SerializedName("key")
    private String key;
    @SerializedName("X-Amz-Signature")
    private String signature;
    @SerializedName("Content-Type")
    private String contentType;
    @SerializedName("acl")
    private String acl;
    @SerializedName("x-amz-algorithm")
    private String algorithm;
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
