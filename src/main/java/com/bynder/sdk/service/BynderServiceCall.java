/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import rx.Observable;

public interface BynderServiceCall<T> {

    /**
     * Synchronous request.
     *
     * @return the generic type
     * @throws RuntimeException the exception from HTTP request
     */
    T execute() throws RuntimeException;

    /**
     * Asynchronous request.
     *
     * @return a Observable wrapper for your response
     */
    Observable<T> executeAsync();
}
