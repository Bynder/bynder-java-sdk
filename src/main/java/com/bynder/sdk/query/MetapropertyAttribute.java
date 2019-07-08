/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Class to specify the metaproperty options to be added to a media asset.
 */
public class MetapropertyAttribute {

    /**
     * Id of the metaproperty to which the metaproperty options belong to.
     */
    private final String metapropertyId;
    /**
     * String array of metaproperty options ids.
     */
    private final String[] optionsIds;

    public MetapropertyAttribute(final String metapropertyId, final String[] optionsIds) {
        this.metapropertyId = metapropertyId;
        this.optionsIds = optionsIds;
    }

    public String getMetapropertyId() {
        return metapropertyId;
    }

    public String[] getOptionsIds() {
        return optionsIds;
    }
}
