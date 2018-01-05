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
package com.bynder.sdk.service;

import static org.junit.Assert.assertEquals;

import com.bynder.sdk.model.Settings;
import com.bynder.sdk.service.impl.BynderServiceImpl;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test {@link BynderService} login methods.
 */
public class BynderServiceTest {

    private Settings settings;
    private BynderService bynderService;

    @Before
    public void setUp() throws Exception {
        settings = new Settings(new URL("https://example.bynder.com"), "consumerKey",
            "consumerSecret", "tokenKey", "tokenSecret");
        bynderService = BynderServiceImpl.create(settings);
    }

    /**
     * Tests that URL returned by {@link BynderService#getAuthoriseUrl(String)} is correct.
     */
    @Test
    public void getAuthoriseUrlWithoutCallbackTest() throws Exception {
        URL authoriseUrl = bynderService.getAuthoriseUrl(null);
        String path = "/api/v4/oauth/authorise/?oauth_token=".concat(settings.getToken());
        assertEquals(new URL(settings.getBaseUrl(), path), authoriseUrl);
    }

    /**
     * Tests that URL returned by {@link BynderService#getAuthoriseUrl(String)} when callback URL is
     * specified is correct.
     */
    @Test
    public void getAuthoriseUrlWithCallbackTest() throws Exception {
        String callbackUrl = "http://localhost/";
        URL authoriseUrl = bynderService.getAuthoriseUrl(callbackUrl);
        String path = "/api/v4/oauth/authorise/?oauth_token=".concat(settings.getToken())
            .concat("&callback=").concat(callbackUrl);
        assertEquals(new URL(settings.getBaseUrl(), path), authoriseUrl);
    }
}
