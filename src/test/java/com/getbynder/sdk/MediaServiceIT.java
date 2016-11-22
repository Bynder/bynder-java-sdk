package com.getbynder.sdk;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.domain.MediaAssetRequest;
import com.getbynder.sdk.service.MediaService;

/**
 *
 * @author daniel.sequeira
 */
public class MediaServiceIT {

    private static final Logger LOG = LoggerFactory.getLogger(MediaServiceIT.class);

    private MediaService mediaService;
    private MediaAssetRequest mediaAssetRequest;

    @Before
    public void setUp() {
        mediaService = new MediaService();
        mediaAssetRequest = new MediaAssetRequest(null, 1, 1, null);
    }

    @Test
    public void getImageAssetsSyncTest() {
        List<MediaAsset> imageAssets = mediaService.getImageAssets(mediaAssetRequest).execute();
        assertNotNull(imageAssets);
    }

    // @Test
    // public void getImageAssetsAsyncTest() throws InterruptedException {
    // final List<MediaAsset> imageAssets = new ArrayList<>();
    // mediaService.getImageAssets(mediaAssetRequest).enqueue(new
    // BynderServiceCallback<List<MediaAsset>>() {
    // @Override
    // public void onResponse(final List<MediaAsset> response) {
    // imageAssets.addAll(response);
    // }
    //
    // @Override
    // public void onFailure(final Exception e) {
    // LOG.error(e.getMessage());
    // }
    // });
    //
    // Thread.sleep(1000);
    // assertTrue(imageAssets.size() > 0);
    // }
    //
    // @Test
    // public void getImageAssetsRxTest() throws InterruptedException {
    // final List<MediaAsset> imageAssets = new ArrayList<>();
    // mediaService.getImageAssets(mediaAssetRequest).reactive().subscribe(new
    // Subscriber<Response<List<MediaAsset>>>() {
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
    // public void onNext(final Response<List<MediaAsset>> response) {
    // imageAssets.addAll(response.body());
    // }
    // });
    //
    // Thread.sleep(1000);
    // assertTrue(imageAssets.size() > 0);
    // }

    @Test
    public void getMediaAssetByIdSyncTest() {
        List<MediaAsset> imageAssets = mediaService.getImageAssets(mediaAssetRequest).execute();
        assertNotNull(imageAssets);
        assertTrue(imageAssets.size() > 0);

        String mediaAssetId = imageAssets.get(0).getId();

        final MediaAsset mediaAsset = mediaService.getMediaAssetById(mediaAssetId, null).execute();
        assertNotNull(mediaAsset);
    }

    // @Test
    // public void getMediaAssetByIdAsyncTest() throws InterruptedException {
    // List<MediaAsset> imageAssets = mediaService.getImageAssets(mediaAssetRequest).execute();
    // assertNotNull(imageAssets);
    // assertTrue(imageAssets.size() > 0);
    //
    // String mediaAssetId = imageAssets.get(0).getId();
    //
    // final List<MediaAsset> mediaAssets = new ArrayList<>();
    // mediaService.getMediaAssetById(mediaAssetId, null).enqueue(new
    // BynderServiceCallback<MediaAsset>() {
    // @Override
    // public void onResponse(final MediaAsset response) {
    // mediaAssets.add(response);
    // }
    //
    // @Override
    // public void onFailure(final Exception e) {
    // LOG.error(e.getMessage());
    // }
    // });
    //
    // Thread.sleep(1000);
    // assertTrue(mediaAssets.size() == 1);
    // assertNotNull(mediaAssets.get(0));
    // assertNotNull(mediaAssets.get(0).getId());
    // }
    //
    // @Test
    // public void getMediaAssetByIdRxTest() throws InterruptedException {
    // List<MediaAsset> imageAssets = mediaService.getImageAssets(mediaAssetRequest).execute();
    // assertNotNull(imageAssets);
    // assertTrue(imageAssets.size() > 0);
    //
    // String mediaAssetId = imageAssets.get(0).getId();
    //
    // final List<MediaAsset> mediaAssets = new ArrayList<>();
    // mediaService.getMediaAssetById(mediaAssetId, null).reactive().subscribe(new
    // Subscriber<Response<MediaAsset>>() {
    //
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
    // public void onNext(final Response<MediaAsset> response) {
    // mediaAssets.add(response.body());
    // }
    // });
    //
    // Thread.sleep(1000);
    // assertTrue(mediaAssets.size() == 1);
    // assertNotNull(mediaAssets.get(0));
    // assertNotNull(mediaAssets.get(0).getId());
    // }
}
