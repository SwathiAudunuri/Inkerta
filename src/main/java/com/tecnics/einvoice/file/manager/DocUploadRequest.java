package com.tecnics.einvoice.file.manager;

public class DocUploadRequest {
	private String folderId;
	private String documentBase64;
	private String mimeType;
	private String documentName;
	private String docType;


	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getDocumentBase64() {
		return documentBase64;
	}

	public void setDocumentBase64(String documentBase64) {
		this.documentBase64 = documentBase64;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public DocUploadRequest(String folderId, String documentBase64, String mimeType, String documentName) {
		super();
		this.folderId = folderId;
		this.documentBase64 = documentBase64;
		this.mimeType = mimeType;
		this.documentName = documentName;
	}

	public DocUploadRequest() {
		super();
	}

	@Override
	public String toString() {
		return "DocUploadRequest [folderId=" + folderId + ", documentBase64=" + documentBase64 + ", mimeType="
				+ mimeType + ", documentName=" + documentName + "]";
	}

}
