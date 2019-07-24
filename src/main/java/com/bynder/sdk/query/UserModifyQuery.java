/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to create user.
 */
public class UserModifyQuery {

  /**
   * Email of the user.
   */
  @ApiField(name = "email")
  private String email;
  /**
   * Password of the user.
   */
  @ApiField(name = "password")
  private String password;
  /**
   * Security profile id for determining the user's rights. Can be retrieved by using the Retrieve
   * security profiles call.
   */
  @ApiField(name = "profileId")
  private String profileId;
  /**
   * First name of the user.
   */
  @ApiField(name = "firstname")
  private String firstName;
  /**
   * Last name of the user.
   */
  @ApiField(name = "lastname")
  private String lastName;

  /**
   * Username for login. If not defined it will take your email as username.
   */
  @ApiField(name = "username")
  private String userName;
  /**
   * Whether or not the user will be active, inactive users won't be able to login.
   */
  @ApiField(name = "active")
  private boolean active = true;
  /**
   * Prefered website language, options can be retrieved using the Retrieve account call.
   */
  @ApiField(name = "language")
  private String language;

  @ApiField(name = "infix")
  private String infix;

  @ApiField(name = "phoneNumber")
  private String phoneNumber;

  @ApiField(name = "companyName")
  private String companyName;

  @ApiField(name = "department")
  private String department;

  @ApiField(name = "job")
  private String job;

  @ApiField(name = "costCenter")
  private String costCenter;

  public UserModifyQuery setEmail(final String email) {
    this.email = email;
    return this;
  }

  public UserModifyQuery setPassword(final String password) {
    this.password = password;
    return this;
  }

  public UserModifyQuery setProfileId(final String profileId) {
    this.profileId = profileId;
    return this;
  }

  public UserModifyQuery setFirstName(final String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserModifyQuery setLastName(final String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserModifyQuery setUserName(final String userName) {
    this.userName = userName;
    return this;
  }

  public UserModifyQuery setActive(final boolean active) {
    this.active = active;
    return this;
  }

  public UserModifyQuery setLanguage(final String language) {
    this.language = language;
    return this;
  }

  public UserModifyQuery setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public UserModifyQuery setCompanyName(final String companyName) {
    this.companyName = companyName;
    return this;
  }

  public UserModifyQuery setDepartment(final String department) {
    this.department = department;
    return this;
  }

  public UserModifyQuery setJob(final String job) {
    this.job = job;
    return this;
  }

  public UserModifyQuery setCostCenter(final String costCenter) {
    this.costCenter = costCenter;
    return this;
  }

}
