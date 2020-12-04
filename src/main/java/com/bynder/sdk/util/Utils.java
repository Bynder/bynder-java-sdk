/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Utils class that provides methods to help handling API requests and responses.
 */
public final class Utils {

    /**
     * Prevents the instantiation of the class.
     */
    private Utils() {
    }

    /**
     * Encodes a string into application/x-www-form-urlencoded format using a UTF-8 as encoding
     * scheme.
     *
     * @param value String to be encoded.
     * @return The encoded string value.
     * @throws UnsupportedEncodingException If the encoding scheme for the parameter is not
     * supported.
     */
    public static String encodeParameterValue(final String value)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
    }

    /**
     * Checks if a date is expiring in a specified number of seconds.
     *
     * @param date String representing the date.
     * @param seconds Number of seconds desired for the check.
     * @return True if the date will expire in the number of seconds passed as parameter.
     */
    public static boolean isDateExpiring(final Date date, final int seconds) {
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.setTime(date);
        expirationDate.add(Calendar.SECOND, -seconds);
        return expirationDate.before(Calendar.getInstance());
    }

    /**
     * Loads a (.properties) config file from the resources.
     *
     * @param file the filename of the config file (without the extension)
     * @return {@link Properties} with the values from the config file
     * @throws IOException if the config file could not be loaded
     */
    public static Properties loadConfig(final String file)
            throws IOException {
        String filename = file + ".properties";
        Properties config = new Properties();

        // Try to load the resource, using some fallbacks if it fails.
        InputStream is = ClassLoader.getSystemResourceAsStream(filename);
        if (is == null) {
            is = Utils.class.getResourceAsStream(filename);
        }
        if (is == null) {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        }

        if (is == null) {
            throw new IOException("Resource could not be loaded: " + filename);
        }

        config.load(is);
        return config;
    }

}
