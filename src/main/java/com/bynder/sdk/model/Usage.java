/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.Map;

public class Usage {

    private String id;
    private String assetId;
    private String integrationId;
    private Map<String, String> integration;
    private String uri;
    private String timestamp;
    private String additional;

    public String getId() {
        return id;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getIntegrationId() {
        return integrationId;
    }


    public Map<String, String> getIntegration() {
        return integration;
    }

    public String getUri() {
        return uri;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAdditional() {
        return additional;
    }
}
