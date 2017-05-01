/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.util;

import java.lang.reflect.Type;
import java.util.Arrays;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import retrofit2.Retrofit;

/**
 * Class responsible for converting integers to booleans when deserializing the Json response
 * returned by the API. This class is used when the {@link Retrofit} object is instantiated.
 */
public class BooleanTypeAdapter implements JsonDeserializer<Boolean> {

    /**
     * Check {@link JsonDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)} for
     * more information.
     */
    @Override
    public Boolean deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
        if (Arrays.asList(Boolean.TRUE.toString(), Boolean.FALSE.toString()).contains(json.toString())) {
            return json.getAsBoolean();
        } else {
            try {
                int code = json.getAsInt();

                if (code == 0) {
                    return false;
                } else if (code == 1) {
                    return true;
                } else {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }
}
