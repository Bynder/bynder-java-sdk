/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to filter user results.
 */
public class UserQuery {

  /**
   * This property has to be set to 1 (TRUE) for the API to retrieved only users marked as active.
   */
  @ApiField(name = "includeInActive", conversionType = ConversionType.BOOLEAN_FIELD)
  private Boolean includeInActive;

  public UserQuery setIncludeInActive(final boolean includeInActive) {
    this.includeInActive = includeInActive;
    return this;
  }
}
