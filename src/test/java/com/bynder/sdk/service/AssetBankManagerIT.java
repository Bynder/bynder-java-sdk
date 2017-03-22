/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.DownloadUrl;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.MediaType;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.MetapropertyOption;
import com.bynder.sdk.model.Tag;
import com.bynder.sdk.query.AddMetapropertyToMediaQuery;
import com.bynder.sdk.query.MediaDownloadQuery;
import com.bynder.sdk.query.MediaInfoQuery;
import com.bynder.sdk.query.MediaPropertiesQuery;
import com.bynder.sdk.query.MediaQuery;
import com.bynder.sdk.query.MetapropertyQuery;
import com.bynder.sdk.service.impl.BynderServiceImpl;
import com.bynder.sdk.util.AppProperties;

import retrofit2.Response;

/**
 * Class to test {@link AssetBankManager} implementation against the API.
 */
public class AssetBankManagerIT {

    private static final Logger LOG = LoggerFactory.getLogger(AssetBankManagerIT.class);

    private final int MAX_TEST_ITERATIONS = 20;
    private final int TEST_IDDLE_TIME = 2000;

    private final String MEDIA_ASSET_DESCRIPTION = "Description changed by bynder-java-sdk integration test";

    // skipped tests reasons
    private final String TEST_SKIPPED_NO_BRANDS = "%s skipped: No brands created for this environment";
    private final String TEST_SKIPPED_NO_TAGS = "%s skipped: No tags created for this environment";
    private final String TEST_SKIPPED_NO_METAPROPERTIES = "%s skipped: No metaproperties created for this environment";
    private final String TEST_SKIPPED_NO_IMAGE_ASSETS = "%s skipped: No image assets uploaded for this environment";
    private final String TEST_SKIPPED_NO_MEDIA_ASSETS = "%s skipped: No media assets uploaded for this environment";
    private final String TEST_SKIPPED_NO_MEDIA_ASSETS_WITH_METAPROPERTIES = "%s skipped: No media assets with metaproperties found for this environment";
    private final String TEST_SKIPPED_NO_MEDIA_ASSETS_WITHOUT_METAPROPERTIES = "%s skipped: No media asset without metaproperties found for this environment";
    private final String TEST_SKIPPED_NO_METAPROPERTIES_OPTIONS = "%s skipped: No metaproperties options found for this environment";
    private final String TEST_SKIPPED_NO_PERMISSION_TO_ADD_METAPROPERTY = "%s skipped: No permission to add metaproperty to media asset in this environment";

    // regex to avoid media assets names and descriptions with special characters
    private final Pattern pattern = Pattern.compile("[a-z0-9 ]", Pattern.CASE_INSENSITIVE);

    private AssetBankManager assetBankManager;
    private final AppProperties appProperties = new AppProperties();

    @Rule
    public TestName testName = new TestName();

    /**
     * Before each test an instance of {@link AssetBankManager} is created using the settings
     * defined in the src/main/resources/app.properties file.
     */
    @Before
    public void setUp() throws Exception {
        assetBankManager = BynderServiceImpl.create(appProperties.getSettings()).getAssetBankManager();
    }

