/**
 * 
 */
package com.tecnics.einvoice.model;

public class FolderResponse {

	
	String hasError ;
	Results results;
	String folderId;
	public String getHasError() {
		return hasError;
	}
	public void setHasError(String hasError) {
		this.hasError = hasError;
	}
	public Results getResults() {
		this.folderId =  results.getFolderId();
		return results;
	}
	public String getFolderID() {
		return folderId;
	}
	public void setFolderID(String folderID) {
		this.folderId = folderID;
	}
	public void setResults(Results results) {
		this.results = results;
		
	}
	
	
}


 class Results {
	String folderId ;

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
}
