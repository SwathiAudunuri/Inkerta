package com.tecnics.einvoice.alfresco;

public class FolderRequest {

	
	private String companyName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "FolderRequest [companyName=" + companyName + "]";
	}

	public FolderRequest(String companyName) {
		super();
		this.companyName = companyName;
	}


	public FolderRequest() {
		super();
	}
	
	
	
	
}
