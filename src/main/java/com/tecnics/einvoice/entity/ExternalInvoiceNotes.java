
package com.tecnics.einvoice.entity;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the external_invoice_notes database table.
 * 
 */
@Entity
@Table(name="external_invoice_notes", schema = "einvoicing")

public class ExternalInvoiceNotes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="notes_id")
	private Integer notesId;

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
	
	
	@Column(name="document_ref_id")
	private String documentRefId;
	
	@Column(name="notes_type")
	private String notes_type;
	

	public String getNotes_type() {
		return notes_type;
	}

	public void setNotes_type(String notes_type) {
		this.notes_type = notes_type;
	}

	public String getDocumentRefId() {
		return documentRefId;
	}

	public void setDocumentRefId(String documentRefId) {
		this.documentRefId = documentRefId;
	}


	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="partner_id")
	private String partnerId;

}

