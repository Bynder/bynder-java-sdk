package com.bynder.sdk.query.oauth;

import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.oauth.GrantType;
import com.bynder.sdk.query.decoder.ApiField;

import java.util.List;

public class ClientCredentialsQuery extends TokenQuery {

    /**
     * The authorization scope(s).
     */
    @ApiField(name = "scope")
    private final String scope;

    public ClientCredentialsQuery(
            final OAuthSettings oAuthSettings,
            final List<String> scopes
    ) {
        super(oAuthSettings, GrantType.CLIENT_CREDENTIALS);
        this.scope = String.join(" ", scopes);
    }

}

