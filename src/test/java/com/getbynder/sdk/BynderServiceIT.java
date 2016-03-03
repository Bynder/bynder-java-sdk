package com.getbynder.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import com.getbynder.sdk.domain.Category;
import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.domain.Metaproperty;
import com.getbynder.sdk.domain.UserAccessData;
import com.getbynder.sdk.util.ConfigProperties;
import com.getbynder.sdk.util.ErrorMessages;
import com.getbynder.sdk.util.SecretProperties;
import com.getbynder.sdk.util.Utils;

/**
 *
 * @author daniel.sequeira
 */
public class BynderServiceIT {

    private final String USERNAME = SecretProperties.getInstance().getProperty("USERNAME");
    private final String PASSWORD = SecretProperties.getInstance().getProperty("PASSWORD");

    private final String MEDIA_TYPE_IMAGE = ConfigProperties.getInstance().getProperty("MEDIA_TYPE_IMAGE");

    private final String ID_NOT_FOUND = "ID-NOT-FOUND";
    private final String MEDIA_ASSET_NAME = String.format("Name changed through Java SDK on %s", new Date().toString());
    private final String MEDIA_ASSET_DESCRIPTION = String.format("Descripton changed through Java SDK on %s", new Date().toString());

    private final String MEDIA_ASSET_KEYWORD_NOT_FOUND = "MEDIA_ASSET_KEYWORD_NOT_FOUND";

