/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.Category;
import com.bynder.sdk.model.FinaliseResponse;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.MediaCount;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.PollStatus;
import com.bynder.sdk.model.Tag;
import com.bynder.sdk.model.UploadRequest;
import com.bynder.sdk.model.User;
import com.bynder.sdk.service.BynderServiceOld;
import com.bynder.sdk.service.impl.AmazonServiceImpl;
import com.bynder.sdk.util.Constants;
import com.bynder.sdk.util.Utils;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class BynderServiceIT {

    private static final Logger LOG = LoggerFactory.getLogger(BynderServiceIT.class);

    private BynderService bynderService;

    @Before
    public void setUp() throws Exception {
        bynderService = new BynderService(Constants.BASE_URL, Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET, Constants.ACCESS_TOKEN_KEY, Constants.ACCESS_TOKEN_SECRET);
    }

    @Test
    public void loginSyncTest() throws MalformedURLException, IOException, URISyntaxException {
        User user = bynderService.login(Constants.USERNAME, Constants.PASSWORD).execute();
        assertNotNull(user);
        assertNotNull(user.getUserId());
        assertNotNull(user.getTokenKey());
        assertNotNull(user.getTokenSecret());
        LOG.info(user.toString());
    }

    @Test
    public void loginAsyncTest() throws InterruptedException {
        Observable<User> observable = bynderService.login(Constants.USERNAME, Constants.PASSWORD).executeAsync();

        observable.subscribeOn(Schedulers.newThread()).subscribe(new Subscriber<User>() {
            @Override
            public void onCompleted() {
                LOG.info("SUCCESS!");
            }

            @Override
            public void onError(final Throwable t) {
                LOG.error("ERROR!", t);
            }

            @Override
            public void onNext(final User user) {
                assertNotNull(user);
                assertNotNull(user.getUserId());
                assertNotNull(user.getTokenKey());
                assertNotNull(user.getTokenSecret());
            }
        });

        // time for the async call to finish
        Thread.sleep(1000);
    }

    @Test
    public void getRequestTokenTest() {
        String response = bynderService.getRequestToken().execute();
        assertNotNull(response);
        Map<String, String> requestToken = Utils.buildMapFromResponse(response);
        assertNotNull(requestToken);
        assertNotNull(requestToken.keySet());
        assertEquals(2, requestToken.keySet().size());
        assertTrue(requestToken.keySet().containsAll(Arrays.asList("oauth_token", "oauth_token_secret")));
        LOG.info(requestToken.toString());
    }

    @Test
    public void getAuthoriseUrlTest() {
        String requestTokenResponse = bynderService.getRequestToken().execute();
        assertNotNull(requestTokenResponse);
        LOG.info(requestTokenResponse.toString());

        Map<String, String> requestToken = Utils.buildMapFromResponse(requestTokenResponse);

        String response = bynderService.getAuthoriseUrl(requestToken.get("oauth_token"), null);
        assertNotNull(response);
        LOG.info(response.toString());
    }

    @Test
    public void getBrandsTest() {
        List<Brand> brands = bynderService.getBrands().execute();
        assertNotNull(brands);
        LOG.info(brands.toString());
    }

    @Test
    public void getCategoriesTest() {
        List<Category> categories = bynderService.getCategories().execute();
        assertNotNull(categories);
        LOG.info(categories.toString());
    }

    @Test
    public void getTagsTest() {
        List<Tag> tags = bynderService.getTags().execute();
        assertNotNull(tags);
        LOG.info(tags.toString());
    }

    @Test
    public void getMetapropertiesTest() {
        Map<String, Metaproperty> metaproperties = bynderService.getMetaproperties().execute();
        assertNotNull(metaproperties);
        LOG.info(metaproperties.toString());
    }

    @Test
    public void getMediaListTest() {
        List<Media> mediaList = bynderService.getMediaList(null, null, 1, 1, null).execute();
        assertNotNull(mediaList);
        assertTrue(mediaList.size() == 1);
        LOG.info(mediaList.toString());
    }

    @Test
    public void getMediaWithCountTest() {
        MediaCount mediaCount = bynderService.getMediaWithCount().execute();
        assertNotNull(mediaCount);
        assertNotNull(mediaCount.getCount());
        assertNotNull(mediaCount.getMedia());
        LOG.info(mediaCount.toString());
    }

    @Test
    public void getMediaByIdTest() {
        List<Media> mediaList = bynderService.getMediaList(null, null, 1, 1, null).execute();
        assertNotNull(mediaList);
        assertTrue(mediaList.size() == 1);

        Media media = bynderService.getMediaById(mediaList.get(0).getId(), null).execute();
        assertNotNull(media);
        assertNull(media.getMediaItems());
        LOG.info(media.toString());

        media = bynderService.getMediaById(mediaList.get(0).getId(), Boolean.TRUE).execute();
        assertNotNull(media.getMediaItems());
        LOG.info(media.toString());
    }

    @Test
    public void uploadTest() throws IOException, URISyntaxException, InterruptedException {

        String filePath = "/Users/daniel/Documents/Personal/Images/tattoo.jpg";

        UploadRequest uploadRequest = bynderService.getUploadInformation(filePath).execute();
        assertNotNull(uploadRequest);

        String awsBucket = bynderService.getClosestS3Endpoint().execute();
        assertNotNull(awsBucket);
        AmazonServiceImpl amazonService = new AmazonServiceImpl(awsBucket);

        int chunkNumber = 0;
        File file = new File(filePath);
        file.getName();
        // Set part size to 5 MB.
        int chunkSize = 5 * 1024 * 1024;
        byte[] buffer = new byte[chunkSize];
        long numberOfChunks = (file.length() + chunkSize - 1) / chunkSize;

        FileInputStream fileInputStream = new FileInputStream(file);
        while (fileInputStream.read(buffer, 0, buffer.length) > 0) {
            ++chunkNumber;

            amazonService.uploadPartToAmazon("tattoo.jpg", uploadRequest, chunkNumber, buffer, (int) numberOfChunks).execute();

            bynderService.registerChunk(uploadRequest, chunkNumber).execute();
        }

        FinaliseResponse finaliseResponse = bynderService.finaliseUploaded(uploadRequest, chunkNumber).execute();
        assertNotNull(finaliseResponse);

        for (int i = 60; i > 0; --i) {
            PollStatus pollStatus = bynderService.pollStatus(Arrays.asList(finaliseResponse.getImportId())).execute();

            if (pollStatus != null) {
                if (pollStatus.getItemsDone().contains(finaliseResponse.getImportId())) {
                    bynderService.saveMedia(finaliseResponse.getImportId(), "C8422414-4922-451C-B5B840055D15216B", "bear tattoo").execute();
                    break;
                }
            }

            Thread.sleep(2000);
        }
    }
}
