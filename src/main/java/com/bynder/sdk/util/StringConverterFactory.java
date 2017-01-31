/**
 * Copyright (c) Bynder. All rights reserved.
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

public class StringConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(final Type type, final Annotation[] annotations, final Retrofit retrofit) {
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
                    return gsonConverterFactory.responseBodyConverter(type, annotations, retrofit).convert(value).toString();
                }
            };
        }
    }
}
