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
 * Converts parameter value from boolean to integers 1 (true) or 0 (false) values to send to API.
 */
public class BooleanParameterDecoder implements ParameterDecoder<String, Boolean> {

    @Override
    public Map<String, String> decode(final String key, final Boolean value) {
        Map<String, String> params = new HashMap<>();
        params.put(key, value.booleanValue() ? "1" : "0");
        return params;
    }
}
