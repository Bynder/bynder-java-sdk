package com.bynder.sdk.query.oauth;

import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.oauth.GrantType;
import com.bynder.sdk.query.decoder.ApiField;

public class ClientCredentialsQuery extends TokenQuery {

    /**
     * The authorization scope(s).
     */
    @ApiField(name = "scope")
    private final String scope;

    public ClientCredentialsQuery(
            final OAuthSettings oAuthSettings
    ) {
        super(oAuthSettings, GrantType.CLIENT_CREDENTIALS);
        this.scope = oAuthSettings.getScopes() != null
                ? String.join(" ", oAuthSettings.getScopes())
                : null;
    }

}

