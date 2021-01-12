package com.bynder.sdk.query.oauth;

import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.oauth.GrantType;
import com.bynder.sdk.query.decoder.ApiField;

public class RefreshTokenQuery extends TokenQuery {

    /**
     * The refresh token which can be used to obtain new access tokens.
     */
    @ApiField(name = "refresh_token")
    private final String refreshToken;

    public RefreshTokenQuery(final OAuthSettings oAuthSettings, final String refreshToken) {
        super(oAuthSettings, GrantType.REFRESH_TOKEN);
        this.refreshToken = refreshToken;
    }

}
