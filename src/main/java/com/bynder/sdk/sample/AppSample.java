/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.*;
import com.bynder.sdk.model.oauth.Token;
import com.bynder.sdk.query.MediaDeleteQuery;
import com.bynder.sdk.query.MediaInfoQuery;
import com.bynder.sdk.query.MediaQuery;
import com.bynder.sdk.query.OrderBy;
import com.bynder.sdk.query.collection.CollectionOrderType;
import com.bynder.sdk.query.collection.CollectionQuery;
import com.bynder.sdk.query.upload.ExistingAssetUploadQuery;
import com.bynder.sdk.query.upload.NewAssetUploadQuery;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.service.collection.CollectionService;
import com.bynder.sdk.service.oauth.OAuthService;
import com.bynder.sdk.util.RXUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AppSample {

    public static void main(final String[] args)
            throws IOException, URISyntaxException {
        AppSample app = new AppSample(
                "https://example.com",
                new OAuthSettings.Builder("OAuth2 client ID", "Oauth2 client secret")
                        .setRedirectUri("https://redirect_url/") // Leave out for authentication with client credentials
                        .setScopes(OAUTH_SCOPES) // List of scopes to request to be granted to the access token.
                        .setRefreshTokenCallback(token -> { // Optional callback method to be triggered when token is refreshed.
                            LOG.info("Auto refresh triggered!");
                            LOG.info(String.format("Refresh token used: %s", token.getRefreshToken()));
                            LOG.info(String.format("New access token: %s", token.getAccessToken()));
                            LOG.info(String.format("New access token expiration date: %s", token.getAccessTokenExpiration()));
                        })
                        .build()
        );
        app.listItems();
        app.uploadFile("/path/to/file.ext");
    }

    private static final Logger LOG = LoggerFactory.getLogger(AppSample.class);
    private static final List<String> OAUTH_SCOPES = Arrays.asList("offline", "asset:read", "asset:write", "collection:read");

    private final BynderClient bynderClient;
    private final AssetService assetService;
    private final CollectionService collectionService;

    private AppSample(final Configuration configuration)
            throws IOException, URISyntaxException {
        bynderClient = BynderClient.Builder.create(configuration);
        assetService = bynderClient.getAssetService();
        collectionService = bynderClient.getCollectionService();
        authenticateWithOAuth2(configuration.getOAuthSettings().getRedirectUri() == null);
    }

    public AppSample(final String baseUrl, final OAuthSettings oAuthSettings)
            throws IOException, URISyntaxException {
        this(new Configuration.Builder(baseUrl, oAuthSettings).build());
    }

    private void logError(final Throwable e) {
        LOG.error(e.getMessage());
    }

    public void listItems() {
        RXUtils.handleResponseBody(
                bynderClient.getDerivatives()
        ).subscribe(
                derivatives -> LOG.info("Derivatives: " + derivatives.stream().map(Derivative::getPrefix).collect(Collectors.toList())),
                this::logError
        ).dispose();

        RXUtils.handleResponseBody(
                assetService.getBrands()
        ).subscribe(
                brands -> LOG.info("Brands: " + brands.stream().map(Brand::getName).collect(Collectors.toList())),
                this::logError
        ).dispose();

        RXUtils.handleResponseBody(
                assetService.getMediaList(new MediaQuery()
                        .setType(MediaType.IMAGE)
                        .setOrderBy(OrderBy.DATE_CREATED_DESC)
                        .setLimit(10)
                        .setPage(1)
                )
        ).subscribe(
                assets -> LOG.info("Assets: " + assets.stream().map(Media::getName).collect(Collectors.toList())),
                this::logError
        ).dispose();

        RXUtils.handleResponseBody(
                collectionService.getCollections(new CollectionQuery()
                        .setKeyword("")
                        .setOrderBy(CollectionOrderType.DATE_CREATED_DESC)
                        .setLimit(10)
                        .setPage(1)
                )
        ).subscribe(
                collections -> LOG.info("Collections: " + collections.stream().map(Collection::getName).collect(Collectors.toList())),
                this::logError
        ).dispose();
    }

    public void uploadFile(final String uploadPath) {
        RXUtils.handleResponseBody(assetService.getBrands()).flatMap(brands ->
                assetService.uploadFile(
                        new NewAssetUploadQuery(uploadPath, brands.get(0).getId())
                )
        ).flatMap(saveMediaResponse -> {
            LOG.info("New asset successfully created: " + saveMediaResponse.getMediaId());
            return RXUtils.handleResponseBody(
                    assetService.getMediaInfo(new MediaInfoQuery(saveMediaResponse.getMediaId()).setVersions(true))
            ).retryWhen(f ->
                    f.take(5).delay(1000, TimeUnit.MILLISECONDS)
            ).doOnError(e ->
                    LOG.error("New asset could not be fetched after trying 5 times.")
            );
        }).flatMap(media -> {
            LOG.info("New asset could be fetched: " + media.getId() + " " + media.getName());
            return assetService.uploadFile(
                    new ExistingAssetUploadQuery(uploadPath, media.getId())
            );
        }).flatMap(saveMediaResponse -> {
            LOG.info("New asset version successfully created: " + saveMediaResponse.getMediaId());
            return RXUtils.handleResponseBody(
                    assetService.getMediaInfo(new MediaInfoQuery(saveMediaResponse.getMediaId()).setVersions(true))
            ).retryWhen(f ->
                    f.take(5).delay(1000, TimeUnit.MILLISECONDS)
            ).doOnError(e ->
                    LOG.error("New asset version could not be fetched after trying 5 times.")
            );
        }).flatMapCompletable(media -> {
            LOG.info("New asset version could be fetched: " + media.getId() + " " + media.getName());
            return RXUtils.handleResponse(
                    assetService.deleteMedia(new MediaDeleteQuery(media.getId()))
            );
        }).blockingAwait();
    }

    private void authenticateWithOAuth2(boolean withClientCredentials)
            throws IOException, URISyntaxException {
        OAuthService oauthService = bynderClient.getOAuthService();

        Token token;
        if (withClientCredentials) {
            token = oauthService.getClientCredentials().blockingGet();
        } else {
            // Open browser with authorization URL
            Desktop.getDesktop().browse(
                    oauthService.getAuthorizationUrl("state example").toURI()
            );

            // Ask for the code returned in the redirect URI
            System.out.println("Insert the code: ");
            Scanner scanner = new Scanner(System.in);
            String code = scanner.nextLine();
            scanner.close();

            // Get the access token
            token = oauthService.getAccessToken(code).blockingGet();
        }
        LOG.info("OAuth token: " + token.getAccessToken());
    }

}
