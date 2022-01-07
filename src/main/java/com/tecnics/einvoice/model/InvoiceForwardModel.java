package com.tecnics.einvoice.model;

import javax.persistence.Column;

public class InvoiceForwardModel {
	
	private String action;	
	private String actionComments;
	private String actionTo;
	private String documentRefId;
	public String getDocumentRefId() {
		return documentRefId;
	}
	public void setDocumentRefId(String documentRefId) {
		this.documentRefId = documentRefId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getActionComments() {
		return actionComments;
	}
	public void setActionComments(String actionComments) {
		this.actionComments = actionComments;
	}
	public String getActionTo() {
		return actionTo;
	}
	public void setActionTo(String actionTo) {
		this.actionTo = actionTo;
	}
	

}
