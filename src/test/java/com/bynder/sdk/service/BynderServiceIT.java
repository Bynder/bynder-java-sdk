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
import java.net.URL;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bynder.sdk.model.Settings;
import com.bynder.sdk.service.impl.BynderServiceImpl;
import com.bynder.sdk.util.AppProperties;
import com.bynder.sdk.util.Utils;

import io.reactivex.Observable;

/**
 * Class to test {@link BynderService} implementation against the API.
 */
public class BynderServiceIT {

    private static final Logger LOG = LoggerFactory.getLogger(BynderServiceIT.class);

    private final String BASE_URL = "BASE_URL";
    private final String OAUTH_TOKEN = "oauth_token";
    private final String CALLBACK = "callback";
    private final String CALLBACK_URL = "http://localhost:8080/";

    private BynderService bynderService;
    private final AppProperties appProperties = new AppProperties();

    @Rule
    public TestName testName = new TestName();

    /**
     * Before each test an instance of {@link BynderService} is created using the settings defined
     * in the src/main/resources/app.properties file.
     */
    @Before
    public void setUp() throws Exception {
        bynderService = BynderServiceImpl
                .create(new Settings(new URL(appProperties.getProperty(BASE_URL)), appProperties.getProperty("CONSUMER_KEY"), appProperties.getProperty("CONSUMER_SECRET"), null, null));
    }

    /**
     * Tests that when {@link BynderService#getRequestToken()} is called the request token pair is
     * returned and the authorize URL is build correctly by
     * {@link BynderService#getAuthoriseUrl(String)}.
     *
     * @throws InterruptedException
     */
    @Test
    public void getRequestTokenAndAuthoriseUrlTest() {
        Observable<String> observable = bynderService.getRequestToken();
        observable.doOnNext(response -> {
            Map<String, String> requestToken = Utils.buildMapFromResponse(response);

            assertNotNull(requestToken);
            assertEquals(2, requestToken.size());

            String authoriseUrl = bynderService.getAuthoriseUrl(null);
            assertNotNull(authoriseUrl);
            assertTrue(authoriseUrl.length() > 0);
            assertTrue(authoriseUrl.contains(appProperties.getProperty(BASE_URL)));

            String query = new URI(authoriseUrl).getQuery();
            assertNotNull(query);

            Map<String, String> params = Utils.buildMapFromResponse(query);
            assertEquals(1, params.size());
            assertNotNull(params.get(OAUTH_TOKEN));

            authoriseUrl = bynderService.getAuthoriseUrl(CALLBACK_URL);
            assertNotNull(authoriseUrl);
            assertTrue(authoriseUrl.length() > 0);
            assertTrue(authoriseUrl.contains(appProperties.getProperty(BASE_URL)));

            query = new URI(authoriseUrl).getQuery();
            assertNotNull(query);

            params = Utils.buildMapFromResponse(query);
            assertEquals(2, params.size());
            assertNotNull(params.get(OAUTH_TOKEN));
            assertNotNull(params.get(CALLBACK));
            assertEquals(CALLBACK_URL, params.get(CALLBACK));
        }).doOnComplete(() -> LOG.info(String.format("%s: SUCCESS", testName.getMethodName()))).doOnError(throwable -> Assert.fail(throwable.getMessage())).subscribe();
    }
}
