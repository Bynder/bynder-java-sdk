/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.bynder.sdk.model.Settings;
import com.bynder.sdk.service.impl.BynderServiceImpl;
import com.bynder.sdk.util.AppProperties;
import com.bynder.sdk.util.Utils;

public class BynderServiceIT {

    private final String BASE_URL = "BASE_URL";
    private final String OAUTH_TOKEN = "oauth_token";
    private final String CALLBACK = "callback";
    private final String CALLBACK_URL = "http://localhost:8080/";

    private BynderService bynderService;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws Exception {
        bynderService = BynderServiceImpl.create(new Settings(AppProperties.getInstance().getProperty(BASE_URL), AppProperties.getInstance().getProperty("CONSUMER_KEY"),
                AppProperties.getInstance().getProperty("CONSUMER_SECRET"), null, null));
    }

    @Test
    public void getRequestTokenAndAuthoriseUrlTest() throws URISyntaxException {
        bynderService.getRequestToken();

        String authoriseUrl = bynderService.getAuthoriseUrl(null);
        assertNotNull(authoriseUrl);
        assertTrue(authoriseUrl.length() > 0);
        assertTrue(authoriseUrl.contains(AppProperties.getInstance().getProperty(BASE_URL)));

        String query = new URI(authoriseUrl).getQuery();
        assertNotNull(query);

        Map<String, String> params = Utils.buildMapFromResponse(query);
        assertTrue(params.keySet().size() == 1);
        assertNotNull(params.get(OAUTH_TOKEN));

        authoriseUrl = bynderService.getAuthoriseUrl(CALLBACK_URL);
        assertNotNull(authoriseUrl);
        assertTrue(authoriseUrl.length() > 0);
        assertTrue(authoriseUrl.contains(AppProperties.getInstance().getProperty(BASE_URL)));

        query = new URI(authoriseUrl).getQuery();
        assertNotNull(query);

        params = Utils.buildMapFromResponse(query);
        assertTrue(params.keySet().size() == 2);
        assertNotNull(params.get(OAUTH_TOKEN));
        assertNotNull(params.get(CALLBACK));
        assertEquals(CALLBACK_URL, params.get(CALLBACK));
    }
}
