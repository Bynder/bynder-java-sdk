package com.getbynder.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.http.client.HttpResponseException;
import org.junit.Before;
import org.junit.Test;

import com.getbynder.api.domain.Category;
import com.getbynder.api.domain.ImageAsset;

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
    public void getUserAccessDataFail() {

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

        //to be deleted
        for(Category category : categories){
            System.out.println(category.toString());
        }
    }

    @Test
    public void getAllImageAssetsTest() throws Exception {

        List<ImageAsset> bynderAllImageAssets = bynderService.getImageAssets();

        assertNotNull(bynderAllImageAssets);

        //to be deleted
        for(ImageAsset bynderImageAsset : bynderAllImageAssets){
            System.out.println(bynderImageAsset.toString());
        }
    }

    @Test
    public void getImageAssetsTest() throws Exception {

        List<ImageAsset> bynderImageAssets = bynderService.getImageAssets(5, 1);

        assertNotNull(bynderImageAssets);

        //to be deleted
        for(ImageAsset bynderImageAsset : bynderImageAssets){
            System.out.println(bynderImageAsset.toString());
        }
    }

    @Test
    public void getImageAssetCountTest() throws Exception {

        int imageAssetCount = bynderService.getImageAssetCount();

        assertNotNull(imageAssetCount);
    }

}
