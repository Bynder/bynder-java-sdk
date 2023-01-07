/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.workflow;

public class CampaignQuery {

	private final String id;
	
	public CampaignQuery(String id) {
		this.id = id;
	}
	
	public String getCampaignId() {
		return id;
	}
}
