/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bynder.sdk.service.BynderClient;

/**
 * Application properties to instantiate {@link BynderClient} and run the {@link AppSample}.
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
}
