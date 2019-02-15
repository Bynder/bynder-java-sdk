/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.collection;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.Collection;
import com.bynder.sdk.query.CollectionAddMediaQuery;
import com.bynder.sdk.query.CollectionCreateQuery;
import com.bynder.sdk.query.CollectionInfoQuery;
import com.bynder.sdk.query.CollectionQuery;
import com.bynder.sdk.query.CollectionRemoveMediaQuery;
import com.bynder.sdk.query.CollectionShareQuery;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.service.collection.CollectionService;
import com.bynder.sdk.util.Utils;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;
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
     * Instance of {@link QueryDecoder} to decode query objects into API parameters.
     */
    private final QueryDecoder queryDecoder;

    /**
     * Initialises a new instance of the class.
     *
     * @param bynderApi Instance to handle the HTTP communication with the Bynder API.
     * @param queryDecoder Query decoder.
     */
    public CollectionServiceImpl(final BynderApi bynderApi, final QueryDecoder queryDecoder) {
        this.bynderApi = bynderApi;
        this.queryDecoder = queryDecoder;
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<List<Collection>>> getCollections(
        final CollectionQuery collectionQuery) {
        Map<String, String> params = queryDecoder.decode(collectionQuery);
        return bynderApi.getCollections(params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Collection>> getCollectionInfo(
        final CollectionInfoQuery collectionInfoQuery) {
        Map<String, String> params = queryDecoder.decode(collectionInfoQuery);
        return bynderApi.getCollectionInfo(params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Void>> createCollection(
        final CollectionCreateQuery collectionCreateQuery) {
        Map<String, String> params = queryDecoder.decode(collectionCreateQuery);
        return bynderApi.createCollection(params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Void>> deleteCollection(
        final CollectionInfoQuery collectionInfoQuery) {
        Map<String, String> params = queryDecoder.decode(collectionInfoQuery);
        return bynderApi.deleteCollection(params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<List<String>>> getCollectionMediaIds(
        final CollectionInfoQuery collectionInfoQuery) {
        return bynderApi.getCollectionMediaIds(collectionInfoQuery.getCollectionId());
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Void>> addMediaToCollection(
        final CollectionAddMediaQuery collectionAddMediaQuery) {
        Map<String, String> params = queryDecoder.decode(collectionAddMediaQuery);
        return bynderApi.addMediaToCollection(collectionAddMediaQuery.getCollectionId(), params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Void>> removeMediaFromCollection(
        final CollectionRemoveMediaQuery collectionRemoveMediaQuery) {
        Map<String, String> params = queryDecoder.decode(collectionRemoveMediaQuery);
        return bynderApi
            .removeMediaFromCollection(collectionRemoveMediaQuery.getCollectionId(), params);
    }

    /**
     * Check {@link CollectionService} for more information.
     */
    @Override
    public Observable<Response<Void>> shareCollection(
        final CollectionShareQuery collectionShareQuery) {
        Map<String, String> params = queryDecoder.decode(collectionShareQuery);
        return bynderApi.shareCollection(collectionShareQuery.getCollectionId(), params);
    }
}
