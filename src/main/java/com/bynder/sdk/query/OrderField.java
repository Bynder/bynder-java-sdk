/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Enum used to define by which field the API results should be ordered.
 */
public enum OrderField {

	DATE_CREATED("media.dateCreated"), DATE_REMOVED("media.dateRemoved"), NAME("media.name"),
	USERNAME("media.username");

	private final String name;

	OrderField(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
