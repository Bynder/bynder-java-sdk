package com.getbynder.api;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.getbynder.api.domain.ImageAsset;
import com.getbynder.api.domain.MediaAsset;
import com.getbynder.api.domain.Metaproperty;
import com.getbynder.api.domain.UserAccessData;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 *
 * @author daniel.sequeira
 */
public final class Utils {

    private Utils() {
        //prevent instantiation
    }

    public static URI createRequestURI(final URL url, final String relativePath) throws URISyntaxException {

        URIBuilder builder = new URIBuilder();

        builder.setScheme(url.getProtocol()).setHost(url.getHost()).setPath(url.getPath().concat(relativePath));

        return builder.build();
    }

    public static URI createRequestURI(final URL url, final String relativePath, final List<BasicNameValuePair> params) throws URISyntaxException {

        URIBuilder builder = new URIBuilder();

        builder.setScheme(url.getProtocol()).setHost(url.getHost()).setPath(url.getPath().concat(relativePath));

        for(BasicNameValuePair pair : params) {
            builder.setParameter(pair.getName(), pair.getValue());
        }

        return builder.build();
    }

    public static String getOAuthHeaderFromUrl(final URL url) {

        String query = url.getQuery();

        List<BasicNameValuePair> queryPairs = new ArrayList<>();

        String[] pairs = query.split("&");

        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            //the value needs to be decoded because the method OAuth.toHeaderElement will encode it again
            queryPairs.add(new BasicNameValuePair(pair.substring(0, idx), OAuth.percentDecode(pair.substring(idx + 1))));
        }

        StringBuilder oauthHeader = new StringBuilder("OAuth ");

        for(BasicNameValuePair nvPair : queryPairs) {
            oauthHeader.append(OAuth.toHeaderElement(nvPair.getName(), nvPair.getValue())).append(",");
        }

        oauthHeader.deleteCharAt(oauthHeader.length()-1);

        return oauthHeader.toString();
    }

    public static String createOAuthHeader(final String consumerKey, final String consumerSecret, final UserAccessData userAccessData, final String url) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

        // create a consumer object and configure it with the access token and token secret obtained from the service provider
        OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(userAccessData.getTokenKey(), userAccessData.getTokenSecret());

        // sign the request and return the encoded url
        String inputUrl = consumer.sign(url);

        return getOAuthHeaderFromUrl(new URL(inputUrl));
    }

    public static List<ImageAsset> createImageAssetListFromJSONArray(final JSONArray responseJsonArray) {

        List<ImageAsset> bynderImageAssets = new ArrayList<>();

        String id = "";
        String title = "";
        String description = "";
        String url = "";
        String thumbnailUrl = "";

        for(int i = 0; i < responseJsonArray.length(); i++) {
            JSONObject responseJsonObj = responseJsonArray.getJSONObject(i);

            id = responseJsonObj.getString("id");
            title = responseJsonObj.getString("name");
            description = responseJsonObj.has("description") ? responseJsonObj.getString("description") : "";

            responseJsonObj = new JSONObject(responseJsonObj.getJSONObject("thumbnails").toString());

            url = responseJsonObj.getString("webimage");
            thumbnailUrl = responseJsonObj.getString("thul");

            bynderImageAssets.add(new ImageAsset(id, title, description, url, thumbnailUrl));
        }

        return bynderImageAssets;
    }

    public static List<MediaAsset> createMediaAssetListFromJSONArray(final JSONArray responseJsonArray) {

        List<MediaAsset> bynderMediaAssets = new ArrayList<>();

        String id = "";
        String name = "";
        String description = "";
        String copyright = "";
        Boolean archive = Boolean.FALSE;
        String publicationDate = "";


        for(int i = 0; i < responseJsonArray.length(); i++) {
            JSONObject responseJsonObj = responseJsonArray.getJSONObject(i);

            id = responseJsonObj.getString("id");
            name = responseJsonObj.getString("name");
            description = responseJsonObj.has("description") ? responseJsonObj.getString("description") : "";
            copyright = responseJsonObj.has("copyright") ? responseJsonObj.getString("copyright") : "";
            archive = Boolean.valueOf(responseJsonObj.get("archive").toString());
            publicationDate = responseJsonObj.has("datePublished") ? responseJsonObj.getString("datePublished") : "";

            bynderMediaAssets.add(new MediaAsset(id, name, description, copyright, archive, publicationDate));
        }

        return bynderMediaAssets;
    }

    public static Metaproperty buildMetaproperty(final JSONObject jsonObject) {

        Metaproperty metaproperty = new Metaproperty();

        metaproperty.setId(jsonObject.getString("id"));
        metaproperty.setName(jsonObject.getString("name"));

        if(jsonObject.has("options")) {
            JSONArray jsonArray = jsonObject.getJSONArray("options");

            for(int i = 0; i < jsonArray.length(); i++) {
                metaproperty.addOption(buildMetaproperty(jsonArray.getJSONObject(i)));
            }
        } else {
            return metaproperty;
        }
        return metaproperty;
    }

    public static boolean isDateFormatValid(final String date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(date.trim());
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static HttpPost createPostRequest(final String consumerKey, final String consumerSecret, final UserAccessData userAccessData, final URI requestUri, final List<BasicNameValuePair> params) throws MalformedURLException, URISyntaxException, UnsupportedEncodingException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

        HttpPost request = new HttpPost(requestUri);

        // create a consumer object and configure it with the access token and token secret obtained from the service provider
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(userAccessData.getTokenKey(), userAccessData.getTokenSecret());

        // set the parameters into the request
        request.setEntity(new UrlEncodedFormEntity(params));

        // sign the request
        consumer.sign(request);

        return request;
    }

    public static List<Metaproperty> createMetapropertyListFromJSONObject(final JSONObject jsonObject) {

        String[] metapropertiesNames = JSONObject.getNames(jsonObject);

        List<Metaproperty> metaproperties = new ArrayList<>();

        for(int i=0; i < metapropertiesNames.length; i++) {
            metaproperties.add(Utils.buildMetaproperty(jsonObject.getJSONObject(metapropertiesNames[i])));
        }

        return metaproperties;
    }
}
