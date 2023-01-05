/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.Derivative;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.service.collection.CollectionService;
import com.bynder.sdk.service.oauth.OAuthService;
import com.bynder.sdk.service.workflow.WorkflowService;
import io.reactivex.Observable;
import retrofit2.Response;

import java.util.List;

/**
 * Bynder client interface.
 */
public interface BynderClient {

    /**
     * Gets an instance of the OAuth service to authorize the SDK instance with Bynder and get the
     * access token to perform API requests.
     *
     * @return Instance of {@link OAuthService}.
     */
    OAuthService getOAuthService();

    /**
     * Gets an instance of the asset service to interact with the assets in your Bynder portal.
     *
     * @return Instance of {@link AssetService}.
     */
    AssetService getAssetService();

    /**
     * Gets an instance of the collection service to interact with the collections in your Bynder
     * portal.
     *
     * @return Instance of {@link CollectionService}.
     */
    CollectionService getCollectionService();

    /**
     * Gets the list of the derivatives configured for the current account.
     *
     * @return Observable with the list of {@link Derivative}.
     */
    Observable<Response<List<Derivative>>> getDerivatives();

    WorkflowService getWorkflowService();

    /**
     * Builder class used to create a new instance of {@link BynderClient} using
     * {@link Configuration} as parameter.
     */
    class Builder {

        private Builder() {
        }

        public static BynderClient create(final Configuration configuration) {
            return new BynderClientImpl(configuration, new QueryDecoder());
        }
    }
}
