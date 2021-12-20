package com.tecnics.einvoice.alfresco;

import java.util.List;


public class AlfrescoFileUploadResponseList {
	private boolean hasError ;
	private List<AlfrescoUploadResponse> results;
	@Override
	public String toString() {
		return "AlfreacoFileUploadResponse [hasError=" + hasError + ", results=" + results + "]";
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public List<AlfrescoUploadResponse> getResults() {
		return results;
	}
	public void setResults(List<AlfrescoUploadResponse> results) {
		this.results = results;
	}
	
	public AlfrescoFileUploadResponseList(boolean hasError, List<AlfrescoUploadResponse> results) {
		super();
		this.hasError = hasError;
		this.results = results;
	}
	
	public AlfrescoFileUploadResponseList() {
		super();
	}
	
	
}
