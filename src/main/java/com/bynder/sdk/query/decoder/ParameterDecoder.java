/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.decoder;

import java.util.Map;

/**
 * Decoder interface to use in queries to convert parameter key and value from one specific type
 * to Map<String, String>.
 *
 * @param <K, V> Key and value types to convert from.
 */
public interface ParameterDecoder<K, V> {

    Map<String, String> decode(K key, V value);
}
