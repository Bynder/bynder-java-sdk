package com.getbynder.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.client.HttpResponseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.getbynder.api.domain.Category;
import com.getbynder.api.domain.MediaAsset;
import com.getbynder.api.domain.Metaproperty;
import com.getbynder.api.domain.UserAccessData;
import com.getbynder.api.util.ConfigProperties;
import com.getbynder.api.util.ErrorMessages;
import com.getbynder.api.util.SecretProperties;

/**
 *
 * @author daniel.sequeira
 */
public class BynderServiceTest {

    private final String BASE_URL = ConfigProperties.getInstance().getProperty("BASE_URL");
    private final String USERNAME = SecretProperties.getInstance().getProperty("USERNAME");
    private final String PASSWORD = SecretProperties.getInstance().getProperty("PASSWORD");

    private final String MEDIA_TYPE_IMAGE = "image";

    private final String ID_NOT_FOUND = "ID-NOT-FOUND";
    private final String MEDIA_ASSET_NAME = String.format("Name changed through Java API on %s", new Date().toString());
    private final String MEDIA_ASSET_DESCRIPTION = String.format("Descripton changed through Java API on %s", new Date().toString());

    private final String INVALID_DATETIME = DatatypeConverter.printDate(Calendar.getInstance());
    private final String VALID_DATETIME_UTC = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
    private final String VALID_DATETIME_GMT = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")));
    private final String VALID_DATETIME_WET = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("WET")));

    private final String TEST_NOT_PERFORMED = "This test was not performed";

    private BynderService bynderService;

    @Before
    public void setUp() throws Exception {
        bynderService = new BynderService(BASE_URL, USERNAME, PASSWORD);
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

        UserAccessData userAccessData = bynderService.getUserAccessData(USERNAME, PASSWORD);

        assertNotNull(userAccessData);
        assertNotNull(userAccessData.getTokenKey());
        assertNotNull(userAccessData.getTokenSecret());
    }

    @Test
    public void getCategoriesTest() throws Exception {

        List<Category> categories = bynderService.getCategories();

        assertNotNull(categories);
        assertTrue(categories.size() > 0);
    }

    @Test
    public void getAllImageAssetsTest() throws Exception {

        List<MediaAsset> allImageAssets = bynderService.getAllImageAssets();

        assertNotNull(allImageAssets);
        assertTrue(allImageAssets.size() > 0);
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
    public void getImageAssetTotalTest() throws Exception {

        int imageAssetTotal = bynderService.getImageAssetsTotal();

        assertNotNull(imageAssetTotal);
        assertTrue(imageAssetTotal > 0);
    }

    @Test
    public void getAllMediaAssetsTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();

        assertNotNull(allMediaAssets);
        assertTrue(allMediaAssets.size() > 0);
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
        assertTrue(allMediaAssets.size() > 0);

        MediaAsset mediaAsset = bynderService.getMediaAssetById(allMediaAssets.get(0).getId(), null);

        assertNotNull(mediaAsset);
        assertNotNull(mediaAsset.getId());
        assertEquals(allMediaAssets.get(0).getId(), mediaAsset.getId());
    }

    @Test
    public void setMediaAssetIdNullTest() {

        try {
            MediaAsset mediaAsset = new MediaAsset(null, MEDIA_ASSET_NAME, null, null, null, null, null, null);
            bynderService.setMediaAssetProperties(mediaAsset);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(ErrorMessages.NULL_MEDIA_ASSET_ID, e.getMessage());
        }
    }

    @Test
    public void setMediaAssetIdNotFoundTest() {

        try {
            MediaAsset mediaAsset = new MediaAsset(ID_NOT_FOUND, MEDIA_ASSET_NAME, null, null, null, null, null, null);
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
            MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), null, null, null, null, INVALID_DATETIME, null, null);
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

        MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), null, MEDIA_ASSET_DESCRIPTION, null, null, null, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);

        assertEquals(202, statusCode);
    }

    @Test
    public void setMediaAssetNameAndPublicationDateTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
        assertNotNull(allMediaAssets);
        assertTrue(allMediaAssets.size() > 0);

        MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), MEDIA_ASSET_NAME, null, null, null, VALID_DATETIME_UTC, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);

        assertEquals(202, statusCode);
    }

    @Test
    public void setMediaAssetPublicationDateGMTTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
        assertNotNull(allMediaAssets);
        assertTrue(allMediaAssets.size() > 0);

        MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), null, null, null, null, VALID_DATETIME_GMT, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);

        assertEquals(202, statusCode);
    }

    @Test
    public void setMediaAssetPublicationDateWETTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
        assertNotNull(allMediaAssets);
        assertTrue(allMediaAssets.size() > 0);

        MediaAsset mediaAsset = new MediaAsset(allMediaAssets.get(0).getId(), null, null, null, null, VALID_DATETIME_WET, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);

        assertEquals(202, statusCode);
    }

    @Test
    public void getAllMetapropertiesTest() throws Exception {

        List<Metaproperty> allMetaproperties = bynderService.getAllMetaproperties();

        assertNotNull(allMetaproperties);
        assertTrue(allMetaproperties.size() > 0);
    }

    @Test
    public void addMetapropertyToMediaAssetTest() throws Exception {

        List<MediaAsset> allMediaAssets = bynderService.getAllMediaAssets();
        assertNotNull(allMediaAssets);
        assertTrue(allMediaAssets.size() > 0);

        List<Metaproperty> allMetaproperties = bynderService.getAllMetaproperties();
        assertNotNull(allMetaproperties);
        assertTrue(allMetaproperties.size() > 0);

        int statusCode = 0;
        boolean testDone = false;

        for (Metaproperty metaproperty : allMetaproperties) {

            if (testDone) {
                break;
            }

            if (metaproperty.getOptions().size() > 0) {

                for (Metaproperty option : metaproperty.getOptions()) {

                    if (option.getOptions().size() > 0) {
                        statusCode = bynderService.addMetapropertyToAsset(allMediaAssets.get(0).getId(), metaproperty.getId(), option.getId(), option.getOptions().get(0).getId());
                        testDone = true;
                        break;
                    }
                }
            }
        }

        if (testDone) {
            assertEquals(202, statusCode);
        } else {
            Assert.fail(TEST_NOT_PERFORMED);
        }
    }

}
