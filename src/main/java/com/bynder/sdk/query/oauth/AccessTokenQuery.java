package com.bynder.sdk.query.oauth;

import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.oauth.GrantType;
import com.bynder.sdk.query.decoder.ApiField;

import java.net.URI;

public class AccessTokenQuery extends TokenQuery {

    /**
     * URI to redirect to after application has been authorized.
     */
    @ApiField(name = "redirect_uri")
    private final URI redirectUri;

    /**
     * The authorization scope(s).
     */
    @ApiField(name = "scope")
    private final String scope;

    /**
     * The code included in the redirect URI after application has been authorized.
     */
    @ApiField
    private final String code;

    public AccessTokenQuery(
            final OAuthSettings oAuthSettings,
            final String code
    ) {
        super(oAuthSettings, GrantType.AUTHORIZATION_CODE);
        this.redirectUri = oAuthSettings.getRedirectUri();
        this.scope = oAuthSettings.getScopes() != null
                ? String.join(" ", oAuthSettings.getScopes())
                : null;
        this.code = code;
    }

}
