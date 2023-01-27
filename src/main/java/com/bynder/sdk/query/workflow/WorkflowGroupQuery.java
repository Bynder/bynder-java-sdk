/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.workflow;

public class WorkflowGroupQuery {

	private final String id;

	public WorkflowGroupQuery(String id) {
		this.id = id;
	}
	
	public String getGroupId() {
		return id;
	}
}
