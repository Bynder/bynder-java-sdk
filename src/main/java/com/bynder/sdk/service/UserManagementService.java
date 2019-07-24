/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.bynder.sdk.model.BynderUser;
import com.bynder.sdk.query.UserCreateQuery;
import com.bynder.sdk.query.UserModifyQuery;
import com.bynder.sdk.query.UserQuery;
import com.bynder.sdk.util.Utils;

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

  /**
   * Creates a user.
   *
   * @param userCreateQuery Information about the user we want to create.
   * @return {@link Observable} with {@link BynderUser} information.
   * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
   *         information.
   */
  Observable<Response<BynderUser>> createUser(UserCreateQuery userCreateQuery)
      throws IllegalAccessException;

  /**
   * Gets a user.
   * 
   * @param userId Id of the user.
   * @return {@link Observable} with list of {@link BynderUser}.
   */
  Observable<Response<BynderUser>> retrieveUser(String userId) throws IllegalAccessException;

  /**
   * Creates a user.
   *
   * @Param userId Id of the user.
   * @param userModifyQuery Information about the user we want to modify.
   * @return {@link Observable} with {@link BynderUser} information.
   * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
   *         information.
   */
  Observable<Response<BynderUser>> modifyUser(String userId, UserModifyQuery userModifyQuery)
      throws IllegalAccessException;

}
