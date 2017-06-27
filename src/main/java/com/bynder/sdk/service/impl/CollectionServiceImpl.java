/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import java.util.List;
import java.util.Map;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.Collection;
import com.bynder.sdk.query.CollectionAddMediaQuery;
import com.bynder.sdk.query.CollectionCreateQuery;
import com.bynder.sdk.query.CollectionInfoQuery;
import com.bynder.sdk.query.CollectionQuery;
import com.bynder.sdk.query.CollectionRemoveMediaQuery;
import com.bynder.sdk.query.CollectionShareQuery;
import com.bynder.sdk.service.CollectionService;
import com.bynder.sdk.util.Utils;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Implementation of {@link CollectionService}.
 */
public class CollectionServiceImpl implements CollectionService {

    /**
     * Instance of {@link BynderApi} which handles the HTTP communication with the Bynder API.
     */
    private final BynderApi bynderApi;

    /**
     * Initialises a new instance of the class.
     *
     * @param bynderApi Instance to handle the HTTP communication with the Bynder API.
     */
    public CollectionServiceImpl(final BynderApi bynderApi) {
        this.bynderApi = bynderApi;
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<List<Collection>>> getCollections(final CollectionQuery collectionQuery) throws IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(collectionQuery);
        return bynderApi.getCollections(params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Collection>> getCollectionInfo(final CollectionInfoQuery collectionInfoQuery) throws IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(collectionInfoQuery);
        return bynderApi.getCollectionInfo(params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Void>> createCollection(final CollectionCreateQuery collectionCreateQuery) throws IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(collectionCreateQuery);
        return bynderApi.createCollection(params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Void>> deleteCollection(final CollectionInfoQuery collectionInfoQuery) throws IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(collectionInfoQuery);
        return bynderApi.deleteCollection(params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<List<String>>> getCollectionMediaIds(final CollectionInfoQuery collectionInfoQuery) {
        return bynderApi.getCollectionMediaIds(collectionInfoQuery.getCollectionId());
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Void>> addMediaToCollection(final CollectionAddMediaQuery collectionAddMediaQuery) throws IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(collectionAddMediaQuery);
        return bynderApi.addMediaToCollection(collectionAddMediaQuery.getCollectionId(), params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Void>> removeMediaFromCollection(final CollectionRemoveMediaQuery collectionRemoveMediaQuery) throws IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(collectionRemoveMediaQuery);
        return bynderApi.removeMediaFromCollection(collectionRemoveMediaQuery.getCollectionId(), params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Void>> shareCollection(final CollectionShareQuery collectionShareQuery) throws IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(collectionShareQuery);
        return bynderApi.shareCollection(collectionShareQuery.getCollectionId(), params);
    }
}
