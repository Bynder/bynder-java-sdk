/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class responsible for converting a String response from the API. This class is used when the
 * {@link Retrofit} object is instantiated.
 */
public class StringConverterFactory extends Converter.Factory {

    /**
     * Check {@link Converter.Factory#responseBodyConverter(Type, Annotation[], Retrofit)} for more
     * information.
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(final Type type,
        final Annotation[] annotations, final Retrofit retrofit) {
        if (!String.class.equals(type)) {
            return null;
        } else {
            return new Converter<ResponseBody, String>() {
                @Override
                public String convert(final ResponseBody value) throws IOException {
                    if (value.contentType().type().contains("text")) {
                        return value.string();
                    }
                    GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
                    return gsonConverterFactory.responseBodyConverter(type, annotations, retrofit)
                        .convert(value).toString();
                }
            };
        }
    }
}
