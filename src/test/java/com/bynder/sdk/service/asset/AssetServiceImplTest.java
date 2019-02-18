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
package com.bynder.sdk.service.asset;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.query.MediaDeleteQuery;
import com.bynder.sdk.query.MediaDownloadQuery;
import com.bynder.sdk.query.MediaInfoQuery;
import com.bynder.sdk.query.MediaModifyQuery;
import com.bynder.sdk.query.MediaQuery;
import com.bynder.sdk.query.MetapropertyQuery;
import com.bynder.sdk.query.UsageCreateQuery;
import com.bynder.sdk.query.UsageDeleteQuery;
import com.bynder.sdk.query.UsageQuery;
import com.bynder.sdk.query.decoder.QueryDecoder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


/**
 * Tests the {@link AssetServiceImpl} class methods.
 */
public class AssetServiceImplTest {

    public static String EMPTY_STRING = "";

    @Mock
    private QueryDecoder queryDecoder;
    @Mock
    private BynderApi bynderApi;
    private AssetService assetService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        assetService = AssetService.Builder.create(bynderApi, queryDecoder);
    }

    @Test
    public void getBrands() {
        assetService.getBrands();
        verify(bynderApi, times(1)).getBrands();
    }

    @Test
    public void getTags() {
        assetService.getTags();
        verify(bynderApi, times(1)).getTags();
    }

    @Test
    public void getMetaproperties() {
        MetapropertyQuery metapropertyQuery = new MetapropertyQuery();
        assetService.getMetaproperties(metapropertyQuery);

        verify(bynderApi, times(1)).getMetaproperties(anyMap());
        verify(queryDecoder, times(1)).decode(metapropertyQuery);
    }

    @Test
    public void getMediaList() {
        MediaQuery mediaQuery = new MediaQuery();
        assetService.getMediaList(mediaQuery);

        verify(bynderApi, times(1)).getMediaList(anyMap());
        verify(queryDecoder, times(1)).decode(mediaQuery);
    }

    @Test
    public void getMediaInfo() {
        MediaInfoQuery mediaInfoQuery = new MediaInfoQuery(EMPTY_STRING);
        assetService.getMediaInfo(mediaInfoQuery);

        verify(bynderApi, times(1)).getMediaInfo(anyMap());
        verify(queryDecoder, times(1)).decode(mediaInfoQuery);
    }

    @Test
    public void modifyMedia() {
        MediaModifyQuery mediaModifyQuery = new MediaModifyQuery(EMPTY_STRING);
        assetService.modifyMedia(mediaModifyQuery);

        verify(bynderApi, times(1)).modifyMedia(anyMap());
        verify(queryDecoder, times(1)).decode(mediaModifyQuery);
    }

    @Test
    public void deleteMedia() {
        MediaDeleteQuery mediaDeleteQuery = new MediaDeleteQuery(EMPTY_STRING);
        assetService.deleteMedia(mediaDeleteQuery);

        verify(bynderApi, times(1)).deleteMedia(anyMap());
        verify(queryDecoder, times(1)).decode(mediaDeleteQuery);
    }

    @Test
    public void getMediaDownloadUrl() {
        MediaDownloadQuery mediaDownloadQuery = new MediaDownloadQuery(EMPTY_STRING);
        assetService.getMediaDownloadUrl(mediaDownloadQuery);

        verify(bynderApi, times(1)).getMediaDownloadUrl(anyString());
    }

    @Test
    public void createUsage() {
        UsageCreateQuery usageCreateQuery = new UsageCreateQuery(EMPTY_STRING, EMPTY_STRING);
        assetService.createUsage(usageCreateQuery);

        verify(bynderApi, times(1)).createUsage(anyMap());
        verify(queryDecoder, times(1)).decode(usageCreateQuery);
    }

    @Test
    public void getUsage() {
        UsageQuery usageQuery = new UsageQuery();
        assetService.getUsage(usageQuery);

        verify(bynderApi, times(1)).getUsage(anyMap());
        verify(queryDecoder, times(1)).decode(usageQuery);
    }

    @Test
    public void deleteUsage() {
        UsageDeleteQuery usageDeleteQuery = new UsageDeleteQuery(EMPTY_STRING, EMPTY_STRING);
        assetService.deleteUsage(usageDeleteQuery);

        verify(bynderApi, times(1)).deleteUsage(anyMap());
        verify(queryDecoder, times(1)).decode(usageDeleteQuery);
    }

    @Test
    public void getSmartfilters() {
        assetService.getSmartfilters();
        verify(bynderApi, times(1)).getSmartfilters();
    }
}
