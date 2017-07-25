/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to create asset usage.
 */
public class UsageCreateQuery {

    /**
     * Id of the integration.
     */
    @ApiField(name = "integration_id")
    private String integrationId;

    /**
     * Id of the asset.
     */
    @ApiField(name = "asset_id")
    private String assetId;

    public UsageCreateQuery(final String integrationId, final String assetId) {
        this.integrationId = integrationId;
        this.assetId = assetId;
    }
}
