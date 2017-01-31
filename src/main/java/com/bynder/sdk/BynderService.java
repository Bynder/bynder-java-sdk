/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.Category;
import com.bynder.sdk.model.FinaliseResponse;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.MediaCount;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.PollStatus;
import com.bynder.sdk.model.Tag;
import com.bynder.sdk.model.UploadRequest;
import com.bynder.sdk.model.User;
import com.bynder.sdk.service.BynderServiceCall;
import com.bynder.sdk.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

/**
 *
 * @deprecated
 */
@Deprecated
public class BynderService {

    private final String baseUrl;
    private final String consumerKey;
    private final String consumerSecret;
    private final BynderApi bynderApi;

    public BynderService(final String baseUrl, final String consumerKey, final String consumerSecret, final String token, final String tokenSecret) {
        this.baseUrl = baseUrl;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;

        bynderApi = Utils.createRetrofitApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, token, tokenSecret);
    }

    public BynderService(final String baseUrl, final String consumerKey, final String consumerSecret, final String requestTokenKey, final String requestTokenSecret, final String username,
            final String password) {
        this.baseUrl = baseUrl;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;

        User user = login(username, password).execute();
        bynderApi = Utils.createRetrofitApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, user.getTokenKey(), user.getTokenSecret());
    }

    private final <T> BynderServiceCall<T> createServiceCall(final Call<T> call) {
        return new BynderServiceCall<T>() {
            @Override
            public T execute() throws RuntimeException {
                try {
                    Response<T> response = call.execute();
                    return response.body();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Observable<T> executeAsync() {
                return Observable.create(new OnSubscribe<T>() {
                    @Override
                    public void call(final Subscriber<? super T> subscriber) {
                        call.enqueue(new Callback<T>() {
                            @Override
                            public void onResponse(final Call<T> call, final Response<T> response) {
                                subscriber.onNext(response.body());
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onFailure(final Call<T> call, final Throwable t) {
                                subscriber.onError(t);
                            }
                        });
                    }
                });
            }
        };
    }

    public BynderServiceCall<User> login(final String username, final String password) {
        Call<User> call = bynderApi.login(username, password);
        return createServiceCall(call);
    }

    public BynderServiceCall<String> getRequestToken() {
        BynderApi bynderApiForRequestToken = Utils.createRetrofitApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, null, null);
        Call<String> call = bynderApiForRequestToken.getRequestToken();
        return createServiceCall(call);
    }

    public BynderServiceCall<String> getAccessToken(final String requestToken, final String requestTokenSecret) {
        BynderApi bynderApiForAccessToken = Utils.createRetrofitApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, requestToken, requestTokenSecret);
        Call<String> call = bynderApiForAccessToken.getAccessToken();
        return createServiceCall(call);
    }

    public String getAuthoriseUrl(final String requestToken, final String callbackUrl) {
        StringBuilder stringBuilder = new StringBuilder(baseUrl).append("v4/oauth/authorise/?oauth_token=").append(requestToken);

        if (StringUtils.isNotEmpty(callbackUrl)) {
            stringBuilder.append("&callback=").append(callbackUrl);
        }

        return stringBuilder.toString();
    }

    public BynderServiceCall<List<Brand>> getBrands() {
        Call<List<Brand>> call = bynderApi.getBrands();
        return createServiceCall(call);
    }

    public BynderServiceCall<List<Category>> getCategories() {
        Call<List<Category>> call = bynderApi.getCategories();
        return createServiceCall(call);
    }

    public BynderServiceCall<List<Tag>> getTags() {
        Call<List<Tag>> call = bynderApi.getTags();
        return createServiceCall(call);
    }

    public BynderServiceCall<Map<String, Metaproperty>> getMetaproperties() {
        Call<Map<String, Metaproperty>> call = bynderApi.getMetaproperties();
        return createServiceCall(call);
    }

    public BynderServiceCall<Void> addMetapropertyToAsset(final String assetId, final String metapropertyId, final String... optionsIds) {
        Map<String, String> metapropertyOptions = new HashMap<>();
        metapropertyOptions.put(String.format("metaproperty.%s", metapropertyId), StringUtils.join(optionsIds, Utils.STR_COMMA));

        Call<Void> call = bynderApi.addMetapropertyToAsset(assetId, metapropertyOptions);
        return createServiceCall(call);
    }

    public BynderServiceCall<List<Media>> getMediaList(final String type, final String keyword, final Integer limit, final Integer page, final List<String> propertyOptionIds) {
        Call<List<Media>> call = bynderApi.getMediaList(type, keyword, limit, page, StringUtils.join(propertyOptionIds, Utils.STR_COMMA));
        return createServiceCall(call);
    }

    public BynderServiceCall<MediaCount> getMediaWithCount() {
        Call<MediaCount> call = bynderApi.getMediaWithCount();
        return createServiceCall(call);
    }

    public BynderServiceCall<Media> getMediaById(final String id, final Boolean versions) {
        Call<Media> call = bynderApi.getMediaById(id, versions);
        return createServiceCall(call);
    }

    public BynderServiceCall<UploadRequest> getUploadInformation(final String filename) {
        Call<UploadRequest> call = bynderApi.getUploadInformation(filename);
        return createServiceCall(call);
    }

    public BynderServiceCall<String> getClosestS3Endpoint() {
        Call<String> call = bynderApi.getClosestS3Endpoint();
        return createServiceCall(call);
    }

    public BynderServiceCall<Void> registerChunk(final UploadRequest uploadRequest, final int chunkNumber) {
        String filename = String.format("%s/p%s", uploadRequest.getS3Filename(), Integer.toString(chunkNumber));
        Call<Void> call = bynderApi.registerChunk(uploadRequest.getS3File().getUploadId(), chunkNumber, uploadRequest.getS3File().getTargetId(), filename);
        return createServiceCall(call);
    }

    public BynderServiceCall<FinaliseResponse> finaliseUploaded(final UploadRequest uploadRequest, final int chunks) {
        String filename = String.format("%s/p%s", uploadRequest.getS3Filename(), Integer.toString(chunks));
        Call<FinaliseResponse> call = bynderApi.finaliseUploaded(uploadRequest.getS3File().getUploadId(), uploadRequest.getS3File().getTargetId(), filename, chunks);
        return createServiceCall(call);
    }

    public BynderServiceCall<PollStatus> pollStatus(final List<String> items) {
        Call<PollStatus> call = bynderApi.pollStatus(StringUtils.join(items, Utils.STR_COMMA));
        return createServiceCall(call);
    }

    public BynderServiceCall<Void> saveMedia(final String importId, final String brandId, final String name) {
        Call<Void> call = bynderApi.saveMedia(importId, brandId, name);
        return createServiceCall(call);
    }

    public BynderServiceCall<Void> saveMediaVersion(final String mediaId, final String importId) {
        Call<Void> call = bynderApi.saveMediaVersion(mediaId, importId);
        return createServiceCall(call);
    }
}

