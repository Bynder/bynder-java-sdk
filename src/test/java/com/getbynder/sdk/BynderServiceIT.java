package com.getbynder.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
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
    private final String MEDIA_ASSET_NAME = String.format("Name changed by Integration Test of Bynder Java SDK");
    private final String MEDIA_ASSET_DESCRIPTION = String.format("Descripton changed by Integration Test of Bynder Java SDK");

    private final String MEDIA_ASSET_KEYWORD_NOT_FOUND = "MEDIA_ASSET_KEYWORD_NOT_FOUND";

    private final String INVALID_DATETIME = DatatypeConverter.printDate(Calendar.getInstance());
    private final String VALID_DATETIME_UTC = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
    private final String VALID_DATETIME_GMT = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")));
    private final String VALID_DATETIME_WET = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("WET")));

    // tests skipped assumptions messages
    private final String TEST_SKIPPED_NO_USERNAME_PASSWORD = "Test skipped: No username or/and password defined";
    private final String TEST_SKIPPED_NO_CATEGORIES = "Test skipped: No categories created for this environment";
    private final String TEST_SKIPPED_NO_IMAGE_ASSETS = "Test skipped: No image assets uploaded for this environment";
    private final String TEST_SKIPPED_NO_MEDIA_ASSETS = "Test skipped: No media assets uploaded for this environment";
    private final String TEST_SKIPPED_NO_METAPROPERTIES = "Test skipped: No metaproperties created for this environment";
    private final String TEST_SKIPPED_NO_METAPROPERTIES_OPTIONS = "Test skipped: No metaproperties options found for this environment";
    private final String TEST_SKIPPED_THOUSAND_IMAGE_ASSETS = "Test skipped: More than 1000 image assets uploaded for this environment";
    private final String TEST_SKIPPED_THOUSAND_MEDIA_ASSETS = "Test skipped: More than 1000 media assets uploaded for this environment";
    private final String TEST_SKIPPED_NO_IMAGE_ASSETS_WITHOUT_METAPROPERTIES = "Test skipped: No image asset without metaproperties found for this environment";
    private final String TEST_SKIPPED_NO_ADD_METAPROPERTY_PERMISSION = "Test skipped: No permission to add metaproperty to media asset in this environment";
    private final String TEST_SKIPPED_NO_IMAGE_ASSETS_WITH_DESCRIPTION = "Test skipped: No image asset with description found for this environment";

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

        Assume.assumeTrue(TEST_SKIPPED_NO_USERNAME_PASSWORD, USERNAME != null && PASSWORD != null && !USERNAME.isEmpty() && !PASSWORD.isEmpty());

        UserAccessData userAccessData = bynderService.getUserAccessData(USERNAME, PASSWORD);

        assertNotNull(userAccessData);
        assertNotNull(userAccessData.getTokenKey());
        assertNotNull(userAccessData.getTokenSecret());
    }

    @Test
    public void getCategoriesTest() throws Exception {

        List<Category> categories = bynderService.getCategories();

        assertNotNull(categories);
        Assume.assumeTrue(TEST_SKIPPED_NO_CATEGORIES, categories.size() > 0);
        assertNotNull(categories.get(0).getId());
    }

    @Test
    public void getAllImageAssetsTest() throws Exception {

        List<MediaAsset> allImageAssets = bynderService.getAllImageAssets();

        assertNotNull(allImageAssets);
        Assume.assumeTrue(TEST_SKIPPED_NO_IMAGE_ASSETS, allImageAssets.size() > 0);
        assertEquals(MEDIA_TYPE_IMAGE, allImageAssets.get(0).getType());
    }

    @Test
    public void getImageAssetsTest() throws Exception {

        List<MediaAsset> imageAssets = bynderService.getImageAssets(5, 1);

        assertNotNull(imageAssets);
        Assume.assumeTrue(TEST_SKIPPED_NO_IMAGE_ASSETS, imageAssets.size() > 0);
        assertTrue(imageAssets.size() == 5);
        assertEquals(MEDIA_TYPE_IMAGE, imageAssets.get(0).getType());
    }

    @Test
    public void getImageAssetsByKeywordTest() throws Exception {

        String keyword = "";

        List<MediaAsset> allImageAssets = bynderService.getAllImageAssets();

        assertNotNull(allImageAssets);
        Assume.assumeTrue(TEST_SKIPPED_NO_IMAGE_ASSETS, allImageAssets.size() > 0);

        for (MediaAsset mediaAsset : allImageAssets) {
            if (mediaAsset.getDescription() != null && !mediaAsset.getDescription().isEmpty()) {
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
        Assume.assumeTrue(TEST_SKIPPED_NO_METAPROPERTIES, allMetaproperties.size() > 0);

        boolean metapropertyFound = false;
        String metapropertyId = null;

        for (Metaproperty metaproperty : allMetaproperties) {
            if (metaproperty.getOptions().size() > 0) {
                metapropertyId = metaproperty.getOptions().get(0).getId();
                metapropertyFound = true;
                break;
            }
        }

        Assume.assumeTrue(TEST_SKIPPED_NO_METAPROPERTIES_OPTIONS, metapropertyFound && metapropertyId != null);

        List<MediaAsset> imageAssets = bynderService.getImageAssetsByMetapropertyId(metapropertyId);

        assertNotNull(imageAssets);
        Assume.assumeTrue(TEST_SKIPPED_NO_IMAGE_ASSETS, imageAssets.size() > 0);
        assertTrue(imageAssets.get(0).getPropertyOptions().contains(metapropertyId));
    }

    @Test
    public void getImageAssetTotalTest() throws Exception {

        int imageAssetsTotal = bynderService.getImageAssetsTotal();

        assertNotNull(imageAssetsTotal);
        Assume.assumeTrue(TEST_SKIPPED_THOUSAND_IMAGE_ASSETS, imageAssetsTotal <= 1000);
        assertTrue(imageAssetsTotal == bynderService.getAllImageAssets().size());
    }

    @Test
    public void getAllMediaAssetsTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();

        assertNotNull(allMediaAssets);
        Assume.assumeTrue(TEST_SKIPPED_NO_MEDIA_ASSETS, allMediaAssets.size() > 0);
        assertNotNull(allMediaAssets.get(0).getId());
    }

    @Test
    public void getMediaAssetTotalTest() throws Exception {

        int mediaAssetTotal = bynderService.getMediaAssetsTotal();

        assertNotNull(mediaAssetTotal);
        Assume.assumeTrue(TEST_SKIPPED_THOUSAND_MEDIA_ASSETS, mediaAssetTotal <= 1000);
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
        Assume.assumeTrue(TEST_SKIPPED_NO_MEDIA_ASSETS, allMediaAssets.size() > 0);

        MediaAsset mediaAsset = bynderService.getMediaAssetById(allMediaAssets.get(0).getId(), null);

        assertNotNull(mediaAsset);
        assertNotNull(mediaAsset.getId());
        assertEquals(allMediaAssets.get(0).getId(), mediaAsset.getId());
        assertNull(mediaAsset.getMediaItems());

        mediaAsset = bynderService.getMediaAssetById(allMediaAssets.get(0).getId(), true);

        assertNotNull(mediaAsset.getMediaItems());
    }

    @Test
    public void setMediaAssetIdNullTest() {

        try {
            MediaAsset mediaAsset = new MediaAsset(null, MEDIA_ASSET_NAME, null, null, null, null, null, null, null, null);
            bynderService.setMediaAssetProperties(mediaAsset);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(ErrorMessages.NULL_MEDIA_ASSET_ID, e.getMessage());
        }
    }

    @Test
    public void setMediaAssetIdNotFoundTest() {

        try {
            MediaAsset mediaAsset = new MediaAsset(ID_NOT_FOUND, MEDIA_ASSET_NAME, null, null, null, null, null, null, null, null);
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
            MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), null, null, null, null, INVALID_DATETIME, null, null, null, null);
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
        Assume.assumeTrue(TEST_SKIPPED_NO_MEDIA_ASSETS, allMediaAssets.size() > 0);

        String mediaAssetId = null;
        String originalDescription = null;
        for (MediaAsset mediaAsset : allMediaAssets) {
            if (mediaAsset.getDescription() != null && !mediaAsset.getDescription().isEmpty()) {
                mediaAssetId = mediaAsset.getId();
                originalDescription = mediaAsset.getDescription();
                break;
            }
        }

        Assume.assumeTrue(TEST_SKIPPED_NO_IMAGE_ASSETS_WITH_DESCRIPTION, originalDescription != null);

        MediaAsset mediaAsset = new MediaAsset(mediaAssetId, null, MEDIA_ASSET_DESCRIPTION, null, null, null, null, null, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);

        // give it some time for the media asset to be updated
        Thread.sleep(6000);

        mediaAsset = bynderService.getMediaAssetById(mediaAssetId, null);
        assertEquals(MEDIA_ASSET_DESCRIPTION, mediaAsset.getDescription());

        mediaAsset = new MediaAsset(mediaAssetId, null, originalDescription, null, null, null, null, null, null, null);

        statusCode = bynderService.setMediaAssetProperties(mediaAsset);
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);

        // give it some time for the media asset to be updated
        Thread.sleep(6000);

        mediaAsset = bynderService.getMediaAssetById(mediaAssetId, null);
        assertEquals(originalDescription, mediaAsset.getDescription());
    }

    @Test
    public void setMediaAssetNameAndPublicationDateTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
        assertNotNull(allMediaAssets);
        Assume.assumeTrue(TEST_SKIPPED_NO_MEDIA_ASSETS, allMediaAssets.size() > 0);

        String mediaAssetId = allMediaAssets.get(0).getId();
        String originalName = allMediaAssets.get(0).getName();

        MediaAsset mediaAsset = new MediaAsset(mediaAssetId, MEDIA_ASSET_NAME, null, null, null, VALID_DATETIME_UTC, null, null, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);

        // give it some time for the media asset to be updated
        Thread.sleep(6000);

        mediaAsset = bynderService.getMediaAssetById(mediaAssetId, null);
        assertEquals(MEDIA_ASSET_NAME, mediaAsset.getName());

        mediaAsset = new MediaAsset(mediaAssetId, originalName, null, null, null, null, null, null, null, null);

        statusCode = bynderService.setMediaAssetProperties(mediaAsset);
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);

        // give it some time for the media asset to be updated
        Thread.sleep(6000);

        mediaAsset = bynderService.getMediaAssetById(mediaAssetId, null);
        assertEquals(originalName, mediaAsset.getName());
    }

    @Test
    public void setMediaAssetPublicationDateGMTTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
        assertNotNull(allMediaAssets);
        Assume.assumeTrue(TEST_SKIPPED_NO_MEDIA_ASSETS, allMediaAssets.size() > 0);

        MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), null, null, null, null, VALID_DATETIME_GMT, null, null, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);
    }

    @Test
    public void setMediaAssetPublicationDateWETTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
        assertNotNull(allMediaAssets);
        Assume.assumeTrue(TEST_SKIPPED_NO_MEDIA_ASSETS, allMediaAssets.size() > 0);

        MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), null, null, null, null, VALID_DATETIME_WET, null, null, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);
    }

    @Test
    public void getAllMetapropertiesTest() throws Exception {

        List<Metaproperty> allMetaproperties = bynderService.getAllMetaproperties();

        assertNotNull(allMetaproperties);
        Assume.assumeTrue(TEST_SKIPPED_NO_METAPROPERTIES, allMetaproperties.size() > 0);
        assertNotNull(allMetaproperties.get(0).getId());
    }

    @Test
    public void addMetapropertyToMediaAssetTest() throws Exception {

        List<MediaAsset> allImageAssets = bynderService.getAllImageAssets();
        assertNotNull(allImageAssets);
        Assume.assumeTrue(TEST_SKIPPED_NO_IMAGE_ASSETS, allImageAssets.size() > 0);

        MediaAsset testMediaAsset = null;

        for (MediaAsset mediaAsset : allImageAssets) {
            if (mediaAsset.getPropertyOptions() == null || mediaAsset.getPropertyOptions().isEmpty()) {
                testMediaAsset = mediaAsset;
                break;
            }
        }

        Assume.assumeTrue(TEST_SKIPPED_NO_IMAGE_ASSETS_WITHOUT_METAPROPERTIES, testMediaAsset != null);

        List<Metaproperty> allMetaproperties = bynderService.getAllMetaproperties();
        assertNotNull(allMetaproperties);
        Assume.assumeTrue(TEST_SKIPPED_NO_METAPROPERTIES, allMetaproperties.size() > 0);

        int statusCode = 0;
        String optionId = null;

        for (Metaproperty metaproperty : allMetaproperties) {
            if (metaproperty.getOptions().size() > 0) {
                statusCode = bynderService.addMetapropertyToAsset(testMediaAsset.getId(), metaproperty.getId(), metaproperty.getOptions().get(0).getId());
                optionId = metaproperty.getOptions().get(0).getId();
                break;
            }
        }

        Assume.assumeTrue(TEST_SKIPPED_NO_ADD_METAPROPERTY_PERMISSION, statusCode == HttpStatus.SC_ACCEPTED);
        Assume.assumeTrue(TEST_SKIPPED_NO_METAPROPERTIES_OPTIONS, optionId != null);

        // give it some time for the metaproperty to be added to the asset
        Thread.sleep(6000);

        testMediaAsset = bynderService.getMediaAssetById(testMediaAsset.getId(), null);
        assertNotNull(testMediaAsset.getPropertyOptions());
        assertTrue(testMediaAsset.getPropertyOptions().contains(optionId));
    }
}
