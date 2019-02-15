/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.decoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts parameter key from string to "property_<string>" key to send to API.
 */
public class MetapropertyParameterDecoder implements ParameterDecoder<String, Map<String, String>> {

    @Override
    public Map<String, String> decode(final String key, final Map<String, String> values) {
        Map<String, String> params = new HashMap<>();
        values.entrySet().forEach(
            entry -> params.put(String.format("%s_%s", key, entry.getKey()), entry.getValue()));
        return params;
    }
}
