package com.bynder.sdk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bynder.sdk.model.Media;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class MediaTypeAdapter implements JsonDeserializer<Media> {

    private static final String PROPERTY_PREFIX = "property_";
    private static final String CUSTOM_METAPROPERTY_FIELDNAME = "customMetaproperties";

    private final Gson gson;

    public MediaTypeAdapter() {
        this.gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
    }

    @Override
    public Media deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Media media = gson.fromJson(jsonObject, Media.class);

        Map<String, List<String>> metaproperties = new LinkedHashMap<>();

        for (Map.Entry<String, JsonElement> elementJson : jsonObject.entrySet()) {
            if (elementJson.getKey().startsWith(PROPERTY_PREFIX)) {
                String propertyName = elementJson.getKey().substring(PROPERTY_PREFIX.length());
                List<String> values = metaproperties.getOrDefault(metaproperties, new ArrayList<>());
                if (elementJson.getValue().isJsonArray()) {
                    for (JsonElement element : elementJson.getValue().getAsJsonArray()) {
                        values.add(element.getAsString());
                    }
                } else {
                    values.add(elementJson.getValue().getAsString());
                }
                metaproperties.put(propertyName, values);
            }
        }
        setMetaproperties(media, metaproperties);
        return media;
    }

    private void setMetaproperties(Media media, Map<String, List<String>> metaproperties) {
        try {
            Field metapropertiesField = media.getClass().getDeclaredField(CUSTOM_METAPROPERTY_FIELDNAME);
            metapropertiesField.setAccessible(true);
            metapropertiesField.set(media, metaproperties);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            // does not occur unless Media class is changed
        }
    }

}
