/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bynder.sdk.model.Settings;
import com.bynder.sdk.service.BynderService;

/**
 * Application properties to instantiate {@link BynderService} and run the integration tests.
 */
public final class AppProperties {

    private static final Logger LOG = LoggerFactory.getLogger(AppProperties.class);

    private final Properties appProperties = new Properties();

    /**
     * Initialises a new instance of the class by loading the content in the
     * src/main/resources/app.properties file.
     */
    public AppProperties() {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("app.properties");

        try {
            appProperties.load(input);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public String getProperty(final String key) {
        return appProperties.getProperty(key);
    }

    /**
     * Gets a {@link Settings} instance using the properties define in the
     * src/main/resources/app.properties file.
     *
     * @return {@link Settings} instance.
     *
     * @throws MalformedURLException
     */
    public Settings getSettings() throws MalformedURLException {
        return new Settings(new URL(getProperty("BASE_URL")), getProperty("CONSUMER_KEY"), getProperty("CONSUMER_SECRET"), getProperty("ACCESS_TOKEN_KEY"), getProperty("ACCESS_TOKEN_SECRET"));
    }

    /**
     * Gets a {@link Settings} instance for login using the properties define in the
     * src/main/resources/app.properties file.
     *
     * @return {@link Settings} instance.
     *
     * @throws MalformedURLException
     */
    public Settings getSettingsForLogin() throws MalformedURLException {
        return new Settings(new URL(getProperty("BASE_URL")), getProperty("CONSUMER_KEY"), getProperty("CONSUMER_SECRET"), getProperty("REQUEST_TOKEN_KEY"), getProperty("REQUEST_TOKEN_SECRET"));
    }
}
