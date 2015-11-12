package com.getbynder.api;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.getbynder.api.domain.Category;
import com.getbynder.api.domain.ImageAsset;

/**
 *
 * Technology is the devil
 *
 * @author daniel.sequeira
 */
public class BynderServiceTest {

    private BynderService bynderService;

    @Before
    public void setUp() throws Exception {
        bynderService = new BynderService("https://workflow-api.bynder.info/api/v4/", "barcelona-admin", "barcelona-admin");
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
