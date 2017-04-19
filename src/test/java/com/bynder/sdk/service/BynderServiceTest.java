/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.bynder.sdk.model.Settings;
import com.bynder.sdk.service.impl.BynderServiceImpl;

/**
 * Class to test {@link BynderService} login methods.
 */
public class BynderServiceTest {

    private Settings settings;

    @Before
    public void setUp() throws Exception {
        settings = new Settings(new URL("https://example.getbynder.com"), "consumerKey", "consumerSecret", "tokenKey", "tokenSecret");
    }

    /**
     * Tests that URL returned by {@link BynderService#getAuthoriseUrl(String)} is correct.
     */
    @Test
    public void getAuthoriseUrlWithoutCallbackTest() throws MalformedURLException {
        BynderService bynderService = BynderServiceImpl.create(settings);
        String authoriseUrl = bynderService.getAuthoriseUrl(null);
        assertEquals(String.format("%s/api/v4/oauth/authorise/?oauth_token=%s", settings.getBaseUrl().toString(), settings.getToken()), authoriseUrl);
    }

    /**
     * Tests that URL returned by {@link BynderService#getAuthoriseUrl(String)} when callback URL is
     * specified is correct.
     */
    @Test
    public void getAuthoriseUrlWithCallbackTest() throws MalformedURLException {
        BynderService bynderService = BynderServiceImpl.create(settings);
        String callbackUrl = "http://localhost/";
        String authoriseUrl = bynderService.getAuthoriseUrl(callbackUrl);
        assertEquals(String.format("%s/api/v4/oauth/authorise/?oauth_token=%s&callback=%s", settings.getBaseUrl().toString(), settings.getToken(), callbackUrl), authoriseUrl);
    }
}
