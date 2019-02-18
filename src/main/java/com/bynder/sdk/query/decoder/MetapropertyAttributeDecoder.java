/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.decoder;

import com.bynder.sdk.query.MetapropertyAttribute;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts attribute name from string to "metaproperty.<string>" to send to API.
 */
public class MetapropertyAttributeDecoder implements
    ParameterDecoder<String, MetapropertyAttribute> {

    @Override
    public Map<String, String> decode(final String name, final MetapropertyAttribute value) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(String.format("%s.%s", name, value.getMetapropertyId()),
            String.join(",", value.getOptionsIds()));
        return parameters;
    }
}
