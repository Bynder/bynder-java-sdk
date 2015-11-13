package com.getbynder.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.getbynder.api.domain.ImageAsset;
import com.getbynder.api.domain.UserAccessData;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
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

    public static String getLoginURI(final String url, final String... params) throws UnsupportedEncodingException {

        StringBuilder loginUri = new StringBuilder(url);
        loginUri.append("?");

        for (int i=0; i<params.length; i+=2) {

            if (i > 0) {
                loginUri.append("&");
            }

            String value = params[i + 1];
            loginUri.append(OAuth.percentEncode(params[i]).concat("=").concat(OAuth.percentEncode(value)));
        }

        return loginUri.toString();
    }

    public static String getOAuthHeaderFromUrl(final String url) {

        StringBuilder oauthHeader = new StringBuilder("OAuth ");

        String[] params = url.split("&");

        for(int i=0; i<params.length; i++) {

            if(i == 0) {
                String temp = params[i].substring(params[i].indexOf("?")+1);

                oauthHeader.append(temp.substring(0, temp.indexOf("=")+1));
                oauthHeader.append("\"");
                oauthHeader.append(temp.substring(temp.indexOf("=")+1));
                oauthHeader.append("\",");
            } else {
                oauthHeader.append(params[i].substring(0, params[i].indexOf("=")+1));
                oauthHeader.append("\"");
                oauthHeader.append(params[i].substring(params[i].indexOf("=")+1));
                oauthHeader.append("\",");
            }
        }

        //delete the last comma
        oauthHeader.deleteCharAt(oauthHeader.length()-1);

        return oauthHeader.toString();
    }

    public static List<ImageAsset> createImageAssetListFromJSONArray(final JSONArray responseJsonArray) {

        List<ImageAsset> bynderImageAssets = new ArrayList<>();

        String id = "";
        String title = "";
        String description = "";
        String url = "";
        String thumbnailUrl = "";

        for(int i = 0; i < responseJsonArray.length(); i++){
            JSONObject responseJsonObj = responseJsonArray.getJSONObject(i);

            id = responseJsonObj.get("id").toString();
            title = responseJsonObj.get("name").toString();
            description = responseJsonObj.get("description").toString();

            responseJsonObj = new JSONObject(responseJsonObj.getJSONObject("thumbnails").toString());

            url = responseJsonObj.get("webimage").toString();
            thumbnailUrl = responseJsonObj.get("thul").toString();

            bynderImageAssets.add(new ImageAsset(id, title, description, url, thumbnailUrl));
        }

        return bynderImageAssets;
    }

    public static String createOAuthHeader(final String consumerKey, final String consumerSecret, final UserAccessData userAccessData, final String url) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

        // create a consumer object and configure it with the access token and token secret obtained from the service provider
        OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(userAccessData.getTokenKey(), userAccessData.getTokenSecret());

        // sign the request
        String inputUrl = consumer.sign(url);

        return getOAuthHeaderFromUrl(inputUrl);
    }
}
