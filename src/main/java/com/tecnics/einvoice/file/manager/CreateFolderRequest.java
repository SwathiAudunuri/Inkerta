package com.tecnics.einvoice.file.manager;

public class CreateFolderRequest {

	private String partnerId;
	

	public String getPartnerId() {
		return partnerId;
	}


	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	@Override
	public String toString() {
		return "OnboardingCreateFolder [companyName=" + partnerId +  "]";
	}

	

}
