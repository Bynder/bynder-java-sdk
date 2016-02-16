package com.getbynder.sdk.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author daniel.sequeira
 */
public class ConfigProperties {

    private final Properties configProperties = new Properties();

    private ConfigProperties() {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("config.properties");

        try {
            configProperties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class SingletonHolder {
        private static final ConfigProperties INSTANCE = new ConfigProperties();
    }

    public static ConfigProperties getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getProperty(final String key) {
        return configProperties.getProperty(key);
    }
}
