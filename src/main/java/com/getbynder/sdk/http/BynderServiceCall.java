package com.getbynder.sdk.http;

import java.util.concurrent.Future;

import retrofit2.Response;

/**
 * Service Call.
 *
 * @param <T> the generic type
 */
public interface BynderServiceCall<T> {

    /**
     * Synchronous request.
     *
     * @return the generic type
     * @throws RuntimeException the exception from HTTP request
     */
    T execute() throws RuntimeException;

    /**
     * Asynchronous requests, in this case, you receive a callback when the data has been received.
     *
     * @param callback the callback
     */
    Future<Response<T>> executeAsync(BynderServiceCallback<? super T> callback);

    /**
     * Reactive requests, in this case, you could take advantage both synchronous and asynchronous.
     *
     * @return a Observable wrapper for your response
     */
    // Observable<Response<T>> reactive();
}
