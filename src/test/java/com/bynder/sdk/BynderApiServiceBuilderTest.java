package com.bynder.sdk;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.bynder.sdk.BynderApiService;
import com.bynder.sdk.BynderApiServiceBuilder;
import com.bynder.sdk.util.Constants;

/**
 *
 * @author daniel.sequeira
 */
public class BynderApiServiceBuilderTest {

    private final String BASE_URL = "https://example.getbynder.com/api/";
    private final String INVALID_BASE_URL = "INVALID_BASE_URL";
    private final String CONSUMER_KEY = "CONSUMER_KEY";
    private final String CONSUMER_SECRET = "CONSUMER_SECRET";
    private final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY";
    private final String ACCESS_TOKEN_SECRET = "ACCESS_TOKEN_SECRET";
    private final String REQUEST_TOKEN_KEY = "REQUEST_TOKEN_KEY";
    private final String REQUEST_TOKEN_SECRET = "REQUEST_TOKEN_SECRET";
    private final String USERNAME = "USERNAME";
    private final String PASSWORD = "PASSWORD";

    private boolean exceptionThrown;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws Exception {
        exceptionThrown = false;
    }

    @Test
    public void createServiceBaseUrlFailTest() throws Exception {
        try {
            new BynderApiServiceBuilder().create();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("baseUrl"));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }

    @Test
    public void createServiceConsumerKeyFailTest() throws Exception {
        try {
            new BynderApiServiceBuilder().setBaseUrl(BASE_URL).create();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("consumerKey"));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }

    @Test
    public void createServiceConsumerSecretFailTest() throws Exception {
        try {
            new BynderApiServiceBuilder().setBaseUrl(BASE_URL).setConsumerKey(CONSUMER_KEY).create();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("consumerSecret"));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }

    @Test
    public void createServiceAccessTokenKeyFailTest() throws Exception {
        try {
            new BynderApiServiceBuilder().setBaseUrl(BASE_URL).setConsumerKey(CONSUMER_KEY).setConsumerSecret(CONSUMER_SECRET).create();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("accessTokenKey"));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }

    @Test
    public void createServiceAccessTokenSecretFailTest() throws Exception {
        try {
            new BynderApiServiceBuilder().setBaseUrl(BASE_URL).setConsumerKey(CONSUMER_KEY).setConsumerSecret(CONSUMER_SECRET).setAccessTokenKey(ACCESS_TOKEN_KEY).create();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("accessTokenSecret"));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void createServiceInvalidBaseUrlTest() throws Exception {
        new BynderApiServiceBuilder().setBaseUrl(INVALID_BASE_URL).setConsumerKey(CONSUMER_KEY).setConsumerSecret(CONSUMER_SECRET).setAccessTokenKey(ACCESS_TOKEN_KEY)
                .setAccessTokenSecret(ACCESS_TOKEN_SECRET).create();
    }

    @Test
    public void createServiceTest() {
        BynderApiService bynderApiService = null;

        try {
            bynderApiService = new BynderApiServiceBuilder().setBaseUrl(BASE_URL).setConsumerKey(CONSUMER_KEY).setConsumerSecret(CONSUMER_SECRET).setAccessTokenKey(ACCESS_TOKEN_KEY)
                    .setAccessTokenSecret(ACCESS_TOKEN_SECRET).create();
            assertNotNull(bynderApiService);
        } catch (Exception e) {
            Assert.fail("createServiceTest failed: Exception caught when trying to construct a BynderApiService instance");
        }
    }

    @Test
    public void createServiceRequestTokenKeyFailTest() throws Exception {
        try {
            new BynderApiServiceBuilder().setBaseUrl(BASE_URL).setConsumerKey(CONSUMER_KEY).setConsumerSecret(CONSUMER_SECRET).createWithLogin(USERNAME, PASSWORD);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("requestTokenKey"));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }

    @Test
    public void createServiceRequestTokenSecretFailTest() throws Exception {
        try {
            new BynderApiServiceBuilder().setBaseUrl(BASE_URL).setConsumerKey(CONSUMER_KEY).setConsumerSecret(CONSUMER_SECRET).setRequestTokenKey(REQUEST_TOKEN_KEY).createWithLogin(USERNAME,
                    PASSWORD);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("requestTokenSecret"));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }

    @Test
    public void createServiceUsernameFailTest() throws Exception {
        try {
            new BynderApiServiceBuilder().setBaseUrl(BASE_URL).setConsumerKey(CONSUMER_KEY).setConsumerSecret(CONSUMER_SECRET).setRequestTokenKey(REQUEST_TOKEN_KEY)
                    .setRequestTokenSecret(REQUEST_TOKEN_SECRET).createWithLogin(null, PASSWORD);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("username"));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }

    @Test
    public void createServicePasswordFailTest() throws Exception {
        try {
            new BynderApiServiceBuilder().setBaseUrl(BASE_URL).setConsumerKey(CONSUMER_KEY).setConsumerSecret(CONSUMER_SECRET).setRequestTokenKey(REQUEST_TOKEN_KEY)
                    .setRequestTokenSecret(REQUEST_TOKEN_SECRET).createWithLogin(USERNAME, null);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("password"));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }
}
