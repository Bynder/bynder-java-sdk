package com.bynder.sdk.configuration;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URL;
import org.junit.Test;

public class ConfigurationTest {

    public static final String EXPECTED_BASE_URL = "https://baseurl.bynder.com";
    public static final String EXPECTED_CLIENT_ID = "clientId";
    public static final String EXPECTED_CLIENT_SECRET = "clientSecret";
    public static final String EXPECTED_REDIRECT_URI = "https://redirecturi.bynder.com";

    @Test
    public void buildConfiguration() throws Exception {
        Configuration configuration = new Configuration.Builder(new URL(EXPECTED_BASE_URL),
            EXPECTED_CLIENT_ID, EXPECTED_CLIENT_SECRET, new URI(EXPECTED_REDIRECT_URI)).build();

        assertEquals(new URL(EXPECTED_BASE_URL), configuration.getBaseUrl());
        assertEquals(EXPECTED_CLIENT_ID, configuration.getClientId());
        assertEquals(EXPECTED_CLIENT_SECRET, configuration.getClientSecret());
        assertEquals(new URI(EXPECTED_REDIRECT_URI), configuration.getRedirectUri());
    }
}
