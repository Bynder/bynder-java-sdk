/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import java.util.List;

/**
 * Query to share a collection.
 */
public class CollectionShareQuery {

    /**
     * Id of the collection we want to share.
     */
    private final String collectionId;

    /**
     * Email addresses of the recipients.
     */
    @ApiField(name = "recipients", conversionType = ConversionType.LIST_FIELD)
    private final List<String> recipients;

    /**
     * Permission right of the recipients.
     */
    @ApiField(name = "collectionOptions")
    private final CollectionRecipientRight right;

    /**
     * Indicates if the recipients need to login to view the collection.
     */
    @ApiField(name = "loginRequired")
    private Boolean loginRequired;

    /**
     * Sharing start date.
     */
    @ApiField(name = "dateStart")
    private String dateStart;

    /**
     * Sharing end date.
     */
    @ApiField(name = "dateEnd")
    private String dateEnd;

    /**
     * Indicates if the recipients should be notified by email.
     */
    @ApiField(name = "sendMail")
    private Boolean sendMail;

    /**
     * Message added to the email if sendMail is set to true.
     */
    @ApiField(name = "message")
    private String message;

    public CollectionShareQuery(final String collectionId, final List<String> recipients,
        final CollectionRecipientRight right) {
        this.collectionId = collectionId;
        this.recipients = recipients;
        this.right = right;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public CollectionRecipientRight getRight() {
        return right;
    }

    public Boolean getLoginRequired() {
        return loginRequired;
    }

    public CollectionShareQuery setLoginRequired(final Boolean loginRequired) {
        this.loginRequired = loginRequired;
        return this;
    }

    public String getDateStart() {
        return dateStart;
    }

    public CollectionShareQuery setDateStart(final String dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public CollectionShareQuery setDateEnd(final String dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public Boolean getSendMail() {
        return sendMail;
    }

    public CollectionShareQuery setSendMail(final Boolean sendMail) {
        this.sendMail = sendMail;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CollectionShareQuery setMessage(final String message) {
        this.message = message;
        return this;
    }
}
