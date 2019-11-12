/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.Derivative;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.MediaType;
import com.bynder.sdk.model.oauth.RefreshTokenCallback;
import com.bynder.sdk.model.oauth.Token;
import com.bynder.sdk.query.MediaQuery;
import com.bynder.sdk.query.OrderBy;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.service.oauth.OAuthService;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sample class to display some of the SDK functionality.
 */
public class AppSample {

    private static final Logger LOG = LoggerFactory.getLogger(AppSample.class);

    public static void main(final String[] args) throws URISyntaxException, IOException {
        /**
         * Loads app.properties file under src/main/resources
         */
        AppProperties appProperties = new AppProperties();

        // Initialize BynderClient with a permanent token
        BynderClient client = BynderClient.Builder.create(
            new Configuration.Builder(new URL(appProperties.getProperty("BASE_URL")))
                .setPermanentToken(appProperties.getProperty("PERMANENT_TOKEN")).build());

        AssetService assetService = client.getAssetService();

        // Call the API to request for media assets
        List<Media> mediaList = assetService.getMediaList(
            new MediaQuery().setType(MediaType.IMAGE).setOrderBy(OrderBy.NAME_DESC).setLimit(10)
                .setPage(1)).blockingSingle().body();
        for (Media media : mediaList) {
            LOG.info(media.getName());
        }

        // Optional: define callback function to be triggered after access token is auto
        // refreshed
        RefreshTokenCallback callback = new RefreshTokenCallback() {
            @Override
            public void execute(Token token) {
                LOG.info("Auto refresh triggered!");
                LOG.info(String.format("Refresh token used: %s", token.getRefreshToken()));
                LOG.info(String.format("New access token: %s", token.getAccessToken()));
                LOG.info(String.format("New access token expiration date: %s", token.getAccessTokenExpiration()));
            }
        };

        // Initialize BynderClient with oauth settings to perform OAuth 2.0
        // authorization flow
        client = BynderClient.Builder.create(
            new Configuration.Builder(new URL(appProperties.getProperty("BASE_URL")))
                .setOAuthSettings(new OAuthSettings(appProperties.getProperty("CLIENT_ID"),
                appProperties.getProperty("CLIENT_SECRET"), new URI(appProperties.getProperty("REDIRECT_URI")),
                callback)).build());

        // Initialize OAuthService
        OAuthService oauthService = client.getOAuthService();

        List<String> scopes = Arrays.asList("offline", "asset:read");
        URL authorizationUrl = oauthService.getAuthorizationUrl("state example", scopes);

        // Open browser with authorization URL
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(authorizationUrl.toURI());

        // Ask for the code returned in the redirect URI
        System.out.println("Insert the code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();
        scanner.close();

        // Get the access token
        oauthService.getAccessToken(code, scopes).blockingSingle();

        // Call the API to request for the account information
        List<Derivative> derivatives = client.getDerivatives().blockingSingle().body();
        for (Derivative derivative : derivatives) {
            LOG.info(derivative.getPrefix());
        }

        // Get the asset service
        assetService = client.getAssetService();

        // Call the API to request for brands
        List<Brand> brands = assetService.getBrands().blockingSingle().body();
        for (Brand brand : brands) {
            LOG.info(brand.getName());
        }

        // Call the API to request for media assets
        mediaList = assetService.getMediaList(
            new MediaQuery().setType(MediaType.IMAGE).setOrderBy(OrderBy.NAME_DESC).setLimit(10)
                .setPage(1)).blockingSingle().body();
        for (Media media : mediaList) {
            LOG.info(media.getName());
        }
    }
}