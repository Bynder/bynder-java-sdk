package com.getbynder.sdk.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 *
 * @author daniel.sequeira
 */
public class StringConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(final Type type, final Annotation[] annotations, final Retrofit retrofit) {
        if (!String.class.equals(type)) {
            return null;
        } else {
            return new Converter<ResponseBody, String>() {
                @Override
                public String convert(final ResponseBody value) throws IOException {
                    return value.string();
                }
            };
        }
    }
}
