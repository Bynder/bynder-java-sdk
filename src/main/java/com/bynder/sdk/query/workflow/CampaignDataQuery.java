/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.workflow;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CampaignDataQuery {

	private final String name;

	private final String key;
	
	@SerializedName(value = "responsibleID")
	private final String responsibleId;
	
	private String description;
	
	private String dateStart;
	
	private String deadline;
	
	private String closed;
	
	private Map<String, String> campaignMetaproperties;
	
	public CampaignDataQuery(String name, String key, String responsibleId) {
		this.name = name;
		this.key = key;
		this.responsibleId = responsibleId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getResponsibleId() {
		return responsibleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getClosed() {
		return closed;
	}

	public void setClosed(String closed) {
		this.closed = closed;
	}

	public Map<String, String> getCampaignMetaproperties() {
		return campaignMetaproperties;
	}

	public void setCampaignMetaproperties(Map<String, String> campaignMetaproperties) {
		this.campaignMetaproperties = campaignMetaproperties;
	}
}
