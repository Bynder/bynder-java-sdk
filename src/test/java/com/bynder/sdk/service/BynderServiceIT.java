/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.DownloadFileUrl;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.query.UploadQuery;
import com.bynder.sdk.service.impl.BynderServiceImpl;
import com.bynder.sdk.util.Constants;

public class BynderServiceIT {

    private static final Logger LOG = LoggerFactory.getLogger(BynderServiceIT.class);

    private BynderService bynderService;

    @Before
    public void setUp() throws Exception {
        bynderService = new BynderServiceImpl(Constants.BASE_URL, Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET, Constants.ACCESS_TOKEN_KEY, Constants.ACCESS_TOKEN_SECRET);
    }

    @Test
    public void getBrandsTest() {
        List<Brand> brands = bynderService.getAssetBankManager().getBrands().execute();
        assertNotNull(brands);
        LOG.info(brands.toString());
    }

    @Test
    public void getMetapropertiesTest() {
        Map<String, Metaproperty> metaproperties = bynderService.getAssetBankManager().getMetaproperties().execute();
        assertNotNull(metaproperties);
        LOG.info(metaproperties.toString());
    }

    @Test
    public void getDownloadFileUrlTest() {
        List<Media> mediaList = bynderService.getAssetBankManager().getMediaList("image", null, 1, 1, null).execute();
        assertNotNull(mediaList);
        assertFalse(mediaList.isEmpty());
        DownloadFileUrl fileUrl = bynderService.getAssetBankManager().getDownloadFileUrl(mediaList.get(0).getId()).execute();
        assertNotNull(fileUrl);
        LOG.info(fileUrl.getS3File().toString());
    }

    @Test
    public void uploadFileTest() throws Exception {
        List<Brand> brands = bynderService.getAssetBankManager().getBrands().execute();
        bynderService.getAssetBankManager().uploadFile(new UploadQuery("/Users/daniel/Documents/Bynder/Media/Seinfeld/monks.jpg", brands.get(0).getId(), null));
    }
}