    private final String INVALID_DATETIME = DatatypeConverter.printDate(Calendar.getInstance());
    private final String VALID_DATETIME_UTC = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
    private final String VALID_DATETIME_GMT = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")));
    private final String VALID_DATETIME_WET = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("WET")));

    private BynderService bynderService;

    @Before
    public void setUp() throws Exception {
        bynderService = new BynderService();
    }

    @Test
    public void getUserAccessDataFailTest() {

        try {
            bynderService.getUserAccessData("INVALID_USERNAME", "INVALID_PASSWORD");
        } catch (Exception e) {
            assertTrue(e instanceof HttpResponseException);
            assertEquals(ErrorMessages.LOGIN_REQUEST_FAILED, e.getMessage());
        }
    }

    @Test
    public void getUserAccessDataTest() throws Exception {

        Assume.assumeTrue(USERNAME != null && PASSWORD != null && !USERNAME.isEmpty() && !PASSWORD.isEmpty());

        UserAccessData userAccessData = bynderService.getUserAccessData(USERNAME, PASSWORD);

        assertNotNull(userAccessData);
        assertNotNull(userAccessData.getTokenKey());
        assertNotNull(userAccessData.getTokenSecret());
    }

    @Test
    public void getCategoriesTest() throws Exception {

        List<Category> categories = bynderService.getCategories();

        assertNotNull(categories);
        Assume.assumeTrue(categories.size() > 0);
        assertNotNull(categories.get(0).getId());
    }

    @Test
    public void getAllImageAssetsTest() throws Exception {

        List<MediaAsset> allImageAssets = bynderService.getAllImageAssets();

        assertNotNull(allImageAssets);
        Assume.assumeTrue(allImageAssets.size() > 0);
        assertEquals(MEDIA_TYPE_IMAGE, allImageAssets.get(0).getType());
    }

    @Test
    public void getImageAssetsTest() throws Exception {

        List<MediaAsset> imageAssets = bynderService.getImageAssets(5, 1);

        assertNotNull(imageAssets);
        assertTrue(imageAssets.size() == 5);
        assertEquals(MEDIA_TYPE_IMAGE, imageAssets.get(0).getType());
    }

    @Test
    public void getImageAssetsByKeywordTest() throws Exception {

        String keyword = "";

        List<MediaAsset> allImageAssets = bynderService.getAllImageAssets();

        for(MediaAsset mediaAsset : allImageAssets) {
            if(mediaAsset.getDescription() != null && !mediaAsset.getDescription().isEmpty()) {
                keyword = mediaAsset.getDescription().split(Utils.STR_SPACE)[0];
                break;
            }
        }

        List<MediaAsset> imageAssets = bynderService.getImageAssetsByKeyword(keyword);

        assertNotNull(imageAssets);
        assertTrue(imageAssets.size() > 0);
        assertEquals(MEDIA_TYPE_IMAGE, imageAssets.get(0).getType());

        // null/empty keyword
        allImageAssets = bynderService.getImageAssetsByKeyword(null);

        assertNotNull(allImageAssets);
        assertTrue(allImageAssets.size() > imageAssets.size());
        assertEquals(MEDIA_TYPE_IMAGE, allImageAssets.get(0).getType());

        // keyword not found
        List<MediaAsset> emptyList = bynderService.getImageAssetsByKeyword(MEDIA_ASSET_KEYWORD_NOT_FOUND);

        assertNotNull(emptyList);
        assertTrue(emptyList.size() == 0);
    }

    @Test
    public void getImageAssetsByMetapropertyIdTest() throws Exception {

        List<Metaproperty> allMetaproperties = bynderService.getAllMetaproperties();
        assertNotNull(allMetaproperties);
        Assume.assumeTrue(allMetaproperties.size() > 0);

        boolean metapropertyFound = false;
        String metapropertyId = null;

        for (Metaproperty metaproperty : allMetaproperties) {
            if (metaproperty.getOptions().size() > 0) {
                metapropertyId = metaproperty.getOptions().get(0).getId();
                metapropertyFound = true;
                break;
            }
        }

        Assume.assumeTrue(metapropertyFound && metapropertyId != null);

        List<MediaAsset> imageAssets = bynderService.getImageAssetsByMetapropertyId(metapropertyId);

        assertNotNull(imageAssets);
        Assume.assumeTrue(imageAssets.size() > 0);
        assertTrue(imageAssets.get(0).getPropertyOptions().contains(metapropertyId));
    }

    @Test
    public void getImageAssetTotalTest() throws Exception {

        int imageAssetsTotal = bynderService.getImageAssetsTotal();

        assertNotNull(imageAssetsTotal);
        assertTrue(imageAssetsTotal == bynderService.getAllImageAssets().size());
    }

    @Test
    public void getAllMediaAssetsTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();

        assertNotNull(allMediaAssets);
        assertTrue(allMediaAssets.size() > 0);
    }

    @Test
    public void getMediaAssetTotalTest() throws Exception {

        int mediaAssetTotal = bynderService.getMediaAssetsTotal();

        assertNotNull(mediaAssetTotal);
        assertTrue(mediaAssetTotal == bynderService.getAllMediaAssets().size());
    }

    @Test
    public void getMediaAssetByIdFailTest() throws Exception {

        try {
            bynderService.getMediaAssetById(ID_NOT_FOUND, null);
        } catch (Exception e) {
            assertTrue(e instanceof HttpResponseException);
            assertEquals(ErrorMessages.MEDIA_ASSET_ID_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void getMediaAssetByIdTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();

        assertNotNull(allMediaAssets);
        Assume.assumeTrue(allMediaAssets.size() > 0);

        MediaAsset mediaAsset = bynderService.getMediaAssetById(allMediaAssets.get(0).getId(), null);

        assertNotNull(mediaAsset);
        assertNotNull(mediaAsset.getId());
        assertEquals(allMediaAssets.get(0).getId(), mediaAsset.getId());
    }

    @Test
    public void setMediaAssetIdNullTest() {

        try {
            MediaAsset mediaAsset = new MediaAsset(null, MEDIA_ASSET_NAME, null, null, null, null, null, null, null);
            bynderService.setMediaAssetProperties(mediaAsset);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(ErrorMessages.NULL_MEDIA_ASSET_ID, e.getMessage());
        }
    }

    @Test
    public void setMediaAssetIdNotFoundTest() {

        try {
            MediaAsset mediaAsset = new MediaAsset(ID_NOT_FOUND, MEDIA_ASSET_NAME, null, null, null, null, null, null, null);
            bynderService.setMediaAssetProperties(mediaAsset);
        } catch (Exception e) {
            assertTrue(e instanceof HttpResponseException);
            assertEquals(ErrorMessages.MEDIA_ASSET_ID_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void setMediaAssetInvalidDateTest() {

        try {
            List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
            assertNotNull(allMediaAssets);
            assertTrue(allMediaAssets.size() > 0);

            // using datetime value without time
            MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), null, null, null, null, INVALID_DATETIME, null, null, null);
            bynderService.setMediaAssetProperties(mediaAsset);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(ErrorMessages.INVALID_PUBLICATION_DATETIME_FORMAT, e.getMessage());
        }
    }

    @Test
    public void setMediaAssetDescriptionTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
        assertNotNull(allMediaAssets);
        assertTrue(allMediaAssets.size() > 0);

        MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), null, MEDIA_ASSET_DESCRIPTION, null, null, null, null, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);

        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);
    }

    @Test
    public void setMediaAssetNameAndPublicationDateTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
        assertNotNull(allMediaAssets);
        assertTrue(allMediaAssets.size() > 0);

        MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), MEDIA_ASSET_NAME, null, null, null, VALID_DATETIME_UTC, null, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);

        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);
    }

    @Test
    public void setMediaAssetPublicationDateGMTTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
        assertNotNull(allMediaAssets);
        assertTrue(allMediaAssets.size() > 0);

        MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), null, null, null, null, VALID_DATETIME_GMT, null, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);

        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);
    }

    @Test
    public void setMediaAssetPublicationDateWETTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
        assertNotNull(allMediaAssets);
        assertTrue(allMediaAssets.size() > 0);

        MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), null, null, null, null, VALID_DATETIME_WET, null, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);

        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);
    }

    @Test
    public void getAllMetapropertiesTest() throws Exception {

        List<Metaproperty> allMetaproperties = bynderService.getAllMetaproperties();

        assertNotNull(allMetaproperties);
        Assume.assumeTrue(allMetaproperties.size() > 0);
        assertNotNull(allMetaproperties.get(0).getId());
    }

    @Test
    public void addMetapropertyToMediaAssetTest() throws Exception {

        List<MediaAsset> allImageAssets = bynderService.getAllImageAssets();
        assertNotNull(allImageAssets);
        Assume.assumeTrue(allImageAssets.size() > 0);

        MediaAsset testMediaAsset = null;

        for(MediaAsset mediaAsset : allImageAssets) {
            if(mediaAsset.getPropertyOptions() == null || mediaAsset.getPropertyOptions().isEmpty()) {
                testMediaAsset = mediaAsset;
                break;
            }
        }

        Assume.assumeTrue(testMediaAsset != null);

        List<Metaproperty> allMetaproperties = bynderService.getAllMetaproperties();
        assertNotNull(allMetaproperties);
        Assume.assumeTrue(allMetaproperties.size() > 0);

        int statusCode = 0;
        String optionId = null;

        for (Metaproperty metaproperty : allMetaproperties) {
            if (metaproperty.getOptions().size() > 0) {
                statusCode = bynderService.addMetapropertyToAsset(testMediaAsset.getId(), metaproperty.getId(), metaproperty.getOptions().get(0).getId());
                optionId = metaproperty.getOptions().get(0).getId();
                break;
            }
        }

        Assume.assumeTrue(optionId != null && statusCode == HttpStatus.SC_ACCEPTED);

        //give it some time for the metaproperty to be added to the asset
        Thread.sleep(6000);

        testMediaAsset = bynderService.getMediaAssetById(testMediaAsset.getId(), null);
        assertNotNull(testMediaAsset.getPropertyOptions());
        assertTrue(testMediaAsset.getPropertyOptions().contains(optionId));
    }
}
