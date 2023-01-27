/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.workflow;

import com.bynder.sdk.model.workflow.JobStatus;
import com.bynder.sdk.query.OrderBy;
import com.bynder.sdk.query.decoder.ApiField;
import com.bynder.sdk.query.decoder.StringArrayParameterDecoder;

public class JobQuery {

	private final String id;

	@ApiField
	private String dateCreatedFrom;

	@ApiField
	private String dateCreatedTo;

	@ApiField
	private String dateModifiedFrom;

	@ApiField
	private String dateModifiedTo;

	@ApiField
	private String deadlineFrom;

	@ApiField
	private String deadlineTo;

	@ApiField(name = "responsibleIDs", decoder = StringArrayParameterDecoder.class)
	private String[] responsibleIds;

	@ApiField(name = "accountableIDs", decoder = StringArrayParameterDecoder.class)
	private String[] accountableIds;

	@ApiField
	private JobStatus status;

	@ApiField
	private OrderBy orderBy;

	@ApiField
	private Integer limit;

	@ApiField
	private Integer page;

	public JobQuery() {
		this.id = null;
	}

	public JobQuery(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public String getDateCreatedFrom() {
		return dateCreatedFrom;
	}

	public JobQuery setDateCreatedFrom(String dateCreatedFrom) {
		this.dateCreatedFrom = dateCreatedFrom;
		return this;
	}

	public String getDateCreatedTo() {
		return dateCreatedTo;
	}

	public JobQuery setDateCreatedTo(String dateCreatedTo) {
		this.dateCreatedTo = dateCreatedTo;
		return this;
	}

	public String getDateModifiedFrom() {
		return dateModifiedFrom;
	}

	public JobQuery setDateModifiedFrom(String dateModifiedFrom) {
		this.dateModifiedFrom = dateModifiedFrom;
		return this;
	}

	public String getDateModifiedTo() {
		return dateModifiedTo;
	}

	public JobQuery setDateModifiedTo(String dateModifiedTo) {
		this.dateModifiedTo = dateModifiedTo;
		return this;
	}

	public String getDeadlineFrom() {
		return deadlineFrom;
	}

	public JobQuery setDeadlineFrom(String deadlineFrom) {
		this.deadlineFrom = deadlineFrom;
		return this;
	}

	public String getDeadlineTo() {
		return deadlineTo;
	}

	public JobQuery setDeadlineTo(String deadlineTo) {
		this.deadlineTo = deadlineTo;
		return this;
	}

	public String[] getResponsibleIds() {
		return responsibleIds;
	}

	public JobQuery setResponsibleIds(String... responsibleIds) {
		this.responsibleIds = responsibleIds;
		return this;
	}

	public String[] getAccountableIds() {
		return accountableIds;
	}

	public JobQuery setAccountableIds(String... accountableIds) {
		this.accountableIds = accountableIds;
		return this;
	}

	public JobStatus getStatus() {
		return status;
	}

	public JobQuery setStatus(JobStatus status) {
		this.status = status;
		return this;
	}

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public JobQuery setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public Integer getLimit() {
		return limit;
	}

	public JobQuery setLimit(Integer limit) {
		this.limit = limit;
		return this;
	}

	public Integer getPage() {
		return page;
	}

	public JobQuery setPage(Integer page) {
		this.page = page;
		return this;
	}
}
