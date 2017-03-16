/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to specify if we want to get metaproperties with media count or not.
 */
public class MetapropertyQuery {

    /**
     * This property has to be set to 1 (TRUE) for the API to calculate and include the media count
     * for each metaproperty option in the response.
     */
    private Boolean count;

    public MetapropertyQuery(final Boolean count) {
        this.count = count;
    }

    public Boolean getCount() {
        return count;
    }
}
