package com.bynder.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.bynder.sdk.util.Constants;
import com.bynder.sdk.util.Utils;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 *
 * @author daniel.sequeira
 */
public class UtilsTest {

    private final String SOME_NAME = "SOME_NAME";

    private boolean exceptionThrown;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws Exception {
        exceptionThrown = false;
    }

    @Test
    public void buildMapFromResponseTest() {
        String KEY_A = "KEY_A";
        String KEY_B = "KEY_B";
        String VALUE_A = "VALUE_A";
        String VALUE_B = "VALUE_B";

        StringBuilder stringBuilder = new StringBuilder(KEY_A).append(Utils.STR_EQUALS).append(VALUE_A).append(Utils.STR_AND).append(KEY_B).append(Utils.STR_EQUALS).append(VALUE_B);
        String response = stringBuilder.toString();

        Map<String, String> responseMap = Utils.buildMapFromResponse(response);
        assertNotNull(responseMap);
        assertEquals(VALUE_A, responseMap.get(KEY_A));
        assertEquals(VALUE_B, responseMap.get(KEY_B));
    }

    @Test
    public void checkNotNullSuccessTest() {
        Utils.checkNotNull(SOME_NAME, "SOME_VALUE");
    }

    @Test
    public void checkNotNullFailTest() {
        try {
            Utils.checkNotNull(SOME_NAME, null);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().startsWith(SOME_NAME));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }

        exceptionThrown = false;

        try {
            Utils.checkNotNull(SOME_NAME, "");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().startsWith(SOME_NAME));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }

    @Test
    public void validateResponseSuccessTest() throws HttpResponseException {
        Response<String> response = Response.success("");
        assertNotNull(response);
        assertEquals(HttpStatus.SC_OK, response.code());

        Utils.validateResponse(response, "");
    }

    @Test
    public void validateResponseFailTest() throws HttpResponseException {
        Response<String> response = Response.error(HttpStatus.SC_BAD_REQUEST, ResponseBody.create(MediaType.parse("text/plain"), ""));
        assertNotNull(response);
        assertNotEquals(HttpStatus.SC_OK, response.code());

        try {
            Utils.validateResponse(response, "ERROR_MESSAGE");
        } catch (HttpResponseException e) {
            assertEquals("ERROR_MESSAGE", e.getMessage());
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(Constants.TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }
}
