/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * This represents a Bynder User
 */
public class User {

    /**
     * Id of the user logged in.
     */
    private String userId;
    /**
     * Token key returned by API.
     */
    private String tokenKey;
    /**
     * Token secret returned by API.
     */
    private String tokenSecret;
    /**
     * True if access was given to the username/password pair.
     */
    @SerializedName(value = "access")
    private Boolean hasAccess;

    private String id;
    
    @SerializedName(value = "username")
    private String userName;

    private String name;

    private String email;

    private String email2;

    @SerializedName(value = "active")
    private Boolean isActive;

    private String profileId;

    private String lastLogin;

    private String firstName;

    private String lastName;

    private String infix;

    public String getUserId() {
        return userId;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public Boolean hasAccess() {
        return hasAccess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }
}
