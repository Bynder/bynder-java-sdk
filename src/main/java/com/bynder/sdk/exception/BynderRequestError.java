package com.bynder.sdk.exception;

import retrofit2.Response;

/**
 * Exception thrown when an error occurs during an API request.
 */
public class BynderRequestError extends Exception {

    private final Response<?> response;

    /**
     * Creates a new instance of the class.
     *
     * @param response response of the request
     */
    public BynderRequestError(final Response<?> response) {
        super(response.toString());
        this.response = response;
    }

    public Response<?> getResponse() {
        return response;
    }

}

