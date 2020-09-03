/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.decoder;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts parameter name from string to "property_name" to send to API.
 */
public class JsonParameterDecoder implements ParameterDecoder<String, String[]> {

    @Override
    public Map<String, String> decode(final String name, final String[] values) {
        Map<String, String> parameters = new HashMap<>();
        Gson gson = new Gson();
        parameters.put(name, gson.toJson(values));
        return parameters;
    }
}
