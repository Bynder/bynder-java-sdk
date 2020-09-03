/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import com.bynder.sdk.api.ApiFactory;
import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.api.OAuthApi;
import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.Derivative;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.service.collection.CollectionService;
import com.bynder.sdk.service.oauth.OAuthService;
import io.reactivex.Observable;
import retrofit2.Response;

import java.util.List;

public class BynderClientImpl implements BynderClient {

    /**
     * Instance of {@link QueryDecoder} to decode query objects into API parameters.
     */
    private final QueryDecoder queryDecoder;
    /**
     * Instance of {@link OAuthApi} which handles the HTTP communication with the OAuth2
     * provider.
     */
    private final OAuthApi oauthClient;
    /**
     * Instance of {@link BynderApi} which handles the HTTP communication with the Bynder API.
     */
    private final BynderApi bynderApi;
    /**
     * Configuration settings needed to instantiate the different interfaces and services of the
     * SDK client.
     */
    private Configuration configuration;
    /**
     * Instance of {@link OAuthService}.
     */
    private OAuthService oauthService;
    /**
     * Instance of {@link AssetService}.
     */
    private AssetService assetService;
    /**
     * Instance of {@link CollectionService}.
     */
    private CollectionService collectionService;

    /**
     * Initialises a new instance of the class.
     *
     * @param configuration Configuration settings.
     * @param decoder Query decoder.
     */
    BynderClientImpl(final Configuration configuration, final QueryDecoder decoder) {
        this.configuration = configuration;
        this.queryDecoder = decoder;
        oauthClient = ApiFactory.createOAuthClient(configuration.getBaseUrl().toString());
        bynderApi = ApiFactory.createBynderClient(configuration);
    }

    /**
     * Check {@link BynderClient} for more information.
     */
    @Override
    public OAuthService getOAuthService() {
        if (oauthService == null) {
            oauthService = OAuthService.Builder.create(configuration, oauthClient, queryDecoder);
        }

        return oauthService;
    }

    /**
     * Check {@link BynderClient} for more information.
     */
    @Override
    public AssetService getAssetService() {
        if (assetService == null) {
            assetService = AssetService.Builder.create(bynderApi, queryDecoder);
        }

        return assetService;
    }

    /**
     * Check {@link BynderClient} for more information.
     */
    @Override
    public CollectionService getCollectionService() {
        if (collectionService == null) {
            collectionService = CollectionService.Builder.create(bynderApi, queryDecoder);
        }

        return collectionService;
    }

    @Override
    public Observable<Response<List<Derivative>>> getDerivatives() {
        return bynderApi.getDerivatives();
    }
}
