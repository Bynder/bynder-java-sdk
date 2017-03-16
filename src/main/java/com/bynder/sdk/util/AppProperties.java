/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bynder.sdk.model.Settings;
import com.bynder.sdk.service.BynderService;

/**
 * Application properties to instantiate {@link BynderService} and run the integration tests.
 */
public class AppProperties {

    private static final Logger LOG = LoggerFactory.getLogger(AppProperties.class);

    private final Properties appProperties = new Properties();

    /**
     * Initializes a new instance of the class by loading the content in the app.properties file.
     */
    private AppProperties() {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("app.properties");

        try {
            appProperties.load(input);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    private static class SingletonHolder {
        private static final AppProperties INSTANCE = new AppProperties();
    }

    public static AppProperties getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getProperty(final String key) {
        return appProperties.getProperty(key);
    }

    /**
     * Gets a {@link Settings} instance using the properties define in the app.properties file.
     *
     * @return {@link Settings} instance.
     */
    public static Settings getSettings() {
        return new Settings(getInstance().getProperty("BASE_URL"), getInstance().getProperty("CONSUMER_KEY"), getInstance().getProperty("CONSUMER_SECRET"),
                getInstance().getProperty("ACCESS_TOKEN_KEY"), getInstance().getProperty("ACCESS_TOKEN_SECRET"));
    }
}
