package com.getbynder.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.util.Constants;

/**
 *
 * @author daniel.sequeira
 */
public class BynderApiServiceTest {

    private final List<String> PROPERTY_OPTION_IDS = Arrays.asList("POID");

    private BynderApiService mockedBynderApiService;

    @Before
    public void setUp() throws Exception {
        mockedBynderApiService = mock(BynderApiService.class);

        List<MediaAsset> imageAssets = new ArrayList<>();
        MediaAsset imageAsset = null;

        for (int i = 1; i < 7; i++) {
            if (i % 3 == 0) {
                imageAsset = new MediaAsset(Integer.toString(i), Integer.toString(i), null, null, null, null, Constants.MEDIA_TYPE_IMAGE, null, null, null);
            } else {
                imageAsset = new MediaAsset(Integer.toString(i), Integer.toString(i), null, null, null, null, Constants.MEDIA_TYPE_IMAGE, PROPERTY_OPTION_IDS, null, null);
            }
            imageAssets.add(imageAsset);
        }

        when(mockedBynderApiService.getImageAssets(Mockito.anyString(), Mockito.eq(50), Mockito.eq(1))).thenReturn(imageAssets);
        when(mockedBynderApiService.getImageAssets(Mockito.anyString(), Mockito.eq(50), Mockito.eq(2))).thenReturn(new ArrayList<MediaAsset>());
        when(mockedBynderApiService.getImageAssetsForLazyLoading(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyListOf(String.class))).thenCallRealMethod();
        when(mockedBynderApiService.getImageAssetsTotal(Mockito.anyString(), Mockito.anyListOf(String.class))).thenCallRealMethod();
    }

    @Test
    public void getImageAssetsForLazyLoadingTest() throws Exception {
        List<MediaAsset> imageAssets = mockedBynderApiService.getImageAssetsForLazyLoading(null, 3, 1, PROPERTY_OPTION_IDS);
        assertNotNull(imageAssets);
        assertEquals(3, imageAssets.size());

        imageAssets = mockedBynderApiService.getImageAssetsForLazyLoading(null, 3, 2, PROPERTY_OPTION_IDS);
        assertNotNull(imageAssets);
        assertEquals(1, imageAssets.size());

        imageAssets = mockedBynderApiService.getImageAssetsForLazyLoading(null, 2, 1, PROPERTY_OPTION_IDS);
        assertNotNull(imageAssets);
        assertEquals(2, imageAssets.size());

        imageAssets = mockedBynderApiService.getImageAssetsForLazyLoading(null, 2, 2, PROPERTY_OPTION_IDS);
        assertNotNull(imageAssets);
        assertEquals(2, imageAssets.size());

        imageAssets = mockedBynderApiService.getImageAssetsForLazyLoading(null, 2, 3, PROPERTY_OPTION_IDS);
        assertNotNull(imageAssets);
        assertEquals(0, imageAssets.size());

        imageAssets = mockedBynderApiService.getImageAssetsForLazyLoading(null, 4, 1, PROPERTY_OPTION_IDS);
        assertNotNull(imageAssets);
        assertEquals(4, imageAssets.size());

        imageAssets = mockedBynderApiService.getImageAssetsForLazyLoading(null, 6, 1, PROPERTY_OPTION_IDS);
        assertNotNull(imageAssets);
        assertEquals(4, imageAssets.size());

        imageAssets = mockedBynderApiService.getImageAssetsForLazyLoading(null, 50, 1, null);
        assertNotNull(imageAssets);
        assertEquals(6, imageAssets.size());

        imageAssets = mockedBynderApiService.getImageAssetsForLazyLoading(null, 50, 1, new ArrayList<String>());
        assertNotNull(imageAssets);
        assertEquals(6, imageAssets.size());
    }

    @Test
    public void getImageAssetsTotalByMetapropertyIdsTest() throws Exception {
        int total = mockedBynderApiService.getImageAssetsTotal(null, Arrays.asList("ID"));
        assertEquals(0, total);

        total = mockedBynderApiService.getImageAssetsTotal(null, PROPERTY_OPTION_IDS);
        assertEquals(4, total);
    }
}
