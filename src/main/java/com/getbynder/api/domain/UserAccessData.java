package com.getbynder.api.domain;

import org.json.JSONObject;

/**
 *
 * Technology is the devil
 *
 * @author daniel.sequeira
 */
public class UserAccessData {

    private String userId;
    private String tokenKey;
    private String tokenSecret;
    private boolean hasAccess;

    public UserAccessData(final String userId, final String tokenKey, final String tokenSecret, final boolean hasAccess) {
        this.userId = userId;
        this.tokenKey = tokenKey;
        this.tokenSecret = tokenSecret;
        this.hasAccess = hasAccess;
    }

    public UserAccessData(final JSONObject jsonObject) {
        this.userId = jsonObject.getString("userId");
        this.tokenKey = jsonObject.getString("tokenKey");
        this.tokenSecret = jsonObject.getString("tokenSecret");
        this.hasAccess = jsonObject.getBoolean("access");
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(final String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(final String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public boolean hasAccess() {
        return hasAccess;
    }

    public void setAccess(final boolean hasAccess) {
        this.hasAccess = hasAccess;
    }
}
