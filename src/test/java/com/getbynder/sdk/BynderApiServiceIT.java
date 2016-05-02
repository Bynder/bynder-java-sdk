package com.getbynder.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getbynder.sdk.domain.Category;
import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.domain.Metaproperty;
import com.getbynder.sdk.domain.Tag;
import com.getbynder.sdk.domain.UserAccessData;
import com.getbynder.sdk.util.ConfigProperties;
import com.getbynder.sdk.util.ErrorMessages;
import com.getbynder.sdk.util.SecretProperties;
import com.getbynder.sdk.util.Utils;

/**
 *
 * @author daniel.sequeira
 */
public class BynderApiServiceIT {

    private static final Logger LOG = LoggerFactory.getLogger(BynderApiServiceIT.class);

    private final String USERNAME = SecretProperties.getInstance().getProperty("USERNAME");
    private final String PASSWORD = SecretProperties.getInstance().getProperty("PASSWORD");
    private final String REQUEST_TOKEN_KEY = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_KEY");
    private final String REQUEST_TOKEN_SECRET = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_SECRET");

    private final String MEDIA_TYPE_IMAGE = ConfigProperties.getInstance().getProperty("MEDIA_TYPE_IMAGE");

    private final String MEDIA_ASSET_KEYWORD_NOT_FOUND = "MEDIA_ASSET_KEYWORD_NOT_FOUND";

    private final String TEST_SKIPPED_NO_REQUEST_TOKENS = "%s skipped: No request token key or/and request token secret defined";
    private final String TEST_SKIPPED_NO_USERNAME_PASSWORD = "%s skipped: No username or/and password defined";
    private final String TEST_SKIPPED_NO_IMAGE_ASSETS = "%s skipped: No image assets uploaded for this environment";
    private final String TEST_SKIPPED_NO_METAPROPERTIES = "%s skipped: No metaproperties created for this environment";
    private final String TEST_SKIPPED_NO_CATEGORIES = "%s skipped: No categories created for this environment";
    private final String TEST_SKIPPED_NO_TAGS = "%s skipped: No tags created for this environment";
    private final String TEST_SKIPPED_NO_METAPROPERTIES_OPTIONS = "%s skipped: No metaproperties options found for this environment";
    private final String TEST_SKIPPED_NO_MEDIA_ASSETS = "%s skipped: No media assets uploaded for this environment";

    // regex to avoid media assets names and descriptions with special characters
    private final Pattern pattern = Pattern.compile("[a-z0-9 ]", Pattern.CASE_INSENSITIVE);

    private BynderApiService bynderApiService;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws Exception {
        bynderApiService = new BynderApiService();
    }

