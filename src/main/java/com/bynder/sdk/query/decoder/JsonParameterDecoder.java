/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.decoder;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts parameter key from string to "property_<string>" key to send to API.
 */
public class JsonParameterDecoder implements ParameterDecoder<String, String[]> {

    @Override
    public Map<String, String> decode(final String key, final String[] values) {
        Map<String, String> params = new HashMap<>();
        Gson gson = new Gson();
        params.put(key, gson.toJson(values));
        return params;
    }
}
