package com.getbynder.sdk.service;

import java.util.List;

import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.domain.MediaAssetRequest;
import com.getbynder.sdk.http.BynderServiceCall;

import retrofit2.Call;

/**
 *
 * @author daniel.sequeira
 */
public class MediaService extends BynderService {

    public BynderServiceCall<MediaAsset> getMediaAssetById(final String id, final Boolean versions) {
        final Call<MediaAsset> call = apiService.getMediaAssetById(id, versions);

        return createServiceCall(call);
    }

    public BynderServiceCall<List<MediaAsset>> getImageAssets(final MediaAssetRequest mediaAssetRequest) {
        final Call<List<MediaAsset>> call =
                apiService.getImageAssets(mediaAssetRequest.getKeyword(), mediaAssetRequest.getLimit(), mediaAssetRequest.getPage(), mediaAssetRequest.getPropertyOptionId());

        return createServiceCall(call);
    }
}
