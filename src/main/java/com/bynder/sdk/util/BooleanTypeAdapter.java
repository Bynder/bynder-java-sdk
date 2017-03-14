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

public class BooleanTypeAdapter implements JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
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
