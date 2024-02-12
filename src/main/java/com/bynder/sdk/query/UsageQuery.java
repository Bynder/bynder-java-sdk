/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import com.bynder.sdk.query.decoder.ApiField;

/**
 * Query to filter asset usage results.
 */
public class UsageQuery {

    /**
     * Id of the asset.
     */
    @ApiField(name = "asset_id")
    private String assetId;

    /**
     * Uri of the asset
     */
    @ApiField(name = "uri")
    private String uri;

    public String getAssetId() {
        return assetId;
    }

    public UsageQuery setAssetId(final String assetId) {
        this.assetId = assetId;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public UsageQuery setUri(final String uri) {
        this.uri = uri;
        return this;
    }
}
