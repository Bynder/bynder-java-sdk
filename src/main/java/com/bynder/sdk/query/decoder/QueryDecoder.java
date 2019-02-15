package com.bynder.sdk.query.decoder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Decodes query object to a Map of parameters.
 */
public class QueryDecoder {

    /**
     * Method called for each field in a query object. It extracts the different fields with
     * {@link ApiField} annotation and, if needed, calls appropriate converter to convert
     * property value to string.
     *
     * @param field Field information.
     * @param query Query object.
     * @param params Parameters name/value pairs to send to the API.
     * @throws IllegalAccessException If the Field object is inaccessible.
     */
    private static void convertField(final Field field, final Object query,
        final Map<String, String> params) throws IllegalAccessException {
        field.setAccessible(true);
        ApiField apiField = field.getAnnotation(ApiField.class);
        Object fieldValue = field.get(query);
        if (apiField != null && fieldValue != null) {
            String name =
                ApiField.DEFAULT_NAME.equals(apiField.name()) ? field.getName() : apiField.name();

            if (apiField.decoder().equals(void.class)) {
                params.put(name, fieldValue.toString());
            } else {
                ParameterDecoder parameterDecoder = null;
                try {
                    parameterDecoder = (ParameterDecoder) apiField.decoder().newInstance();
                } catch (InstantiationException e) {
                    // Failed to instantiate class. Nothing to do.
                }

                params.putAll(parameterDecoder.decode(name, fieldValue));
            }
        }

        field.setAccessible(false);
    }

    /**
     * Given a query object this method gets its API parameters. The parameters are basically the
     * fields of the query object that have {@link ApiField} annotation.
     *
     * @param query Query object.
     * @return Map with parameters name/value pairs to send to the API.
     */
    public Map<String, String> decode(final Object query) {
        Map<String, String> params = new HashMap<>();
        if (query != null) {
            List<Field> fields = new ArrayList<>();

            fields.addAll(Arrays.asList(query.getClass().getDeclaredFields()));

            if (query.getClass().getSuperclass().getDeclaredFields() != null) {
                fields.addAll(Arrays.asList(query.getClass().getSuperclass().getDeclaredFields()));
            }

            for (Field field : fields) {
                try {
                    convertField(field, query, params);
                } catch (IllegalAccessException e) {
                    // Nothing to do. This will never happen as
                    // we are iterating over the fields.
                }
            }
        }

        return params;
    }
}
