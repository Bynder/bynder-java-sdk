package com.getbynder.api.util;

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
