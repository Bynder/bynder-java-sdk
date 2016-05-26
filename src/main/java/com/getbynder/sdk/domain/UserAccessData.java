package com.getbynder.sdk.domain;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author daniel.sequeira
 */
public class UserAccessData {

    private String userId;
    private String tokenKey;
    private String tokenSecret;
    @SerializedName(value = "access")
    private boolean hasAccess;

    public UserAccessData(final String userId, final String tokenKey, final String tokenSecret, final boolean hasAccess) {
        this.userId = userId;
        this.tokenKey = tokenKey;
        this.tokenSecret = tokenSecret;
        this.hasAccess = hasAccess;
    }

    public String getUserId() {
        return userId;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public boolean hasAccess() {
        return hasAccess;
    }
}
