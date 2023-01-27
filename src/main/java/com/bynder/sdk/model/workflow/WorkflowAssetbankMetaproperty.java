/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

public class WorkflowAssetbankMetaproperty {

	@SerializedName(value = "cf_id")
	private String id;
	
	public String getId() {
		return id;
	}
}
