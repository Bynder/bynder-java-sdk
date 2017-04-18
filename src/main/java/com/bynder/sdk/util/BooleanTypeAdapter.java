/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import retrofit2.Retrofit;

/**
 * Class responsible for converting integers to booleans when deserializing the json response
 * returned by the API. This class is used when the {@link Retrofit} object is instantiated.
 */
public class BooleanTypeAdapter implements JsonDeserializer<Boolean> {

    /**
     * Check {@link JsonDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)} for
     * more information.
     */
    @Override
    public Boolean deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (Boolean.class.equals(typeOfT)) {
            return json.getAsBoolean();
        } else {
            int code = json.getAsInt();

            if (code == 0) {
                return false;
            } else if (code == 1) {
                return true;
            } else {
                return null;
            }
        }
    }
}
