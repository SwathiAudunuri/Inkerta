package com.tecnics.einvoice.model;

import java.util.List;

import com.tecnics.einvoice.entity.AttachmentDetails;
import com.tecnics.einvoice.entity.ExternalInvoiceDocumentDetails;
import com.tecnics.einvoice.model.InvoiceAction;

public class ExternalInvoiceRequestModel {
	
	private InvoiceAction actionDetails;
	private ExternalInvoiceDocumentDetails invoiceDetails;
	private List<AttachmentDetails> attachmentDetails;
	
	private MailAddresses mailAddresses;
	
	
	
	public MailAddresses getMailAddresses() {
		return mailAddresses;
	}


	public void setMailAddresses(MailAddresses mailAddresses) {
		this.mailAddresses = mailAddresses;
	}


	public InvoiceAction getActionDetails() {
		return actionDetails;
	}


	public void setActionDetails(InvoiceAction actionDetails) {
		this.actionDetails = actionDetails;
	}


	public ExternalInvoiceDocumentDetails getInvoiceDetails() {
		return invoiceDetails;
	}


	public void setInvoiceDetails(ExternalInvoiceDocumentDetails invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}


	public List<AttachmentDetails> getAttachmentDetails() {
		return attachmentDetails;
	}


	public void setAttachmentDetails(List<AttachmentDetails> attachmentDetails) {
		this.attachmentDetails = attachmentDetails;
	}


	@Override
	public String toString() {
		return "ExternalInvoiceRequestModel [invoiceDetails=" + invoiceDetails 
				+ ", actionDetails=" + actionDetails + "]";
	}

}