    @Test
    public void loginFailTest() throws Exception {

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_REQUEST_TOKENS, testName.getMethodName()), !StringUtils.isEmpty(REQUEST_TOKEN_KEY) && !StringUtils.isEmpty(REQUEST_TOKEN_SECRET));
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        try {
            bynderApiService.login("INVALID_USERNAME", "INVALID_PASSWORD");
        } catch (HttpResponseException e) {
            if (e.getStatusCode() == HttpStatus.SC_FORBIDDEN) {
                assertEquals(ErrorMessages.LOGIN_REQUEST_FAILED, e.getMessage());
            } else {
                throw e;
            }
        }
    }

    @Test
    public void loginTest() throws Exception {

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_USERNAME_PASSWORD, testName.getMethodName()), !StringUtils.isEmpty(USERNAME) && !StringUtils.isEmpty(PASSWORD));
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        UserAccessData userAccessData = bynderApiService.login(USERNAME, PASSWORD).body();

        assertNotNull(userAccessData);
        assertNotNull(userAccessData.getTokenKey());
        assertNotNull(userAccessData.getTokenSecret());
    }

    @Test
    public void getImageAssetsTest() throws Exception {

        List<MediaAsset> imageAssets = bynderApiService.getMediaAssets(MEDIA_TYPE_IMAGE, null, 5, 1, null).body();
        assertNotNull(imageAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS, testName.getMethodName()), imageAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        assertTrue(imageAssets.size() == 5);
        assertEquals(MEDIA_TYPE_IMAGE, imageAssets.get(0).getType());
    }

    @Test
    public void getAllMetaproperties() throws Exception {

        Map<String, Metaproperty> metaproperties = bynderApiService.getMetaproperties().body();
        assertNotNull(metaproperties);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES, testName.getMethodName()), metaproperties.entrySet().size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        for (Entry<String, Metaproperty> entry : metaproperties.entrySet()) {
            assertNotNull(entry.getValue().getId());
            break;
        }
    }

    @Test
    public void getCategoriesTest() throws Exception {

        List<Category> categories = bynderApiService.getCategories().body();
        assertNotNull(categories);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_CATEGORIES, testName.getMethodName()), categories.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        assertNotNull(categories.get(0).getId());
    }

    @Test
    public void getTagsTest() throws Exception {

        List<Tag> tags = bynderApiService.getTags().body();
        assertNotNull(tags);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_TAGS, testName.getMethodName()), tags.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        assertNotNull(tags.get(0).getId());
    }

    @Test
    public void getImageAssetsByKeywordTest() throws Exception {

        List<MediaAsset> imageAssets = bynderApiService.getMediaAssets(MEDIA_TYPE_IMAGE, null, 50, 1, null).body();
        assertNotNull(imageAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS, testName.getMethodName()), imageAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        String keyword = "";

        for (MediaAsset mediaAsset : imageAssets) {
            if (StringUtils.isNotEmpty(mediaAsset.getName()) && pattern.matcher(mediaAsset.getName()).find()) {
                keyword = mediaAsset.getName().split(Utils.STR_SPACE)[0];
                break;
            }
        }

        List<MediaAsset> imageAssetsByKeyword = bynderApiService.getMediaAssets(MEDIA_TYPE_IMAGE, keyword, null, null, null).body();

        assertNotNull(imageAssetsByKeyword);
        assertTrue(imageAssetsByKeyword.size() > 0);
        assertEquals(MEDIA_TYPE_IMAGE, imageAssetsByKeyword.get(0).getType());

        // keyword not found
        List<MediaAsset> emptyList = bynderApiService.getMediaAssets(MEDIA_TYPE_IMAGE, MEDIA_ASSET_KEYWORD_NOT_FOUND, null, null, null).body();

        assertNotNull(emptyList);
        assertTrue(emptyList.size() == 0);
    }

    @Test
    public void getImageAssetsByMetapropertyIdTest() throws Exception {

        Map<String, Metaproperty> metaproperties = bynderApiService.getMetaproperties().body();
        assertNotNull(metaproperties);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES, testName.getMethodName()), metaproperties.entrySet().size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        boolean metapropertyFound = false;
        String metapropertyId = null;

        for (Entry<String, Metaproperty> entry : metaproperties.entrySet()) {
            if (entry.getValue().getOptions().size() > 0) {
                metapropertyId = entry.getValue().getOptions().get(0).getId();
                metapropertyFound = true;
                break;
            }
        }

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES_OPTIONS, testName.getMethodName()), metapropertyFound && metapropertyId != null);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        List<MediaAsset> imageAssets = bynderApiService.getMediaAssets(MEDIA_TYPE_IMAGE, null, null, null, metapropertyId).body();

        assertNotNull(imageAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS, testName.getMethodName()), imageAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        assertTrue(imageAssets.get(0).getPropertyOptions().contains(metapropertyId));
    }

    @Test
    public void getMediaAssetByIdTest() throws Exception {

        List<MediaAsset> mediaAssets = bynderApiService.getMediaAssets(null, null, 50, 1, null).body();
        assertNotNull(mediaAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS, testName.getMethodName()), mediaAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        MediaAsset mediaAsset = bynderApiService.getMediaAssetById(mediaAssets.get(0).getId(), null).body();

        assertNotNull(mediaAsset);
        assertNotNull(mediaAsset.getId());
        assertEquals(mediaAssets.get(0).getId(), mediaAsset.getId());
        assertNull(mediaAsset.getMediaItems());

        mediaAsset = bynderApiService.getMediaAssetById(mediaAssets.get(0).getId(), Boolean.TRUE).body();

        assertNotNull(mediaAsset.getMediaItems());
    }
}
