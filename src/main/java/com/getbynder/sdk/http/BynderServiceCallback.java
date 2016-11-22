package com.getbynder.sdk.http;

/**
 * Callback with the response for an Asynchronous request.
 *
 * @param <T> the generic type
 */
public interface BynderServiceCallback<T> {

    /**
     * Called with the response.
     *
     * @param response the response
     */
    void onResponse(final T response);

    /**
     * Called if there is an error during the request.
     *
     * @param e the exception thrown during the request
     */
    void onFailure(Exception e);
}
