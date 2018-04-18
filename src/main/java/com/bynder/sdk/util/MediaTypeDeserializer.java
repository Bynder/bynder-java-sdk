/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.util;

import com.bynder.sdk.model.Media;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Retrofit;

/**
 * Class responsible for deserializing the media JSON response
 * returned by the API. This class is used when the {@link Retrofit} object is instantiated.
 */
public class MediaTypeDeserializer implements JsonDeserializer<Media> {

    private static final String KEY_PREFIX = "property_";

    /**
     * Check {@link JsonDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)} for
     * more information.
     */
    @Override
    public Media deserialize(final JsonElement json, final Type typeOfT,
        final JsonDeserializationContext context) {

        final JsonObject jsonObject = json.getAsJsonObject();

        Map<String, List<String>> metaproperties = readMetapropertiesMap(jsonObject);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
        Gson gson = gsonBuilder.create();

        Media media = gson.fromJson(jsonObject, Media.class);
        media.setMetaproperties(metaproperties);

        return media;
    }

    private Map<String, List<String>> readMetapropertiesMap(final JsonObject jsonObject) {
        Map<String, List<String>> metaproperties = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if (entry.getKey().startsWith(KEY_PREFIX)) {
                String key = entry.getKey().substring(KEY_PREFIX.length());
                metaproperties
                    .put(key, Arrays.asList(entry.getValue().getAsString().split("\\s*,\\s*")));
            }
        }

        return metaproperties;
    }
}
