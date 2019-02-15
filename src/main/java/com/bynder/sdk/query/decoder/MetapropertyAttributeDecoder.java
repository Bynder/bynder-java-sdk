/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.decoder;

import com.bynder.sdk.query.MetapropertyAttribute;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts attribute key from string to "metaproperty.<string>" key to send to API.
 */
public class MetapropertyAttributeDecoder implements
    ParameterDecoder<String, MetapropertyAttribute> {

    @Override
    public Map<String, String> decode(final String key, final MetapropertyAttribute value) {
        Map<String, String> params = new HashMap<>();
        params.put(String.format("%s.%s", key, value.getMetapropertyId()),
            String.join(",", value.getOptionsIds()));
        return params;
    }
}
