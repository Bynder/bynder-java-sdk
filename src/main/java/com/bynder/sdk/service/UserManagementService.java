/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import java.util.List;

import com.bynder.sdk.model.BynderUser;
import com.bynder.sdk.query.UserQuery;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Interface to represent operations that can be done to the Bynder User Management.
 */
public interface UserManagementService {

  /**
   * Gets users.
   * 
   * @param userQuery Information to correctly filter users.
   * @return {@link Observable} with list of {@link BynderUser}.
   */
  Observable<Response<List<BynderUser>>> getUsers(UserQuery userQuery) throws IllegalAccessException;

}
