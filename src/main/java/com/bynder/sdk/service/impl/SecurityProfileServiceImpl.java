/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import java.util.List;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.SecurityProfile;
import com.bynder.sdk.service.SecurityProfileService;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Implementation of {@link SecurityProfileService}.
 */
public class SecurityProfileServiceImpl implements SecurityProfileService {

  /**
   * Instance of {@link BynderApi} which handles the HTTP communication with the Bynder API.
   */
  private final BynderApi bynderApi;

  /**
   * Initialises a new instance of the class.
   *
   * @param bynderApi Instance to handle the HTTP communication with the Bynder API.
   */
  public SecurityProfileServiceImpl(final BynderApi bynderApi) {
    this.bynderApi = bynderApi;
  }

  /**
   * Check {@link SecurityProfileService} for more information.
   */
  @Override
  public Observable<Response<List<SecurityProfile>>> getSecurityProfiles() throws IllegalAccessException {
    return bynderApi.getSecurityProfiles();
  }

}
