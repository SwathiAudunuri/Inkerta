package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the invoice_status_tracker database table.
 * 
 */

@Entity
@Table(name="external_invoice_status_tracker", schema = "einvoicing")
public class ExternalInvoiceStatusTracker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="invoice_status_tracker_id")
	private Integer invoiceStatusTrackerId;
	

	@Column(name="document_ref_id")
	private String documentRefId;
	

	@Column(name="action_type")
	private String actionType;
	

	@Column(name="action")
	private String action;
	
	@Column(name="action_comments")
	private String actionComments;
	
	@Column(name="action_by")
	private String actionBy;
	
	@Column(name="action_to")
	private String actionTo;
	

	@Column(name="action_date")
	private Timestamp actionDate;


	public ExternalInvoiceStatusTracker() {
	
	}

	public Integer getInvoiceStatusTrackerId() {
		return this.invoiceStatusTrackerId;
	}

	public void setInvoiceStatusTrackerId(Integer invoiceStatusTrackerId) {
		this.invoiceStatusTrackerId = invoiceStatusTrackerId;
	}

	public String getDocumentRefId() {
		return this.documentRefId;
	}

	public void setDocumentRefId(String documentRefId) {
		this.documentRefId = documentRefId;
	}
	
	public String getActionType() {
		return this.actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	
	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getActionComments() {
		return this.actionComments;
	}

	public void setActionComments(String actionComments) {
		this.actionComments = actionComments;
	}
	
	public String getActionBy() {
		return this.actionBy;
	}

	public void setActionBy(String actionBy) {
		
		this.actionBy = actionBy;
	}
	
	public String getActionTo() {
		return actionTo;
	}

	public void setActionTo(String actionTo) {
		this.actionTo = actionTo;
	}
		

	public Timestamp getActionDate() {
		return this.actionDate;
	}

	public void setActionDate(Timestamp actionDate) {
		this.actionDate = actionDate;
	}


}



