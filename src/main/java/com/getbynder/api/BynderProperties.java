package com.getbynder.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author daniel.sequeira
 */
public class BynderProperties {

    private final Properties configProperties = new Properties();

    private BynderProperties() {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("config.properties");

        try {
            configProperties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class SingletonHolder {
        private static final BynderProperties INSTANCE = new BynderProperties();
    }

    public static BynderProperties getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getProperty(final String key) {
        return configProperties.getProperty(key);
    }
}
