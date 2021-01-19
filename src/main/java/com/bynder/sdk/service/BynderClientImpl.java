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
import com.bynder.sdk.service.oauth.OAuthServiceImpl;
import io.reactivex.Observable;
import retrofit2.Response;

import java.util.List;

public class BynderClientImpl implements BynderClient {

    /**
     * Instance of {@link BynderApi} which handles the HTTP communication with the Bynder API.
     */
    private final BynderApi bynderApi;

    /**
     * Instance of {@link OAuthService}.
     */
    private final OAuthService oauthService;

    /**
     * Instance of {@link AssetService}.
     */
    private final AssetService assetService;

    /**
     * Instance of {@link CollectionService}.
     */
    private final CollectionService collectionService;

    /**
     * Initialises a new instance of the class.
     *
     * @param configuration Configuration settings.
     * @param queryDecoder Query decoder.
     */
    BynderClientImpl(final Configuration configuration, final QueryDecoder queryDecoder) {
        OAuthApi oAuthApi = ApiFactory.createOAuthApi(configuration);
        oauthService = new OAuthServiceImpl(configuration, oAuthApi, queryDecoder);

        bynderApi = ApiFactory.createBynderApi(configuration, this);
        assetService = AssetService.Builder.create(bynderApi, queryDecoder);
        collectionService = CollectionService.Builder.create(bynderApi, queryDecoder);
    }

    /**
     * Check {@link BynderClient} for more information.
     */
    @Override
    public OAuthService getOAuthService() {
        return oauthService;
    }

    /**
     * Check {@link BynderClient} for more information.
     */
    @Override
    public AssetService getAssetService() {
        return assetService;
    }

    /**
     * Check {@link BynderClient} for more information.
     */
    @Override
    public CollectionService getCollectionService() {
        return collectionService;
    }

    @Override
    public Observable<Response<List<Derivative>>> getDerivatives() {
        return bynderApi.getDerivatives();
    }

}
