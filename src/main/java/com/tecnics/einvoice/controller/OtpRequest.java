package com.tecnics.einvoice.controller;

public class OtpRequest {

	
	private String  primaryPhoneNumber;
	private String email;
	private Long secondaryPhoneNumber;
	private String otpKey;

	public OtpRequest() {
		super();
	}

	public OtpRequest(String primaryPhoneNumber, String email) {
		super();
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public String getOtpKey() {
		return otpKey;
	}

	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}

	public Long getSecondaryPhoneNumber() {
		return secondaryPhoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setOtpKey(String otpKey) {
		this.otpKey = otpKey;
	}

	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		this.primaryPhoneNumber = primaryPhoneNumber;
	}

	public void setSecondaryPhoneNumber(Long secondaryPhoneNumber) {
		this.secondaryPhoneNumber = secondaryPhoneNumber;
	}

	@Override
	public String toString() {
		return "OtpRequest [primaryPhoneNumber=" + primaryPhoneNumber + ", email=" + email + "]";
	}
	
	
}
