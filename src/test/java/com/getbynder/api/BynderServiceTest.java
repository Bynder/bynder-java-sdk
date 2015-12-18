package com.getbynder.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.http.client.HttpResponseException;
import org.junit.Before;
import org.junit.Test;

import com.getbynder.api.domain.Category;
import com.getbynder.api.domain.ImageAsset;
import com.getbynder.api.domain.MediaAsset;
import com.getbynder.api.domain.Metaproperty;

/**
 *
 * @author daniel.sequeira
 */
public class BynderServiceTest {

    private final String BASE_URL = BynderProperties.getInstance().getProperty("BASE_URL");
    private final String USERNAME = BynderProperties.getInstance().getProperty("USERNAME");
    private final String PASSWORD = BynderProperties.getInstance().getProperty("PASSWORD");

    private BynderService bynderService;

    @Before
    public void setUp() throws Exception {
        bynderService = new BynderService(BASE_URL, USERNAME, PASSWORD);
    }

    @Test
    public void getUserAccessDataFailTest() {

        try {
            bynderService.getUserAccessData("username", "password");
        } catch (Exception e) {
            assertTrue(e instanceof HttpResponseException);
        }
    }

    @Test
    public void getCategoriesTest() throws Exception {

        List<Category> categories = bynderService.getCategories();

        assertNotNull(categories);
    }

    @Test
    public void getAllImageAssetsTest() throws Exception {

        List<ImageAsset> bynderAllImageAssets = bynderService.getImageAssets();

        assertNotNull(bynderAllImageAssets);
    }

    @Test
    public void getImageAssetsTest() throws Exception {

        List<ImageAsset> bynderImageAssets = bynderService.getImageAssets(5, 1);

        assertNotNull(bynderImageAssets);
    }

    @Test
    public void getImageAssetCountTest() throws Exception {

        int imageAssetCount = bynderService.getImageAssetCount();

        assertNotNull(imageAssetCount);
    }

    @Test
    public void getMediaAssetsTest() throws Exception {

        List<MediaAsset> bynderMediaAssets = bynderService.getMediaAssets();

        assertNotNull(bynderMediaAssets);
    }

    @Test
    public void setMediaAssetIdNullTest() {

        try {
            MediaAsset mediaAsset = new MediaAsset(null, "Test shall fail", null, null, null, null);
            bynderService.setMediaAssetProperties(mediaAsset);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void setMediaAssetIdNotFoundTest() {

        try {
            MediaAsset mediaAsset = new MediaAsset("ID-NOT-FOUND", null, null, null, null, null);
            bynderService.setMediaAssetProperties(mediaAsset);
        } catch (Exception e) {
            assertTrue(e instanceof HttpResponseException);
        }
    }

    @Test
    public void setMediaAssetInvalidDateTest() {

        try {
            List<MediaAsset> bynderMediaAssets = bynderService.getMediaAssets();
            assertNotNull(bynderMediaAssets);

            MediaAsset mediaAsset = new MediaAsset(bynderMediaAssets.get(0).getId(), "Test shall fail", null, null, null, "2015-14-12");
            bynderService.setMediaAssetProperties(mediaAsset);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void setMediaAssetDescriptionTest() throws Exception {

        List<MediaAsset> bynderMediaAssets = bynderService.getMediaAssets();
        assertNotNull(bynderMediaAssets);

        MediaAsset mediaAsset = new MediaAsset(bynderMediaAssets.get(0).getId(), null, "This description was changed through the Java API", null, null, null);

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);

        assertEquals(202, statusCode);
    }

    @Test
    public void setMediaAssetNameAndPublicationDateTest() throws Exception {

        List<MediaAsset> bynderMediaAssets = bynderService.getMediaAssets();
        assertNotNull(bynderMediaAssets);

        MediaAsset mediaAsset = new MediaAsset(bynderMediaAssets.get(0).getId(), "This name was changed through the Java API", null, null, null, "2015-12-14T10:30:00Z");

        int statusCode = bynderService.setMediaAssetProperties(mediaAsset);

        assertEquals(202, statusCode);
    }

    @Test
    public void getAllMetapropertiesTest() throws Exception {

        List<Metaproperty> metaproperties = bynderService.getAllMetaproperties();

        assertNotNull(metaproperties);
    }

    @Test
    public void addMetapropertyToAssetTest() throws Exception {

        List<MediaAsset> bynderMediaAssets = bynderService.getMediaAssets();
        assertNotNull(bynderMediaAssets);
        assertTrue(bynderMediaAssets.size() != 0);

        List<Metaproperty> metaproperties = bynderService.getAllMetaproperties();
        assertNotNull(metaproperties);
        assertTrue(metaproperties.size() != 0);

        int statusCode = 0;
        boolean testDone = false;
        for(Metaproperty metaproperty : metaproperties) {
            if(testDone) {
                break;
            }
            if(metaproperty.getOptions().size() != 0) {
                for(Metaproperty option : metaproperty.getOptions()) {
                    if(option.getOptions().size() != 0) {
                        statusCode = bynderService.addMetapropertyToAsset(bynderMediaAssets.get(0).getId(), metaproperty.getId(), option.getId(), option.getOptions().get(0).getId());
                        testDone = true;
                        break;
                    }
                }
            }
        }

        if(testDone) {
            assertEquals(202, statusCode);
        }
    }

}
