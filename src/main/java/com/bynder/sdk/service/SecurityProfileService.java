/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import java.util.List;

import com.bynder.sdk.model.SecurityProfile;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Interface to represent operations that can be done to the Bynder Security Profile.
 */
public interface SecurityProfileService {

  /**
   * Gets security profiles.
   * 
   * @return {@link Observable} with list of {@link SecurityProfile}.
   */
  Observable<Response<List<SecurityProfile>>> getSecurityProfiles() throws IllegalAccessException;

}
