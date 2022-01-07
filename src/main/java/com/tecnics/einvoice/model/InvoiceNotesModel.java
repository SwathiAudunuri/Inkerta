package com.tecnics.einvoice.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class InvoiceNotesModel {
	
	
	@Column(name="notes_id")
	private Integer notesId;
	

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="created_by")
	private String createdBy;
	

	@Column(name="createdbydisplayname")
	private String createdByDisplayName;
	
	public String getCreatedByDisplayName() {
		return createdByDisplayName;
	}

	public void setCreatedByDisplayName(String createdByDisplayName) {
		this.createdByDisplayName = createdByDisplayName;
	}

	@Column(name="notes")
	private String notes;
	
	@Column(name="partner_id")
	private String partnerId;
	
	@Column(name="document_ref_id")
	private String documentRefId;

	public String getDocumentRefId() {
		return documentRefId;
	}

	public void setDocumentRefId(String documentRefId) {
		this.documentRefId = documentRefId;
	}

	public Integer getNotesId() {
		return notesId;
	}

	public void setNotesId(Integer notesId) {
		this.notesId = notesId;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}


}
