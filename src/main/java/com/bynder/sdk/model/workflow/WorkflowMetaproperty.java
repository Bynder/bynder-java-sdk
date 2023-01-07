/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkflowMetaproperty {

	@SerializedName(value = "ID")
	private String id;

	private String label;

	@SerializedName(value = "short_name")
	private String shortName;

	private String type;

	private String entity;

	private String dateCreated;

	@SerializedName(value = "created_by")
	private WorkflowUser createdBy;

	@SerializedName(value = "assetbank_metaproperty")
	private WorkflowAssetbankMetaproperty assetbankMetaproperty;

	private List<WorkflowMetapropertyOption> options;

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public String getType() {
		return type;
	}
	
	public String getEntity() {
		return entity;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public WorkflowUser getCreatedBy() {
		return createdBy;
	}
	
	public WorkflowAssetbankMetaproperty getAssetbankMetaproperty() {
		return assetbankMetaproperty;
	}

	public List<WorkflowMetapropertyOption> getOptions() {
		return options;
	}
}
