package com.tecnics.einvoice.model;

import java.util.List;

public class SendReminderModel {
	
	private List<String> toAddresses;
	private List <String> ccAddresses;
	private List <String> bccAddresses;
	private String emailSubject;
	private String mailBody;
	private boolean includeAttachment;
	private String document_ref_id;
	private boolean external;
	
	
	public List<String> getToAddresses() {
		return toAddresses;
	}


	public void setToAddresses(List<String> toAddresses) {
		this.toAddresses = toAddresses;
	}


	public List<String> getCcAddresses() {
		return ccAddresses;
	}


	public void setCcAddresses(List<String> ccAddresses) {
		this.ccAddresses = ccAddresses;
	}


	public List<String> getBccAddresses() {
		return bccAddresses;
	}


	public void setBccAddresses(List<String> bccAddresses) {
		this.bccAddresses = bccAddresses;
	}


	public String getEmailSubject() {
		return emailSubject;
	}


	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}


	public String getMailBody() {
		return mailBody;
	}


	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}


	public boolean isIncludeAttachment() {
		return includeAttachment;
	}


	public void setIncludeAttachment(boolean includeAttachment) {
		this.includeAttachment = includeAttachment;
	}


	public String getDocument_ref_id() {
		return document_ref_id;
	}


	public void setDocument_ref_id(String document_ref_id) {
		this.document_ref_id = document_ref_id;
	}


	public boolean isExternal() {
		return external;
	}


	public void setExternal(boolean external) {
		this.external = external;
	}


	@Override
	public String toString() {
		return "SendReminderModel [emailSubject=" + emailSubject + ", document_ref_id=" + document_ref_id + ", external=" + external
				+ "]";
	}

	

	
}

