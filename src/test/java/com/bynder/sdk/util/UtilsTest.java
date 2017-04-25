/**
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

import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.bynder.sdk.model.Credentials;
import com.bynder.sdk.query.ApiField;
import com.bynder.sdk.query.ConversionType;
import com.bynder.sdk.query.MetapropertyField;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Tests the {@link Utils} class methods.
 */
public class UtilsTest {

    /**
     * Tests that {@link Utils#buildMapFromResponse(String)} correctly converts the API response to
     * a {@link Map}.
     */
    @Test
    public void buildMapFromResponseTest() {
        String keyA = "keyA";
        String keyB = "keyB";
        String valueA = "valueA";
        String valueB = "valueB";

        String response = new StringBuilder(keyA).append(Utils.STR_EQUALS).append(valueA).append(Utils.STR_AND).append(keyB).append(Utils.STR_EQUALS).append(valueB).toString();

        Map<String, String> map = Utils.buildMapFromResponse(response);
        assertNotNull(map);
        assertEquals(2, map.size());
        assertEquals(valueA, map.get(keyA));
        assertEquals(valueB, map.get(keyB));

        response = new StringBuilder(keyA).append(Utils.STR_EQUALS).append(valueA).toString();

        map = Utils.buildMapFromResponse(response);
        assertNotNull(map);
        assertEquals(1, map.size());
        assertEquals(valueA, map.get(keyA));
    }

    /**
     * Tests that if the API response has invalid format the expected exception is thrown by
     * {@link Utils#buildMapFromResponse(String)}.
     */
    @Test(expected = InvalidParameterException.class)
    public void buildMapFromResponseFailTest() {
        String keyA = "keyA";
        String keyB = "keyB";
        String valueA = "valueA";

        String response = new StringBuilder(keyA).append(Utils.STR_EQUALS).append(valueA).append(Utils.STR_AND).append(keyB).append(Utils.STR_EQUALS).toString();
        Utils.buildMapFromResponse(response);
    }

    /**
     * Tests that {@link Utils#createApiService(Class, URL, String, String, String, String)}
     * correctly creates the API endpoints defined in a given interface.
     */
    @Test
    public void createApiServiceTest() throws MalformedURLException {
        TestApi testApi = Utils.createApiService(TestApi.class, new URL("https://example.bynder.com"), new Credentials("consumerKey", "consumerSecret", "tokenKey", "tokenSecret"));
        assertNotNull(testApi);
        assertTrue(testApi.getTestMethod() instanceof Observable<?>);
    }

    /**
     * API interface only used for test purposes.
     */
    private interface TestApi {
        @GET("api/test/")
        Observable<Response<Void>> getTestMethod();
    }

    /**
     * Tests that {@link Utils#getApiParameters(Object)} returns only the parameters for the class
     * fields that have the {@link ApiField} annotation.
     */
    @Test
    public void getApiParametersWithoutConversionTest() throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(new TestQuery("1", "2", "3"));
        assertEquals(2, params.size());
        assertEquals("1", params.get("field1"));
        assertEquals("2", params.get("field2"));
    }

    /**
     * Tests that {@link Utils#getApiParameters(Object)} correctly converts the fields that specify
     * a {@link ConversionType} in the {@link ApiField} annotation.
     */
    @Test
    public void getApiParametersWithConversionTest() throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(new TestConversionQuery(Arrays.asList("1", "2", "3"), new MetapropertyField("1", Arrays.asList("1", "2"))));
        assertEquals(2, params.size());
        assertEquals("1,2,3", params.get("listField"));
        assertEquals("1,2", params.get("metapropertyField.1"));
    }

    /**
     * Query class only used for test purposes.
     */
    private class TestQuery {
        /**
         * Query field.
         */
        @ApiField(name = "field1")
        private String field1;
        /**
         * Query field.
         */
        @ApiField(name = "field2")
        private String field2;
        /**
         * Query field.
         */
        @SuppressWarnings("unused")
        private String field3;

        public TestQuery(final String field1, final String field2, final String field3) {
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
        @ApiField(name = "listField", conversionType = ConversionType.LIST_FIELD)
        private List<String> field1;
        /**
         * Query field.
         */
        @ApiField(name = "metapropertyField", conversionType = ConversionType.METAPROPERTY_FIELD)
        private MetapropertyField field2;

        public TestConversionQuery(final List<String> field1, final MetapropertyField field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }
}
