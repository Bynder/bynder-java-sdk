package com.getbynder.sdk.util;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import com.getbynder.sdk.domain.Count;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author daniel.sequeira
 */
public class CountTypeAdapter implements JsonDeserializer<Count> {

    @Override
    public Count deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        Count count = new Gson().fromJson(json, Count.class);

        for (Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
            if (entry.getKey().startsWith("property_")) {
                String name = entry.getKey().substring(entry.getKey().indexOf("_") + 1);

                Type type = new TypeToken<Map<String, Integer>>() {}.getType();
                Map<String, Integer> options = new Gson().fromJson(entry.getValue(), type);

                count.addMetaproperty(name, options);
            }
        }

        return count;
    }
}
