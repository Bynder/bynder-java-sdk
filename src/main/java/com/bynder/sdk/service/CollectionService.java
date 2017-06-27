/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.bynder.sdk.model.Collection;
import com.bynder.sdk.query.CollectionAddMediaQuery;
import com.bynder.sdk.query.CollectionCreateQuery;
import com.bynder.sdk.query.CollectionInfoQuery;
import com.bynder.sdk.query.CollectionQuery;
import com.bynder.sdk.query.CollectionRemoveMediaQuery;
import com.bynder.sdk.query.CollectionShareQuery;
import com.bynder.sdk.util.Utils;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Interface to represent operations that can be done to the Bynder Collections.
 */
public interface CollectionService {

    /**
     * Gets a list of collections using query information.
     *
     * @param collectionQuery Information to correctly filter/paginate collections.
     *
     * @return {@link Observable} with list of {@link Collection}.
     *
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     *         information.
     */
    Observable<Response<List<Collection>>> getCollections(CollectionQuery collectionQuery) throws IllegalAccessException;

    /**
     * Gets all the information for a specific collection.
     *
     * @param collectionInfoQuery Information about the collection we want to get the information
     *        from.
     *
     * @return {@link Observable} with {@link Collection} information.
     *
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     *         information.
     */
    Observable<Response<Collection>> getCollectionInfo(CollectionInfoQuery collectionInfoQuery) throws IllegalAccessException;

    /**
     * Creates a collection.
     *
     * @param collectionCreateQuery Information about the collection we want to create.
     *
     * @return {@link Observable} with the request {@link Response} information.
     *
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     *         information.
     */
    Observable<Response<Void>> createCollection(CollectionCreateQuery collectionCreateQuery) throws IllegalAccessException;

    /**
     * Deletes a collection.
     *
     * @param collectionInfoQuery Information to identify the collection we want to delete.
     *
     * @return {@link Observable} with the request {@link Response} information.
     *
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     *         information.
     */
    Observable<Response<Void>> deleteCollection(CollectionInfoQuery collectionInfoQuery) throws IllegalAccessException;

    /**
     * Gets a list of the media assets ids of a collection.
     *
     * @param collectionInfoQuery Information to identify the collection we want to retrieve media
     *        from.
     *
     * @return {@link Observable} with list of media assets ids.
     */
    Observable<Response<List<String>>> getCollectionMediaIds(CollectionInfoQuery collectionInfoQuery);

    /**
     * Adds media assets to a collection.
     *
     * @param collectionAddMediaQuery Information needed to add media to a collection.
     *
     * @return {@link Observable} with the request {@link Response} information.
     *
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     *         information.
     */
    Observable<Response<Void>> addMediaToCollection(CollectionAddMediaQuery collectionAddMediaQuery) throws IllegalAccessException;

    /**
     * Removes media assets from a collection.
     *
     * @param collectionRemoveMediaQuery Information needed to remove media from a collection.
     *
     * @return {@link Observable} with the request {@link Response} information.
     *
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     *         information.
     */
    Observable<Response<Void>> removeMediaFromCollection(CollectionRemoveMediaQuery collectionRemoveMediaQuery) throws IllegalAccessException;

    /**
     * Shares a collection.
     *
     * @param collectionShareQuery Information required for sharing a collection.
     *
     * @return {@link Observable} with the request {@link Response} information.
     *
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     *         information.
     */
    Observable<Response<Void>> shareCollection(CollectionShareQuery collectionShareQuery) throws IllegalAccessException;
}
