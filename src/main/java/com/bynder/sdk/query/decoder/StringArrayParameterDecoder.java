/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.decoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts parameter value from string array to a string of comma separated values to send to API.
 */
public class StringArrayParameterDecoder implements ParameterDecoder<String, String[]> {

    @Override
    public Map<String, String> decode(final String name, final String[] value) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(name, String.join(",", value));
        return parameters;
    }
}
