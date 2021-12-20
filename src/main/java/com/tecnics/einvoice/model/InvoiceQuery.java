package com.tecnics.einvoice.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonRootName;


@JsonRootName(value = "data")
public class InvoiceQuery {
	
	private Integer id;
	
	private String queryRefId;

	private String documentRefId;
	private Timestamp createdDate;

	private String createdBy;

	private String createdByDisplayName;

	private String queryType;

	private String queryFrom;

	private String queryText;

	private String dispatchMode;
	private Timestamp dispatchedOn;
	private Boolean isDispatched;

	private String parentQueryRefId;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQueryRefId() {
		return this.queryRefId;
	}

	public void setQueryRefId(String queryRefId) {
		this.queryRefId = queryRefId;
	}

	public String getDocumentRefId() {
		return documentRefId;
	}

	public void setDocumentRefId(String documentRefId) {
		this.documentRefId = documentRefId;
	}

	public InvoiceQuery() {
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getCreatedByDisplayName() {
		return createdByDisplayName;
	}

	public void setCreatedByDisplayName(String createdByDisplayName) {
		this.createdByDisplayName = createdByDisplayName;
	}
	
	public String getQueryFrom() {
		return this.queryFrom;
	}

	public void setQueryFrom(String queryFrom) {
		this.queryFrom = queryFrom;
	}

	public String getQueryText() {
		return this.queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public String getQueryType() {
		return this.queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getDispatchMode() {
		return this.dispatchMode;
	}

	public void setDispatchMode(String dispatchMode) {
		this.dispatchMode = dispatchMode;
	}

	public Timestamp getDispatchedOn() {
		return this.dispatchedOn;
	}

	public void setDispatchedOn(Timestamp dispatchedOn) {
		this.dispatchedOn = dispatchedOn;
	}

	public Boolean getIsDispatched() {
		return this.isDispatched;
	}

	public void setIsDispatched(Boolean isDispatched) {
		this.isDispatched = isDispatched;
	}

	public String getParentQueryRefId() {
		return this.parentQueryRefId;
	}

	public void setParentQueryRefId(String parentQueryRefId) {
		this.parentQueryRefId = parentQueryRefId;
	}

		
	@Override
	public String toString() {
		return "InvoiceQuery [query_ref_id=" + queryRefId + ", document_ref_id=" + documentRefId + ", query_type=" + queryType
				+ ", query_text=" + queryText + ", query_from=" + queryFrom + ", created_by=" + createdBy + ", created_date=" + createdDate
				+ ", is_dispatched=" + isDispatched + ", dispatch_mode=" + dispatchMode + ", dispatched_on=" + dispatchedOn + ", parent_query_ref_id="
				+ parentQueryRefId + "]";
	}

	
	
	private List<InvoiceQuery> children;
	
	public List<InvoiceQuery> getChildren() {
		return children;
	}

	public void setChildren(List<InvoiceQuery> children) {
		this.children = children;
	}
	
	
	

}
