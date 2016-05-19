package com.getbynder.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;

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

    private final String MEDIA_TYPE_IMAGE = "image";
    private final String MEDIA_ASSET_KEYWORD_NOT_FOUND = "MEDIA_ASSET_KEYWORD_NOT_FOUND";
    private final String ID_NOT_FOUND = "ID_NOT_FOUND";
    private final String MEDIA_ASSET_DESCRIPTION = "Description changed by Integration Test of Bynder Java SDK";

    private final String INVALID_DATETIME = new Date().toString();
    private final String VALID_DATETIME_UTC = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
    private final String VALID_DATETIME_GMT = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")));
    private final String VALID_DATETIME_WET = DatatypeConverter.printDateTime(Calendar.getInstance(TimeZone.getTimeZone("WET")));

    private final int TIME_TO_SLEEP = 5000;

    private final String TEST_SKIPPED_NO_REQUEST_TOKENS = "%s skipped: No request token key or/and request token secret defined";
    private final String TEST_SKIPPED_NO_USERNAME_PASSWORD = "%s skipped: No username or/and password defined";
    private final String TEST_SKIPPED_NO_IMAGE_ASSETS = "%s skipped: No image assets uploaded for this environment";
    private final String TEST_SKIPPED_NO_METAPROPERTIES = "%s skipped: No metaproperties created for this environment";
    private final String TEST_SKIPPED_NO_CATEGORIES = "%s skipped: No categories created for this environment";
    private final String TEST_SKIPPED_NO_TAGS = "%s skipped: No tags created for this environment";
    private final String TEST_SKIPPED_NO_METAPROPERTIES_OPTIONS = "%s skipped: No metaproperties options found for this environment";
    private final String TEST_SKIPPED_NO_MEDIA_ASSETS = "%s skipped: No media assets uploaded for this environment";
    private final String TEST_SKIPPED_NO_IMAGE_ASSETS_WITH_DESCRIPTION = "%s skipped: No image asset with description found for this environment";
    private final String TEST_SKIPPED_NO_IMAGE_ASSETS_WITHOUT_METAPROPERTIES = "%s skipped: No image asset without metaproperties found for this environment";
    private final String TEST_SKIPPED_NO_ADD_METAPROPERTY_PERMISSION = "%s skipped: No permission to add metaproperty to media asset in this environment";
    private final String TEST_SKIPPED_NO_KEYWORD_DEFINED = "%s skipped: No keyword defined";

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
            assertTrue(e.getStatusCode() == HttpStatus.SC_FORBIDDEN);
        }
    }

    @Test
    public void loginTest() throws Exception {

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_REQUEST_TOKENS, testName.getMethodName()), !StringUtils.isEmpty(REQUEST_TOKEN_KEY) && !StringUtils.isEmpty(REQUEST_TOKEN_SECRET));
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_USERNAME_PASSWORD, testName.getMethodName()), !StringUtils.isEmpty(USERNAME) && !StringUtils.isEmpty(PASSWORD));
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        UserAccessData userAccessData = bynderApiService.login(USERNAME, PASSWORD);
        assertNotNull(userAccessData);
        assertNotNull(userAccessData.getTokenKey());
        assertNotNull(userAccessData.getTokenSecret());
    }

    @Test
    public void getRequestTokenTest() throws Exception {

        try {
            Map<String, String> requestToken = bynderApiService.getRequestToken();
            assertNotNull(requestToken);
            assertNotNull(requestToken.keySet());
            assertEquals(2, requestToken.keySet().size());
            assertTrue(requestToken.keySet().containsAll(Arrays.asList("oauth_token", "oauth_token_secret")));
        } catch (HttpResponseException e) {
            assertTrue(e.getStatusCode() == HttpStatus.SC_UNAUTHORIZED);
        }
    }

    @Test
    public void getMetapropertiesTest() throws Exception {

        Map<String, Metaproperty> metaproperties = bynderApiService.getMetaproperties();
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

        List<Category> categories = bynderApiService.getCategories();
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

        List<Tag> tags = bynderApiService.getTags();
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
    public void getMediaAssetsTest() throws Exception {

        List<MediaAsset> mediaAssets = bynderApiService.getMediaAssets(null, null, 1, 1, null);
        assertNotNull(mediaAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS, testName.getMethodName()), mediaAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        assertTrue(mediaAssets.size() == 1);
    }

    @Test
    public void getMediaAssetByIdFailTest() throws Exception {

        MediaAsset mediaAsset = bynderApiService.getMediaAssetById(ID_NOT_FOUND, null);
        assertNull(mediaAsset);
    }

    @Test
    public void getMediaAssetByIdTest() throws Exception {

        List<MediaAsset> mediaAssets = bynderApiService.getMediaAssets(null, null, 1, 1, null);
        assertNotNull(mediaAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS, testName.getMethodName()), mediaAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        String mediaAssetId = mediaAssets.get(0).getId();

        MediaAsset mediaAsset = bynderApiService.getMediaAssetById(mediaAssetId, null);
        assertNotNull(mediaAsset);
        assertNotNull(mediaAsset.getId());
        assertEquals(mediaAssetId, mediaAsset.getId());
        assertNull(mediaAsset.getMediaItems());

        mediaAsset = bynderApiService.getMediaAssetById(mediaAssetId, Boolean.TRUE);
        assertNotNull(mediaAsset.getMediaItems());
    }

    @Test
    public void getImageAssetsTest() throws Exception {

        List<MediaAsset> imageAssets = bynderApiService.getImageAssets(null, 1, 1);
        assertNotNull(imageAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS, testName.getMethodName()), imageAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        assertTrue(imageAssets.size() == 1);
        assertEquals(MEDIA_TYPE_IMAGE, imageAssets.get(0).getType());
    }

    @Test
    public void getImageAssetsPaginationTest() throws Exception {

        List<MediaAsset> imageAssetsPage1 = bynderApiService.getImageAssets(null, 1, 1);
        assertNotNull(imageAssetsPage1);
        List<MediaAsset> imageAssetsPage2 = bynderApiService.getImageAssets(null, 1, 2);
        assertNotNull(imageAssetsPage2);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS, testName.getMethodName()), imageAssetsPage1.size() > 0 && imageAssetsPage2.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        assertNotEquals(imageAssetsPage1.get(0).getId(), imageAssetsPage2.get(0).getId());
    }

    @Test
    public void getImageAssetsByMetapropertyIdTest() throws Exception {

        Map<String, Metaproperty> metaproperties = bynderApiService.getMetaproperties();
        assertNotNull(metaproperties);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES, testName.getMethodName()), metaproperties.entrySet().size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        String metapropertyId = null;

        for (Entry<String, Metaproperty> entry : metaproperties.entrySet()) {
            if (entry.getValue().getOptions().size() > 0) {
                metapropertyId = entry.getValue().getOptions().get(0).getId();
                break;
            }
        }

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES_OPTIONS, testName.getMethodName()), metapropertyId != null);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        List<MediaAsset> imageAssets = bynderApiService.getImageAssetsByMetapropertyId(metapropertyId);
        assertNotNull(imageAssets);
        assertTrue(imageAssets.size() > 0);
        assertTrue(imageAssets.get(0).getPropertyOptions().contains(metapropertyId));
    }

    @Test
    public void getImageAssetsTotalByMetapropertyIdsTest() throws IOException {

        Map<String, Metaproperty> metaproperties = bynderApiService.getMetaproperties();
        assertNotNull(metaproperties);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES, testName.getMethodName()), metaproperties.entrySet().size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        String metapropertyId = null;

        for (Entry<String, Metaproperty> entry : metaproperties.entrySet()) {
            if (entry.getValue().getOptions().size() > 0) {
                metapropertyId = entry.getValue().getOptions().get(0).getId();
                break;
            }
        }

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES_OPTIONS, testName.getMethodName()), metapropertyId != null);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        int total = bynderApiService.getImageAssetsTotalByMetapropertyIds(Arrays.asList(metapropertyId));
        assertTrue(total > 0);
    }

    @Test
    public void getImageAssetsTotalTest() throws Exception {

        List<MediaAsset> imageAssets = bynderApiService.getImageAssets(null, 1, 1);
        assertNotNull(imageAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS, testName.getMethodName()), imageAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        int imageAssetsTotal = bynderApiService.getImageAssetsTotal();
        assertTrue(imageAssetsTotal > 0);
    }

    @Test
    public void getImageAssetsByKeywordTest() throws Exception {

        List<MediaAsset> imageAssets = bynderApiService.getImageAssets(null, 50, 1);
        assertNotNull(imageAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS, testName.getMethodName()), imageAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        String keyword = null;

        for (MediaAsset mediaAsset : imageAssets) {
            if (StringUtils.isNotEmpty(mediaAsset.getName()) && pattern.matcher(mediaAsset.getName()).find()) {
                keyword = mediaAsset.getName().split(Utils.STR_SPACE)[0];
                break;
            }
        }

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_KEYWORD_DEFINED, testName.getMethodName()), keyword != null);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        List<MediaAsset> imageAssetsByKeyword = bynderApiService.getImageAssets(keyword, null, null);
        assertNotNull(imageAssetsByKeyword);
        assertTrue(imageAssetsByKeyword.size() > 0);
        assertEquals(MEDIA_TYPE_IMAGE, imageAssetsByKeyword.get(0).getType());

        // keyword not found
        List<MediaAsset> emptyList = bynderApiService.getImageAssets(MEDIA_ASSET_KEYWORD_NOT_FOUND, null, null);
        assertNotNull(emptyList);
        assertTrue(emptyList.size() == 0);
    }

    @Test
    public void setMediaAssetIdNotFoundTest() throws Exception {

        int statusCode = bynderApiService.setMediaAssetProperties(ID_NOT_FOUND, null, null, null, null, null);
        assertEquals(HttpStatus.SC_NOT_FOUND, statusCode);
    }

    @Test
    public void setImageAssetDescriptionTest() throws Exception {

        List<MediaAsset> imageAssets = bynderApiService.getImageAssets(null, 100, 1);
        assertNotNull(imageAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS, testName.getMethodName()), imageAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        String imageAssetId = null;
        String imageAssetDescription = null;

        for (MediaAsset imageAsset : imageAssets) {
            if (StringUtils.isNotEmpty(imageAsset.getDescription()) && pattern.matcher(imageAsset.getDescription()).find()) {
                imageAssetId = imageAsset.getId();
                imageAssetDescription = imageAsset.getDescription();
                break;
            }
        }

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS_WITH_DESCRIPTION, testName.getMethodName()), imageAssetId != null && imageAssetDescription != null);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        int statusCode = bynderApiService.setMediaAssetProperties(imageAssetId, null, MEDIA_ASSET_DESCRIPTION, null, null, null);
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);

        // give it some time for the media asset to be updated
        Thread.sleep(TIME_TO_SLEEP);

        MediaAsset mediaAsset = bynderApiService.getMediaAssetById(imageAssetId, null);
        assertEquals(MEDIA_ASSET_DESCRIPTION, mediaAsset.getDescription());

        statusCode = bynderApiService.setMediaAssetProperties(imageAssetId, null, imageAssetDescription, null, null, null);
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);

        // give it some time for the media asset to be updated
        Thread.sleep(TIME_TO_SLEEP);

        mediaAsset = bynderApiService.getMediaAssetById(imageAssetId, null);
        assertEquals(imageAssetDescription, mediaAsset.getDescription());
    }

    @Test
    public void setMediaAssetInvalidDateTest() throws Exception {

        List<MediaAsset> mediaAssets = bynderApiService.getMediaAssets(null, null, 1, 1, null);
        assertNotNull(mediaAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS, testName.getMethodName()), mediaAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        int statusCode = bynderApiService.setMediaAssetProperties(mediaAssets.get(0).getId(), null, null, null, null, INVALID_DATETIME);
        assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }
    @Test
    public void setMediaAssetPublicationDateTest() throws Exception {

        List<MediaAsset> mediaAssets = bynderApiService.getMediaAssets(null, null, 1, 1, null);
        assertNotNull(mediaAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_MEDIA_ASSETS, testName.getMethodName()), mediaAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        String mediaAssetid = mediaAssets.get(0).getId();

        int statusCode = bynderApiService.setMediaAssetProperties(mediaAssetid, null, null, null, null, VALID_DATETIME_UTC);
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);

        statusCode = bynderApiService.setMediaAssetProperties(mediaAssetid, null, null, null, null, VALID_DATETIME_WET);
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);

        statusCode = bynderApiService.setMediaAssetProperties(mediaAssetid, null, null, null, null, VALID_DATETIME_GMT);
        assertEquals(HttpStatus.SC_ACCEPTED, statusCode);
    }

    @Test
    public void addMetapropertyToMediaAssetTest() throws Exception {

        List<MediaAsset> imageAssets = bynderApiService.getImageAssets(null, 100, 1);
        assertNotNull(imageAssets);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS, testName.getMethodName()), imageAssets.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        MediaAsset testImageAsset = null;

        for (MediaAsset imageAsset : imageAssets) {
            if (imageAsset.getPropertyOptions() == null || imageAsset.getPropertyOptions().isEmpty()) {
                testImageAsset = imageAsset;
                break;
            }
        }

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_IMAGE_ASSETS_WITHOUT_METAPROPERTIES, testName.getMethodName()), testImageAsset != null);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        Map<String, Metaproperty> metaproperties = bynderApiService.getMetaproperties();
        assertNotNull(metaproperties);

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES, testName.getMethodName()), metaproperties.size() > 0);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        int statusCode = 0;
        String metapropertyId = null;
        String optionId = null;

        for (Entry<String, Metaproperty> entry : metaproperties.entrySet()) {
            if (entry.getValue().getOptions().size() > 0) {
                statusCode = bynderApiService.addMetapropertyToAsset(testImageAsset.getId(), entry.getValue().getId(), entry.getValue().getOptions().get(0).getId());
                metapropertyId = entry.getValue().getId();
                optionId = entry.getValue().getOptions().get(0).getId();
                break;
            }
        }

        try {
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_METAPROPERTIES_OPTIONS, testName.getMethodName()), optionId != null);
            Assume.assumeTrue(String.format(TEST_SKIPPED_NO_ADD_METAPROPERTY_PERMISSION, testName.getMethodName()), statusCode == HttpStatus.SC_ACCEPTED);
        } catch (AssumptionViolatedException e) {
            LOG.warn(e.getMessage());
            throw e;
        }

        // give it some time for the metaproperty to be added to the asset
        Thread.sleep(TIME_TO_SLEEP);

        testImageAsset = bynderApiService.getMediaAssetById(testImageAsset.getId(), null);
        assertNotNull(testImageAsset.getPropertyOptions());
        assertTrue(testImageAsset.getPropertyOptions().contains(optionId));

        // remove the metaproperty from the asset
        bynderApiService.addMetapropertyToAsset(testImageAsset.getId(), metapropertyId, "");

        // give it some time for the metaproperty to be removed from the asset
        Thread.sleep(TIME_TO_SLEEP);

        testImageAsset = bynderApiService.getMediaAssetById(testImageAsset.getId(), null);
        assertNull(testImageAsset.getPropertyOptions());
    }
}
