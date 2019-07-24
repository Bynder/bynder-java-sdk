/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import java.util.List;
import java.util.Map;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.BynderUser;
import com.bynder.sdk.query.UserQuery;
import com.bynder.sdk.service.AssetBankService;
import com.bynder.sdk.service.UserManagementService;
import com.bynder.sdk.util.Utils;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Implementation of {@link AssetBankService}.
 */
public class UserManagementServiceImpl implements UserManagementService {

  /**
   * Instance of {@link BynderApi} which handles the HTTP communication with the Bynder API.
   */
  private final BynderApi bynderApi;

  /**
   * Initialises a new instance of the class.
   *
   * @param bynderApi Instance to handle the HTTP communication with the Bynder API.
   */
  public UserManagementServiceImpl(final BynderApi bynderApi) {
    this.bynderApi = bynderApi;
  }

  /**
   * Check {@link AssetBankService} for more information.
   */
  @Override
  public Observable<Response<List<BynderUser>>> getUsers(final UserQuery userQuery)
      throws IllegalAccessException {
    Map<String, String> params = Utils.getApiParameters(userQuery);
    return bynderApi.getUsers(params);
  }

}
