package com.getbynder.sdk.util;

/**
 *
 * @author daniel.sequeira
 */
public final class ErrorMessages {

    public static final String INVALID_PUBLICATION_DATETIME_FORMAT = "Invalid datetime format for inserted publication date, should be ISO8601-format.";
    public static final String MEDIA_ASSET_ID_NOT_FOUND = "Media Asset not found for the given ID.";
    public static final String LOGIN_REQUEST_FAILED = "Could not login to the API. The request was unsuccessful.";
    public static final String REQUEST_TOKEN_FAILED = "Could not obtain request token. The request was unsuccessful.";
    public static final String PROPERTY_NOT_DEFINED = "No %s property defined.";
    public static final String NULL_PARAMETER = "%s shall not be null.";
    public static final String INVALID_LIMIT_AND_OFFSET_PARAMETERS = "Limit and offset shall be greater than 0.";
}
