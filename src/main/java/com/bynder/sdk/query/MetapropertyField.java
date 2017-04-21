package com.bynder.sdk.query;

import java.util.List;

/**
 * Class to specify the metaproperty options to be added to a media asset.
 */
public class MetapropertyField {
    /**
     * Id of the metaproperty to which the metaproperty options belong to.
     */
    private String metapropertyId;
    /**
     * List of metaproperty options ids.
     */
    private List<String> optionsIds;

    public MetapropertyField(final String metapropertyId, final List<String> optionsIds) {
        this.metapropertyId = metapropertyId;
        this.optionsIds = optionsIds;
    }

    public String getMetapropertyId() {
        return metapropertyId;
    }

    public List<String> getOptionsIds() {
        return optionsIds;
    }
}
