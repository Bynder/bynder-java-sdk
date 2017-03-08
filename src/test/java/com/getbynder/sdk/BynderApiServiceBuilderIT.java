package com.getbynder.sdk;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.util.Constants;

/**
 *
 * @author daniel.sequeira
 */
public class BynderApiServiceBuilderIT {

    private static final Logger LOG = LoggerFactory.getLogger(BynderApiServiceBuilderIT.class);

    private BynderApiService bynderApiService = null;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws Exception {
        try {
            Assume.assumeTrue(String.format(Constants.TEST_SKIPPED_NO_BASE_URL, testName.getMethodName()), !StringUtils.isEmpty(Constants.BASE_URL));
            Assume.assumeTrue(String.format(Constants.TEST_SKIPPED_NO_CONSUMER_TOKENS, testName.getMethodName()),
                    !StringUtils.isEmpty(Constants.CONSUMER_KEY) && !StringUtils.isEmpty(Constants.CONSUMER_SECRET));
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }
    }

    @Test
    public void createApiServiceTest() throws Exception {
        try {
            Assume.assumeTrue(String.format(Constants.TEST_SKIPPED_NO_ACCESS_TOKENS, testName.getMethodName()),
                    !StringUtils.isEmpty(Constants.ACCESS_TOKEN_KEY) && !StringUtils.isEmpty(Constants.ACCESS_TOKEN_SECRET));
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        bynderApiService = new BynderApiServiceBuilder().setBaseUrl(Constants.BASE_URL).setConsumerKey(Constants.CONSUMER_KEY).setConsumerSecret(Constants.CONSUMER_SECRET)
                .setAccessTokenKey(Constants.ACCESS_TOKEN_KEY).setAccessTokenSecret(Constants.ACCESS_TOKEN_SECRET).create();
        assertNotNull(bynderApiService);

        List<MediaAsset> mediaAssets = bynderApiService.getMediaAssets(null, null, 1, 1, null);
        assertNotNull(mediaAssets);
    }

    @Test
    public void createApiServiceWithLoginSuccessTest() throws Exception {
        try {
            Assume.assumeTrue(String.format(Constants.TEST_SKIPPED_NO_REQUEST_TOKENS, testName.getMethodName()),
                    !StringUtils.isEmpty(Constants.REQUEST_TOKEN_KEY) && !StringUtils.isEmpty(Constants.REQUEST_TOKEN_SECRET));
            Assume.assumeTrue(String.format(Constants.TEST_SKIPPED_NO_USERNAME_PASSWORD, testName.getMethodName()),
                    !StringUtils.isEmpty(Constants.USERNAME) && !StringUtils.isEmpty(Constants.PASSWORD));
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        bynderApiService = new BynderApiServiceBuilder().setBaseUrl(Constants.BASE_URL).setConsumerKey(Constants.CONSUMER_KEY).setConsumerSecret(Constants.CONSUMER_SECRET)
                .setRequestTokenKey(Constants.REQUEST_TOKEN_KEY).setRequestTokenSecret(Constants.REQUEST_TOKEN_SECRET).createWithLogin(Constants.USERNAME, Constants.PASSWORD);
        assertNotNull(bynderApiService);

        List<MediaAsset> mediaAssets = bynderApiService.getMediaAssets(null, null, 1, 1, null);
        assertNotNull(mediaAssets);
    }

    @Test
    public void createApiServiceWithLoginFailTest() throws Exception {
        try {
            Assume.assumeTrue(String.format(Constants.TEST_SKIPPED_NO_REQUEST_TOKENS, testName.getMethodName()),
                    !StringUtils.isEmpty(Constants.REQUEST_TOKEN_KEY) && !StringUtils.isEmpty(Constants.REQUEST_TOKEN_SECRET));
            Assume.assumeTrue(String.format(Constants.TEST_SKIPPED_NO_USERNAME_PASSWORD, testName.getMethodName()),
                    !StringUtils.isEmpty(Constants.USERNAME) && !StringUtils.isEmpty(Constants.PASSWORD));
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        bynderApiService = new BynderApiServiceBuilder().setBaseUrl(Constants.BASE_URL).setConsumerKey(Constants.CONSUMER_KEY).setConsumerSecret(Constants.CONSUMER_SECRET)
                .setRequestTokenKey(Constants.REQUEST_TOKEN_KEY).setRequestTokenSecret(Constants.REQUEST_TOKEN_SECRET).createWithLogin(Constants.USERNAME, Constants.PASSWORD);
        assertNotNull(bynderApiService);

        boolean exceptionThrown = false;

        try {
            bynderApiService.login(Constants.REQUEST_TOKEN_KEY, Constants.REQUEST_TOKEN_SECRET, "INVALID_USERNAME", "INVALID_PASSWORD");
        } catch (HttpResponseException e) {
            assertTrue(e.getStatusCode() == HttpStatus.SC_FORBIDDEN);
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }
}
