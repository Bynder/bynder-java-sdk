package com.getbynder.sdk;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.domain.MediaAssetRequest;
import com.getbynder.sdk.domain.Metaproperty;
import com.getbynder.sdk.service.MediaService;
import com.getbynder.sdk.service.MetapropertyService;

/**
 *
 * @author daniel.sequeira
 */
public class MetapropertyServiceIT {

    private static final Logger LOG = LoggerFactory.getLogger(MetapropertyServiceIT.class);

    private MetapropertyService metapropertyService;
    private MediaService mediaService;

    @Before
    public void setUp() throws Exception {
        metapropertyService = new MetapropertyService();
        mediaService = new MediaService();
    }

    @Test
    public void getMetapropertiesSyncTest() {
        Map<String, Metaproperty> metaproperties = metapropertyService.getMetaproperties().execute();
        assertTrue(metaproperties.size() > 0);
    }

    // @Test
    // public void getMetapropertiesAsyncTest() throws InterruptedException {
    // final Map<String, Metaproperty> metaproperties = new HashMap<String, Metaproperty>();
    // metapropertyService.getMetaproperties().enqueue(new BynderServiceCallback<Map<String,
    // Metaproperty>>() {
    // @Override
    // public void onResponse(final Map<String, Metaproperty> response) {
    // metaproperties.putAll(response);
    // }
    //
    // @Override
    // public void onFailure(final Exception e) {
    // LOG.error(e.getMessage());
    // }
    // });
    //
    // Thread.sleep(1000);
    // assertTrue(metaproperties.size() > 0);
    // }
    //
    // @Test
    // public void getMetapropertiesRxTest() throws InterruptedException {
    // final Map<String, Metaproperty> metaproperties = new HashMap<String, Metaproperty>();
    // metapropertyService.getMetaproperties().reactive().subscribe(new
    // Subscriber<Response<Map<String, Metaproperty>>>() {
    // @Override
    // public void onCompleted() {
    // LOG.info("reactive request completed successfully");
    // }
    //
    // @Override
    // public void onError(final Throwable e) {
    // LOG.error(e.getMessage());
    // }
    //
    // @Override
    // public void onNext(final Response<Map<String, Metaproperty>> response) {
    // metaproperties.putAll(response.body());
    // }
    // });
    //
    // Thread.sleep(1000);
    // assertTrue(metaproperties.size() > 0);
    // }

    @Test
    public void addMetapropertyToAssetSyncTest() throws InterruptedException {
        Map<String, Metaproperty> metaproperties = metapropertyService.getMetaproperties().execute();
        assertTrue(metaproperties.entrySet().size() > 0);

        MediaAssetRequest mediaAssetRequest = new MediaAssetRequest(null, 1, 1, null);
        List<MediaAsset> imageAssets = mediaService.getImageAssets(mediaAssetRequest).execute();
        assertNotNull(imageAssets);
        assertTrue(imageAssets.size() > 0);

        String imageAssetId = imageAssets.get(0).getId();

        metapropertyService.addMetapropertyToAsset(imageAssetId, metaproperties.get(0).getId(), "");
        MediaAsset imageAsset = mediaService.getMediaAssetById(imageAssetId, null).execute();
        assertTrue(!imageAsset.getPropertyOptions().contains(metaproperties.get(0).getId()));
        metapropertyService.addMetapropertyToAsset(imageAssetId, metaproperties.get(0).getId(), metaproperties.get(0).getOptions().get(0).getId());
        assertTrue(imageAsset.getPropertyOptions().contains(metaproperties.get(0).getId()));
    }

    @Test
    public void addMetapropertyToAssetAsyncTest() throws InterruptedException {
        // TODO integration async test for addMetapropertyToAsset
    }

    @Test
    public void addMetapropertyToAssetRxTest() throws InterruptedException {
        // TODO integration reactive test for addMetapropertyToAsset
    }
}
