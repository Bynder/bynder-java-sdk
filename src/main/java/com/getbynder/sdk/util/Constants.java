package com.getbynder.sdk.util;

/**
 *
 * @author daniel.sequeira
 */
public final class Constants {

    public static final String MEDIA_TYPE_IMAGE = "image";

    // secret.properties file base url and tokens
    public static final String ACCESS_TOKEN_KEY = SecretProperties.getInstance().getProperty("ACCESS_TOKEN_KEY");
    public static final String ACCESS_TOKEN_SECRET = SecretProperties.getInstance().getProperty("ACCESS_TOKEN_SECRET");
    public static final String BASE_URL = SecretProperties.getInstance().getProperty("BASE_URL");
    public static final String CONSUMER_KEY = SecretProperties.getInstance().getProperty("CONSUMER_KEY");
    public static final String CONSUMER_SECRET = SecretProperties.getInstance().getProperty("CONSUMER_SECRET");
    public static final String USERNAME = SecretProperties.getInstance().getProperty("USERNAME");
    public static final String PASSWORD = SecretProperties.getInstance().getProperty("PASSWORD");
    public static final String REQUEST_TOKEN_KEY = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_KEY");
    public static final String REQUEST_TOKEN_SECRET = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_SECRET");

    // skipped tests reasons
    public static final String TEST_SKIPPED_NO_ACCESS_TOKENS = "%s skipped: No access token key or/and access token secret defined";
    public static final String TEST_SKIPPED_NO_BASE_URL = "%s skipped: No base url defined";
    public static final String TEST_SKIPPED_NO_CONSUMER_TOKENS = "%s skipped: No consumer token key or/and consumer token secret defined";
    public static final String TEST_SKIPPED_NO_REQUEST_TOKENS = "%s skipped: No request token key or/and request token secret defined";
    public static final String TEST_SKIPPED_NO_USERNAME_PASSWORD = "%s skipped: No username or/and password defined";

    // failed tests reasons
    public static final String TEST_FAILED_EXCEPTION_NOT_THROWN = "%s failed: Exception was not thrown";
}
