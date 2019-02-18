/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.decoder;

import java.util.Map;

/**
 * Decoder interface to use in queries to convert parameter name and value from one specific type
 * to {@link Map}.
 *
 * @param <N> Parameter name type.
 * @param <V> Parameter value type.
 */
public interface ParameterDecoder<N, V> {

    Map<String, String> decode(N name, V value);
}
