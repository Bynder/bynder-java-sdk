/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.Map;

import com.bynder.sdk.api.BynderApi;
import com.google.gson.annotations.SerializedName;

/**
 * Media model returned by {@link BynderApi#getRecentlyRemovedMediaList(Map)}.
 */
public class DeletedMedia {

	/**
	 * Media id.
	 */
	private String id;
	/**
	 * Media name.
	 */
	private String name;
	/**
	 * Date removed.
	 */
	private String dateRemoved;
	/**
	 * Date created.
	 */
	private String dateCreated;
	/**
	 * Username of the user triggering the deletion.
	 */
	private String username;
	/**
	 * UserID of the user triggering the deletion.
	 */
	private String userId;
	/**
	 * Public URL of the media item when available.
	 */
	@SerializedName(value = "S3_filepointer")
	private String publicUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateRemoved() {
		return dateRemoved;
	}

	public void setDateRemoved(String dateRemoved) {
		this.dateRemoved = dateRemoved;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPublicUrl() {
		return publicUrl;
	}

	public void setPublicUrl(String publicUrl) {
		this.publicUrl = publicUrl;
	}

}
