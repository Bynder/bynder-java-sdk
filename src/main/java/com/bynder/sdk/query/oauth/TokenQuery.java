package com.bynder.sdk.query.oauth;

import com.bynder.sdk.api.OAuthApi;
import com.bynder.sdk.model.oauth.GrantType;
import com.bynder.sdk.query.decoder.ApiField;
import java.net.URI;
import java.util.Map;

/**
 * Query used to call {@link OAuthApi#getAccessToken(Map)}.
 */
public class TokenQuery {

    /**
     * OAuth application client id.
     */
    @ApiField(name = "client_id")
    private String clientId;
    /**
     * OAuth application client secret.
     */
    @ApiField(name = "client_secret")
    private String clientSecret;
    /**
     * URI to redirect to after application has been authorized.
     */
    @ApiField(name = "redirect_uri")
    private URI redirectUri;
    /**
     * The authorization grant type. Possible values: {@link GrantType#AUTHORIZATION_CODE} and
     * {@link GrantType#REFRESH_TOKEN}.
     */
    @ApiField(name = "grant_type")
    private GrantType grantType;
    /**
     * The code included in the redirect URI after application has been authorized. Required if
     * {@link GrantType#AUTHORIZATION_CODE} was selected as grant type.
     */
    @ApiField(name = "code")
    private String code;
    /**
     * The refresh token which can be used to obtain new access tokens. Required if
     * {@link GrantType#REFRESH_TOKEN} was selected as grant type.
     */
    @ApiField(name = "refresh_token")
    private String refreshToken;

    public TokenQuery(final String clientId, final String clientSecret, final URI redirectUri,
        final GrantType grantType, final String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.grantType = grantType;
        this.code = code;
    }

    public TokenQuery(final String clientId, final String clientSecret, final GrantType grantType,
        final String refreshToken) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.refreshToken = refreshToken;
    }
}
