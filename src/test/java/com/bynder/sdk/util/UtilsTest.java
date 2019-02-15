/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 *
 * JUnit framework component copyright (c) 2002-2017 JUnit. All Rights Reserved. Licensed under
 * Eclipse Public License - v 1.0. You may obtain a copy of the License at
 * https://www.eclipse.org/legal/epl-v10.html.
 */
package com.bynder.sdk.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.bynder.sdk.configuration.HttpConnectionSettings;
import com.bynder.sdk.query.MetapropertyAttribute;
import com.bynder.sdk.query.decoder.ApiField;
import com.bynder.sdk.query.decoder.BooleanParameterDecoder;
import com.bynder.sdk.query.decoder.MetapropertyAttributeDecoder;
import com.bynder.sdk.query.decoder.StringArrayParameterDecoder;
import io.reactivex.Observable;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Tests the {@link Utils} class methods.
 */
public class UtilsTest {

    /**
     * API interface only used for test purposes.
     */
    private interface TestApi {

        @GET("api/test/")
        Observable<Response<Void>> getTestMethod();
    }

    /**
     * Query class only used for test purposes.
     */
    private class TestQuery {

        /**
         * Query field.
         */
        @ApiField(name = "field1")
        private final String field1;
        /**
         * Query field.
         */
        @ApiField(name = "field2")
        private final String field2;
        /**
         * Query field.
         */
        private final String field3;

        TestQuery(final String field1, final String field2, final String field3) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
        }
    }

    /**
     * Query class only used for test purposes.
     */
    private class TestConversionQuery {

        /**
         * Query field.
         */
        @ApiField(name = "listField", decoder = StringArrayParameterDecoder.class)
        private final List<String> field1;
        /**
         * Query field.
         */
        @ApiField(name = "metapropertyField", decoder = MetapropertyAttributeDecoder.class)
        private final MetapropertyAttribute field2;
        /**
         * Query field.
         */
        @ApiField(name = "booleanField", decoder = BooleanParameterDecoder.class)
        private final Boolean field3;

        TestConversionQuery(final List<String> field1, final MetapropertyAttribute field2,
            final Boolean field3) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
        }
    }
}
