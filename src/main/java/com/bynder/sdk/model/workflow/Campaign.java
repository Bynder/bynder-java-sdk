/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Campaign {

	@SerializedName(value = "ID")
	private String id;
	
	private String name;
	
	private String key;
	
	private String description;
	
	@SerializedName(value = "accountID")
	private String accountId;
	
	private String dateCreated;
	
	@SerializedName(value = "createdByID")
	private String createdById;
	
	private String dateModified;
	
	@SerializedName(value = "responsibleID")
	private String responsibleId;
	
	private Boolean closed;
	
	private String dateStart;
	
	private String deadline;
		
	@SerializedName(value = "presetID")
	private String presetID;
	
	@SerializedName(value = "thumbnailURL")
	private String thumbnailUrl;
	
	private Map<String, String> campaignMetaproperties;
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public String getDateModified() {
		return dateModified;
	}

	public Boolean getClosed() {
		return closed;
	}

	public String getDateStart() {
		return dateStart;
	}

	public String getDeadline() {
		return deadline;
	}

	public Map<String, String> getCampaignMetaproperties() {
		return campaignMetaproperties;
	}

	public String getResponsibleId() {
		return responsibleId;
	}
}
