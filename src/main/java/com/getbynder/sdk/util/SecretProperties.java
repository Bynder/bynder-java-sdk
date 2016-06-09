package com.getbynder.sdk.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author daniel.sequeira
 */
public class SecretProperties {

    private final Properties secretProperties = new Properties();

    private SecretProperties() {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("secret.properties");

        try {
            secretProperties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class SingletonHolder {
        private static final SecretProperties INSTANCE = new SecretProperties();
    }

    public static SecretProperties getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getProperty(final String key) {
        return secretProperties.getProperty(key);
    }
}
