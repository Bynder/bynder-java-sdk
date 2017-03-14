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

import com.bynder.sdk.model.Settings;

public class AppProperties {

    private final Properties appProperties = new Properties();

    private AppProperties() {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("app.properties");

        try {
            appProperties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
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

    public static Settings getSettings() {
        return new Settings(getInstance().getProperty("BASE_URL"), getInstance().getProperty("CONSUMER_KEY"), getInstance().getProperty("CONSUMER_SECRET"),
                getInstance().getProperty("ACCESS_TOKEN_KEY"), getInstance().getProperty("ACCESS_TOKEN_SECRET"));
    }
}
