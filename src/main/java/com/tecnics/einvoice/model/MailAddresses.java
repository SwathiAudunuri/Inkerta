package com.tecnics.einvoice.model;

import java.util.List;
public class MailAddresses {
	
	private List<String> toAddresses;
	private List <String> ccAddresses;
	private List <String> bccAddresses;
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

}
