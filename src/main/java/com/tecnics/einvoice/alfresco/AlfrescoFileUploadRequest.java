package com.tecnics.einvoice.alfresco;

import java.util.List;

public class AlfrescoFileUploadRequest {

	private String documentName;
	private String documentBase64;
	private String addedBy;
	private String mimeType;
	private String className;
	private String folderId;
	private List<FileUploadProperties> properties;

	public String getDocumentName() {
		return documentName;
	}
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public String getDocumentBase64() {
		return documentBase64;
	}
	
	public void setDocumentBase64(String documentBase64) {
		this.documentBase64 = documentBase64;
	}
	
	public String getAddedBy() {
		return addedBy;
	}
	
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getFolderId() {
		return folderId;
	}
	
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
	

	public AlfrescoFileUploadRequest() {
		super();
	}

	public List<FileUploadProperties> getProperties() {
		return properties;
	}

	public void setProperties( List<FileUploadProperties> properties) {
		this.properties = properties;
	}

	/**
	 * @param documentName
	 * @param documentBase64
	 * @param addedBy
	 * @param mimeType
	 * @param className
	 * @param folderId
	 * @param properties
	 */
	public AlfrescoFileUploadRequest(String documentName, String documentBase64, String addedBy, String mimeType,
			String className, String folderId,  List<FileUploadProperties> properties) {
		super();
		this.documentName = documentName;
		this.documentBase64 = documentBase64;
		this.addedBy = addedBy;
		this.mimeType = mimeType;
		this.className = className;
		this.folderId = folderId;
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "AlfrescoFileUpload [documentName=" + documentName + ", documentBase64=" + documentBase64 + ", addedBy="
				+ addedBy + ", mimeType=" + mimeType + ", className=" + className + ", folderId=" + folderId
				+ ", properties=" + properties + "]";
	}

}
