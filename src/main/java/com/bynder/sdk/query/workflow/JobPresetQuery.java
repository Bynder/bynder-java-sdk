/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.workflow;

public class JobPresetQuery {

	private final String id;

	public JobPresetQuery(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
