package com.getbynder.sdk.domain;

/**
 *
 * @author daniel.sequeira
 */
public class UserAccessData {

    private String userId;
    private String tokenKey;
    private String tokenSecret;
    private boolean access;

    public UserAccessData(final String userId, final String tokenKey, final String tokenSecret, final boolean access) {
        this.userId = userId;
        this.tokenKey = tokenKey;
        this.tokenSecret = tokenSecret;
        this.access = access;
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
        return access;
    }
}
