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
public class UserCreateQuery {

  /**
   * Email of the user.
   */
  @ApiField(name = "email")
  private final String email;
  /**
   * Password of the user.
   */
  @ApiField(name = "password")
  private final String password;
  /**
   * Security profile id for determining the user's rights. Can be retrieved by using the Retrieve
   * security profiles call.
   */
  @ApiField(name = "profileId")
  private final String profileId;
  /**
   * First name of the user.
   */
  @ApiField(name = "firstname")
  private final String firstName;
  /**
   * Last name of the user.
   */
  @ApiField(name = "lastname")
  private final String lastName;

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

  public UserCreateQuery(final String email, final String password, final String profileId, final String firstName, final String lastName) {
    this.email = email;
    this.password = password;
    this.profileId = profileId;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public UserCreateQuery setUserName(final String userName) {
    this.userName = userName;
    return this;
  }

  public UserCreateQuery setActive(final boolean active) {
    this.active = active;
    return this;
  }

  public UserCreateQuery setLanguage(final String language) {
    this.language = language;
    return this;
  }

  public UserCreateQuery setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public UserCreateQuery setCompanyName(final String companyName) {
    this.companyName = companyName;
    return this;
  }

  public UserCreateQuery setDepartment(final String department) {
    this.department = department;
    return this;
  }

  public UserCreateQuery setJob(final String job) {
    this.job = job;
    return this;
  }

  public UserCreateQuery setCostCenter(final String costCenter) {
    this.costCenter = costCenter;
    return this;
  }

}
