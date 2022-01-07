package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the invoice_status_tracker database table.
 * 
 */

@Entity
@Table(name="invoice_status_tracker", schema = "einvoicing")
public class InvoiceStatusTracker implements Serializable {
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
	
	@Column(name="visible_to_partnerid")
	private String visibleToPartnerid;



	@Column(name="action_date")
	private Timestamp actionDate;
	
	@Column(name="dispatch_mode")
	private String dispatchMode;

	@Column(name="dispatched_on")
	private Timestamp dispatchedOn;

	@Column(name="is_dispatched")
	private Boolean isDispatched;
	
	@Column(name="source")
	private String source;
	

	public InvoiceStatusTracker() {
	
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
		

	public String getVisibleToPartnerid() {
		return visibleToPartnerid;
	}

	public void setVisibleToPartnerid(String visibleToPartnerid) {
		this.visibleToPartnerid = visibleToPartnerid;
	}
	public Timestamp getActionDate() {
		return this.actionDate;
	}

	public void setActionDate(Timestamp actionDate) {
		this.actionDate = actionDate;
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
	
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
}


