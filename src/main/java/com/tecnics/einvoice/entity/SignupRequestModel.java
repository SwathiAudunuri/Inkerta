package com.tecnics.einvoice.entity;

public class SignupRequestModel {
	
	
	public PartnerDetailSignup partnerDetails;
	
	public CompanyGstinDetail gstnDetails;
																					
	public ContactDetail contactDetails;
	
	public String alias;
	
	public String password;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public PartnerDetailSignup getPartnerDetails() {
		return partnerDetails;
	}

	public void setPartnerDetails(PartnerDetailSignup partnerDetails) {
		this.partnerDetails = partnerDetails;
	}

	public CompanyGstinDetail getGstnDetails() {
		return gstnDetails;
	}

	public void setGstnDetails(CompanyGstinDetail gstnDetails) {
		this.gstnDetails = gstnDetails;
	}

	public ContactDetail getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(ContactDetail contactDetails) {
		this.contactDetails = contactDetails;
	}

	public SignupRequestModel(PartnerDetailSignup partnerDetails, CompanyGstinDetail gstnDetails,
			ContactDetail contactDetails) {
		super();
		this.partnerDetails = partnerDetails;
		this.gstnDetails = gstnDetails;
		this.contactDetails = contactDetails;
	}

	public SignupRequestModel() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}
