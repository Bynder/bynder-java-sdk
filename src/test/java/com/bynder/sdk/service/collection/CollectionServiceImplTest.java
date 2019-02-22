/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 *
 * JUnit framework component copyright (c) 2002-2017 JUnit. All Rights Reserved. Licensed under
 * Eclipse Public License - v 1.0. You may obtain a copy of the License at
 * https://www.eclipse.org/legal/epl-v10.html.
 */
package com.bynder.sdk.service.collection;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.query.collection.CollectionAddMediaQuery;
import com.bynder.sdk.query.collection.CollectionCreateQuery;
import com.bynder.sdk.query.collection.CollectionInfoQuery;
import com.bynder.sdk.query.collection.CollectionQuery;
import com.bynder.sdk.query.collection.CollectionRecipientRight;
import com.bynder.sdk.query.collection.CollectionRemoveMediaQuery;
import com.bynder.sdk.query.collection.CollectionShareQuery;
import com.bynder.sdk.query.decoder.QueryDecoder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Tests the {@link CollectionServiceImpl} class methods.
 */
public class CollectionServiceImplTest {

    public static String EMPTY_STRING = "";

    @Mock
    private QueryDecoder queryDecoder;
    @Mock
    private BynderApi bynderApi;
    private CollectionService collectionService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        collectionService = CollectionService.Builder.create(bynderApi, queryDecoder);
    }

    @Test
    public void getCollections() {
        CollectionQuery collectionQuery = new CollectionQuery();
        collectionService.getCollections(collectionQuery);

        verify(bynderApi, times(1)).getCollections(anyMap());
        verify(queryDecoder, times(1)).decode(collectionQuery);
    }

    @Test
    public void getCollectionInfo() {
        CollectionInfoQuery collectionInfoQuery = new CollectionInfoQuery(EMPTY_STRING);
        collectionService.getCollectionInfo(collectionInfoQuery);

        verify(bynderApi, times(1)).getCollectionInfo(anyMap());
        verify(queryDecoder, times(1)).decode(collectionInfoQuery);
    }

    @Test
    public void createCollection() {
        CollectionCreateQuery collectionCreateQuery = new CollectionCreateQuery(EMPTY_STRING);
        collectionService.createCollection(collectionCreateQuery);

        verify(bynderApi, times(1)).createCollection(anyMap());
        verify(queryDecoder, times(1)).decode(collectionCreateQuery);
    }

    @Test
    public void deleteCollection() {
        CollectionInfoQuery collectionInfoQuery = new CollectionInfoQuery(EMPTY_STRING);
        collectionService.deleteCollection(collectionInfoQuery);

        verify(bynderApi, times(1)).deleteCollection(anyMap());
        verify(queryDecoder, times(1)).decode(collectionInfoQuery);
    }

    @Test
    public void getCollectionMediaIds() {
        CollectionInfoQuery collectionInfoQuery = new CollectionInfoQuery(EMPTY_STRING);
        collectionService.getCollectionMediaIds(collectionInfoQuery);

        verify(bynderApi, times(1)).getCollectionMediaIds(anyString());
    }

    @Test
    public void addMediaToCollection() {
        CollectionAddMediaQuery collectionAddMediaQuery = new CollectionAddMediaQuery(EMPTY_STRING,
            new String[]{EMPTY_STRING});
        collectionService.addMediaToCollection(collectionAddMediaQuery);

        verify(bynderApi, times(1)).addMediaToCollection(anyString(), anyMap());
        verify(queryDecoder, times(1)).decode(collectionAddMediaQuery);
    }

    @Test
    public void removeMediaFromCollection() {
        CollectionRemoveMediaQuery collectionRemoveMediaQuery = new CollectionRemoveMediaQuery(
            EMPTY_STRING, new String[]{EMPTY_STRING});
        collectionService.removeMediaFromCollection(collectionRemoveMediaQuery);

        verify(bynderApi, times(1)).removeMediaFromCollection(anyString(), anyMap());
        verify(queryDecoder, times(1)).decode(collectionRemoveMediaQuery);
    }

    @Test
    public void shareCollection() {
        CollectionShareQuery collectionShareQuery = new CollectionShareQuery(EMPTY_STRING,
            new String[]{EMPTY_STRING}, CollectionRecipientRight.VIEW);
        collectionService.shareCollection(collectionShareQuery);

        verify(bynderApi, times(1)).shareCollection(anyString(), anyMap());
        verify(queryDecoder, times(1)).decode(collectionShareQuery);
    }
}
