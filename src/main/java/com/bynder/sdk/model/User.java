/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import com.google.gson.annotations.SerializedName;

public class User {

    private String userId;
    private String tokenKey;
    private String tokenSecret;
    @SerializedName(value = "access")
    private Boolean hasAccess;

    public String getUserId() {
        return userId;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public Boolean hasAccess() {
        return hasAccess;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", tokenKey=" + tokenKey + ", tokenSecret=" + tokenSecret + ", hasAccess=" + hasAccess + "]";
    }
}
