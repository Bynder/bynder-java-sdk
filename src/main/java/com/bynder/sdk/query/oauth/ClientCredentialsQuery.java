package com.bynder.sdk.query.oauth;

import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.oauth.GrantType;
import com.bynder.sdk.query.decoder.ApiField;
import com.bynder.sdk.query.decoder.OAuthScopesEncoder;

import java.util.List;

public class ClientCredentialsQuery extends TokenQuery {

    /**
     * The authorization scope(s).
     */
    @ApiField(name = "scope", decoder = OAuthScopesEncoder.class)
    private final List<String> scope;

    public ClientCredentialsQuery(
            final OAuthSettings oAuthSettings
    ) {
        super(oAuthSettings, GrantType.CLIENT_CREDENTIALS);
        this.scope = oAuthSettings.getScopes();
    }

}

