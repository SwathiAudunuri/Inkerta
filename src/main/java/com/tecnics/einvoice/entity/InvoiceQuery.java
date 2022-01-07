package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the invoice_queries database table.
 * 
 */
@Entity
@Table(name="invoice_queries", schema = "einvoicing")
public class InvoiceQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="query_ref_id")
	private String queryRefId;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="dispatch_mode")
	private String dispatchMode;

	@Column(name="dispatched_on")
	private Timestamp dispatchedOn;

	@Column(name="is_dispatched")
	private Boolean isDispatched;

	@Column(name="parent_query_ref_id")
	private String parentQueryRefId;

	@Column(name="query_from")
	private String queryFrom;

	@Column(name="query_text")
	private String queryText;

	@Column(name="query_type")
	private String queryType;
	
	@Column(name="subject")
	private String subject;

	
	

	@Column(name="document_ref_id")
	private String documentRefId;
	
	@Column(name="created_by")
	private String createdBy;
	
	@JsonSerialize
	@JsonDeserialize
	@Transient
	private List<AttachmentDetails> attachmentDetails;

	public List<AttachmentDetails> getAttachmentDetails() {
		return attachmentDetails;
	}

	public void setAttachmentDetails(List<AttachmentDetails> attachmentDetails) {
		this.attachmentDetails = attachmentDetails;
	}


	public String getDocumentRefId() {
		return documentRefId;
	}

	public void setDocumentRefId(String documentRefId) {
		this.documentRefId = documentRefId;
	}

	public InvoiceQuery() {
	}

	public String getQueryRefId() {
		return this.queryRefId;
	}

	public void setQueryRefId(String queryRefId) {
		this.queryRefId = queryRefId;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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
	
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getQueryType() {
		return this.queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	
	@Override
	public String toString() {
		return "InvoiceQuery [query_ref_id=" + queryRefId + ", document_ref_id=" + documentRefId + ", query_type=" + queryType
				+ ", query_text=" + queryText + " , subject=" + subject + ", query_from=" + queryFrom + ", created_by=" + createdBy + ", created_date=" + createdDate
				+ ", is_dispatched=" + isDispatched + ", dispatch_mode=" + dispatchMode + ", dispatched_on=" + dispatchedOn + ", parent_query_ref_id="
				+ parentQueryRefId + "]";
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}