    /**
     * Tests that when {@link AssetBankManager#getBrands()} is called the response returned by the
     * Bynder API is the correct one.
     */
    @Test
    public void getBrandsTest() {
        Response<List<Brand>> response = assetBankManager.getBrands().blockingSingle();
        assertNotNull(response);
        assertEquals(HttpStatus.SC_OK, response.code());

        List<Brand> brands = response.body();
        assertNotNull(brands);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_BRANDS, testName.getMethodName()), brands.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        assertNotNull(brands.get(0).getId());
        assertNotNull(brands.get(0).getName());
    }

    /**
     * Tests that when {@link AssetBankManager#getTags()} is called the response returned by the
     * Bynder API is the correct one.
     */
    @Test
    public void getTagsTest() {
        Response<List<Tag>> response = assetBankManager.getTags().blockingSingle();
        assertNotNull(response);
        assertEquals(HttpStatus.SC_OK, response.code());

        List<Tag> tags = response.body();
        assertNotNull(tags);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_TAGS, testName.getMethodName()), tags.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        assertNotNull(tags.get(0).getId());
        assertNotNull(tags.get(0).getTag());
        assertTrue(tags.get(0).getMediaCount() > 0);
    }

    /**
     * Tests that when {@link AssetBankManager#getMetaproperties(MetapropertyQuery)} is called with
     * count parameter set to FALSE the response returned by the Bynder API is the correct one.
     */
    @Test
    public void getMetapropertiesWithoutCountTest() {
        Response<Map<String, Metaproperty>> response = assetBankManager.getMetaproperties(new MetapropertyQuery(Boolean.FALSE)).blockingSingle();
        assertNotNull(response);
        assertEquals(HttpStatus.SC_OK, response.code());

        Map<String, Metaproperty> metaproperties = response.body();
        assertNotNull(metaproperties);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES, testName.getMethodName()), metaproperties.entrySet().size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        for (Entry<String, Metaproperty> entry : metaproperties.entrySet()) {
            assertNotNull(entry.getValue().getId());
            assertNotNull(entry.getValue().getName());
            assertNotNull(entry.getValue().getLabel());
            assertTrue(entry.getValue().getZindex() > 0);
            assertNotNull(entry.getValue().getOptions());

            if (entry.getValue().getOptions().size() > 0) {
                for (MetapropertyOption metapropertyOption : entry.getValue().getOptions()) {
                    assertNotNull(metapropertyOption.getId());
                    assertNotNull(metapropertyOption.getName());
                    assertNotNull(metapropertyOption.getLabel());
                    assertTrue(metapropertyOption.getMediaCount() == 0);
                }
                break;
            }
        }
    }

    /**
     * Tests that when {@link AssetBankManager#getMetaproperties(MetapropertyQuery)} is called with
     * count parameter set to TRUE the response returned by the Bynder API is the correct one.
     */
    @Test
    public void getMetapropertiesWithCountTest() {
        Response<List<Media>> mediaListResponse = assetBankManager.getMediaList(new MediaQuery(null, null, 100, 1, null)).blockingSingle();
        assertNotNull(mediaListResponse);
        assertEquals(HttpStatus.SC_OK, mediaListResponse.code());

        List<Media> mediaList = mediaListResponse.body();
        assertNotNull(mediaList);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS, testName.getMethodName()), mediaList.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        String metapropertyOptionId = null;
        for (Media media : mediaList) {
            if (media.getPropertyOptions().size() > 0) {
                metapropertyOptionId = media.getPropertyOptions().get(0);
                break;
            }
        }

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS_WITH_METAPROPERTIES, testName.getMethodName()), metapropertyOptionId != null);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        Response<Map<String, Metaproperty>> metapropertiesResponse = assetBankManager.getMetaproperties(new MetapropertyQuery(Boolean.TRUE)).blockingSingle();
        assertNotNull(metapropertiesResponse);
        assertEquals(HttpStatus.SC_OK, metapropertiesResponse.code());

        Map<String, Metaproperty> metaproperties = metapropertiesResponse.body();
        assertNotNull(metaproperties);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES, testName.getMethodName()), metaproperties.entrySet().size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        for (Entry<String, Metaproperty> entry : metaproperties.entrySet()) {
            for (MetapropertyOption metapropertyOption : entry.getValue().getOptions()) {
                if (metapropertyOption.getId().equals(metapropertyOptionId)) {
                    assertTrue(metapropertyOption.getMediaCount() > 0);
                    break;
                }
            }
        }
    }

    /**
     * Tests that when {@link AssetBankManager#getMediaList(MediaQuery)} is called the response
     * returned by the Bynder API is the correct one.
     */
    @Test
    public void getMediaListTest() {
        Response<List<Media>> response = assetBankManager.getMediaList(new MediaQuery(null, null, 1, 1, null)).blockingSingle();
        assertNotNull(response);
        assertEquals(HttpStatus.SC_OK, response.code());

        List<Media> mediaList = response.body();
        assertNotNull(mediaList);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS, testName.getMethodName()), mediaList.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        assertTrue(mediaList.size() == 1);
        assertNotNull(mediaList.get(0).getId());
        assertNotNull(mediaList.get(0).getBrandId());
    }

    /**
     * Tests that when {@link AssetBankManager#getMediaInfo(MediaInfoQuery)} is called the response
     * returned by the Bynder API is the correct one.
     */
    @Test
    public void getMediaInfoTest() {
        Response<List<Media>> mediaListResponse = assetBankManager.getMediaList(new MediaQuery(null, null, 1, 1, null)).blockingSingle();
        assertNotNull(mediaListResponse);
        assertEquals(HttpStatus.SC_OK, mediaListResponse.code());

        List<Media> mediaList = mediaListResponse.body();
        assertNotNull(mediaList);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS, testName.getMethodName()), mediaList.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        assertTrue(mediaList.size() == 1);
        assertNotNull(mediaList.get(0).getId());

        String mediaId = mediaList.get(0).getId();

        Response<Media> mediaInfoResponse = assetBankManager.getMediaInfo(new MediaInfoQuery(mediaId, Boolean.FALSE)).blockingSingle();
        assertNotNull(mediaInfoResponse);
        assertEquals(HttpStatus.SC_OK, mediaInfoResponse.code());

        Media media = mediaInfoResponse.body();
        assertNotNull(media);
        assertNotNull(media.getId());
        assertNotNull(media.getBrandId());
        assertEquals(mediaId, media.getId());
        assertNull(media.getMediaItems());

        // get media info with media items versions
        mediaInfoResponse = assetBankManager.getMediaInfo(new MediaInfoQuery(mediaId, Boolean.TRUE)).blockingSingle();
        assertNotNull(mediaInfoResponse);
        assertEquals(HttpStatus.SC_OK, mediaInfoResponse.code());
        assertNotNull(mediaInfoResponse.body().getMediaItems());
        assertTrue(mediaInfoResponse.body().getMediaItems().size() > 0);
    }

    /**
     * Tests that when {@link AssetBankManager#getMediaDownloadUrl(MediaDownloadQuery)} is called
     * the response returned by the Bynder API is the correct one.
     */
    @Test
    public void getMediaDownloadUrlTest() {
        Response<List<Media>> mediaListResponse = assetBankManager.getMediaList(new MediaQuery(MediaType.IMAGE, null, 1, 1, null)).blockingSingle();
        assertNotNull(mediaListResponse);
        assertEquals(HttpStatus.SC_OK, mediaListResponse.code());

        List<Media> imageAssets = mediaListResponse.body();
        assertNotNull(imageAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS, testName.getMethodName()), imageAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        Response<DownloadUrl> mediaDownloadUrlResponse = assetBankManager.getMediaDownloadUrl(new MediaDownloadQuery(imageAssets.get(0).getId(), null)).blockingSingle();
        assertNotNull(mediaDownloadUrlResponse);
        assertEquals(HttpStatus.SC_OK, mediaDownloadUrlResponse.code());
        assertNotNull(mediaDownloadUrlResponse.body());
        assertNotNull(mediaDownloadUrlResponse.body().getS3File());
    }

    /**
     * Tests that when {@link AssetBankManager#setMediaProperties(MediaPropertiesQuery)} is called
     * to update the description of a media asset the response returned by the Bynder API is the
     * correct one.
     */
    @Test
    public void setMediaDescriptionTest() throws InterruptedException {
        Response<List<Media>> mediaListResponse = assetBankManager.getMediaList(new MediaQuery(null, null, 100, 1, null)).blockingSingle();
        assertNotNull(mediaListResponse);
        assertEquals(HttpStatus.SC_OK, mediaListResponse.code());

        List<Media> mediaList = mediaListResponse.body();
        assertNotNull(mediaList);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS, testName.getMethodName()), mediaList.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        String mediaId = null;
        String mediaDescription = null;
        for (Media media : mediaList) {
            if (media.getDescription().isEmpty() || pattern.matcher(media.getDescription()).find()) {
                mediaId = media.getId();
                mediaDescription = media.getDescription();
                break;
            }
        }

        int statusCode = assetBankManager.setMediaProperties(new MediaPropertiesQuery(mediaId, null, MEDIA_ASSET_DESCRIPTION, null, null, null)).blockingSingle().code();
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);

        for (int i = MAX_TEST_ITERATIONS; i > 0; --i) {
            Media media = assetBankManager.getMediaInfo(new MediaInfoQuery(mediaId, null)).blockingSingle().body();
            if (MEDIA_ASSET_DESCRIPTION.equals(media.getDescription())) {
                break;
            }
            if (i == 1) {
                statusCode = assetBankManager.setMediaProperties(new MediaPropertiesQuery(mediaId, null, mediaDescription, null, null, null)).blockingSingle().code();
                if (statusCode == HttpStatus.SC_ACCEPTED) {
                    Assert.fail(String.format("Description of Media [%s] took too long to be updated and it was reverted to its original value", media.getId()));
                } else {
                    Assert.fail(String.format("Description of Media [%s] took too long to be updated and it couldn't be reverted to its original value: %s", media.getId(), mediaDescription));
                }
            }
            Thread.sleep(TEST_IDDLE_TIME);
        }

        statusCode = assetBankManager.setMediaProperties(new MediaPropertiesQuery(mediaId, null, mediaDescription, null, null, null)).blockingSingle().code();
        assertEquals(String.format("Description of Media [%s] couldn't be reverted to its original value: %s", mediaId, mediaDescription), HttpStatus.SC_ACCEPTED, statusCode);
    }

    /**
     * Tests that when {@link AssetBankManager#setMediaProperties(MediaPropertiesQuery)} is called
     * to update the archive status of a media asset the response returned by the Bynder API is the
     * correct one.
     */
    @Test
    public void setMediaArchiveStatusTest() throws InterruptedException {
        Response<List<Media>> mediaListResponse = assetBankManager.getMediaList(new MediaQuery(null, null, 1, 1, null)).blockingSingle();
        assertNotNull(mediaListResponse);
        assertEquals(HttpStatus.SC_OK, mediaListResponse.code());

        List<Media> mediaList = mediaListResponse.body();
        assertNotNull(mediaList);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS, testName.getMethodName()), mediaList.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        String mediaId = mediaList.get(0).getId();
        Boolean mediaArchive = mediaList.get(0).getArchive();
        Boolean archiveNewValue = !mediaArchive;

        int statusCode = assetBankManager.setMediaProperties(new MediaPropertiesQuery(mediaId, null, null, null, archiveNewValue, null)).blockingSingle().code();
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);

        for (int i = MAX_TEST_ITERATIONS; i > 0; --i) {
            Media media = assetBankManager.getMediaInfo(new MediaInfoQuery(mediaId, null)).blockingSingle().body();
            if (media.getArchive() == archiveNewValue) {
                break;
            }
            if (i == 1) {
                statusCode = assetBankManager.setMediaProperties(new MediaPropertiesQuery(mediaId, null, null, null, mediaArchive, null)).blockingSingle().code();
                if (statusCode == HttpStatus.SC_ACCEPTED) {
                    Assert.fail(String.format("Archive status of Media [%s] took too long to be updated and it was reverted to its original value", media.getId()));
                } else {
                    Assert.fail(String.format("Archive status of Media [%s] took too long to be updated and it couldn't be reverted to its original value: %s", media.getId(), mediaArchive));
                }
            }
            Thread.sleep(TEST_IDDLE_TIME);
        }

        statusCode = assetBankManager.setMediaProperties(new MediaPropertiesQuery(mediaId, null, null, null, mediaArchive, null)).blockingSingle().code();
        assertEquals(String.format("Archive status of Media [%s] couldn't be reverted to its original value: %s", mediaId, mediaArchive), HttpStatus.SC_ACCEPTED, statusCode);
    }

    /**
     * Tests that when {@link AssetBankManager#addMetapropertyToMedia(AddMetapropertyToMediaQuery)}
     * is called the response returned by the Bynder API is the correct one.
     */
    @Test
    public void addMetapropertyToMediaTest() throws InterruptedException {
        Response<List<Media>> mediaListResponse = assetBankManager.getMediaList(new MediaQuery(null, null, 100, 1, null)).blockingSingle();
        assertNotNull(mediaListResponse);
        assertEquals(HttpStatus.SC_OK, mediaListResponse.code());

        List<Media> mediaList = mediaListResponse.body();
        assertNotNull(mediaList);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS, testName.getMethodName()), mediaList.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        Media media = null;
        for (Media mediaAsset : mediaList) {
            if (mediaAsset.getPropertyOptions().isEmpty()) {
                media = mediaAsset;
                break;
            }
        }

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS_WITHOUT_METAPROPERTIES, testName.getMethodName()), media != null);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        Response<Map<String, Metaproperty>> metapropertiesResponse = assetBankManager.getMetaproperties(new MetapropertyQuery(Boolean.FALSE)).blockingSingle();
        assertNotNull(metapropertiesResponse);
        assertEquals(HttpStatus.SC_OK, metapropertiesResponse.code());

        Map<String, Metaproperty> metaproperties = metapropertiesResponse.body();
        assertNotNull(metaproperties);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES, testName.getMethodName()), metaproperties.entrySet().size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        int statusCode = 0;
        String metapropertyId = null;
        String metapropertyOptionId = null;
        for (Entry<String, Metaproperty> entry : metaproperties.entrySet()) {
            if (entry.getValue().getOptions().size() > 0) {
                String[] metapropertyOptions = {entry.getValue().getOptions().get(0).getId()};
                statusCode = assetBankManager.addMetapropertyToMedia(new AddMetapropertyToMediaQuery(media.getId(), entry.getValue().getId(), metapropertyOptions)).blockingSingle().code();
                metapropertyId = entry.getValue().getId();
                metapropertyOptionId = entry.getValue().getOptions().get(0).getId();
                break;
            }
        }

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES_OPTIONS, testName.getMethodName()), metapropertyOptionId != null);
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_PERMISSION_TO_ADD_METAPROPERTY, testName.getMethodName()), statusCode == HttpStatus.SC_ACCEPTED);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        for (int i = MAX_TEST_ITERATIONS; i > 0; --i) {
            media = assetBankManager.getMediaInfo(new MediaInfoQuery(media.getId(), null)).blockingSingle().body();
            if (media.getPropertyOptions().contains(metapropertyOptionId)) {
                break;
            }
            if (i == 1) {
                statusCode =
                        assetBankManager.addMetapropertyToMedia(new AddMetapropertyToMediaQuery(media.getId(), metapropertyId, StringUtils.EMPTY.split(StringUtils.EMPTY))).blockingSingle().code();
                if (statusCode == HttpStatus.SC_ACCEPTED) {
                    Assert.fail(String.format("Metaproperty options of Media [%s] took too long to be updated and it was reverted", media.getId()));
                } else {
                    Assert.fail(String.format("Metaproperty options of Media [%s] took too long to be updated and it couldn't be reverted", media.getId()));
                }
            }
            Thread.sleep(TEST_IDDLE_TIME);
        }

        statusCode = assetBankManager.addMetapropertyToMedia(new AddMetapropertyToMediaQuery(media.getId(), metapropertyId, StringUtils.EMPTY.split(StringUtils.EMPTY))).blockingSingle().code();
        assertEquals(String.format("Metaproperty options of Media [%s] took too long to be updated and it couldn't be reverted", media.getId()), HttpStatus.SC_ACCEPTED, statusCode);
    }
}
