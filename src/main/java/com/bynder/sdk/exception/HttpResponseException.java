package com.bynder.sdk.exception;

import retrofit2.Response;

import java.io.IOException;

/**
 * Exception thrown when an error occurs during an API request.
 */
public class HttpResponseException extends Exception {

    private final Response<?> response;

    /**
     * Creates a new instance of the class.
     *
     * @param response response of the request
     */
    public HttpResponseException(final Response<?> response) {
        this.response = response;
    }

    @Override
    public String getMessage() {
        String message = response.toString();
        try {
            message += "\n" + response.errorBody().string();
        } catch (IOException | NullPointerException ignored) {
        }
        return message;
    }

    public Response<?> getResponse() {
        return response;
    }

}
