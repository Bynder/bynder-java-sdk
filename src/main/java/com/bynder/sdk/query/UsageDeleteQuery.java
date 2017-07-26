/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to delete asset usage.
 */
public class UsageDeleteQuery {

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
    /**
     * URI with the location of the asset in the integration.
     */
    @ApiField(name = "uri")
    private String location;

    public UsageDeleteQuery(final String integrationId, final String assetId) {
        this.integrationId = integrationId;
        this.assetId = assetId;
    }

    public UsageDeleteQuery setLocation(final String location) {
        this.location = location;
        return this;
    }
}
