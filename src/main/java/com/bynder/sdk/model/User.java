/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import com.bynder.sdk.api.BynderApi;
import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Model returned when success login through API {@link BynderApi#login(Map)}.
 */
public class User {

    /**
     * Id of the user logged in.
     */
    private String userId;
    /**
     * Token key returned by API.
     */
    private String tokenKey;
    /**
     * Token secret returned by API.
     */
    private String tokenSecret;
    /**
     * True if access was given to the username/password pair.
     */
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
}
