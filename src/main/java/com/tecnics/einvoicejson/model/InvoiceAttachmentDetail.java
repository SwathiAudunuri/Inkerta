package com.tecnics.einvoicejson.model;

public class InvoiceAttachmentDetail {
	public String doc_type;
	public String mime_type;
	public String base64;
	public String fileName;

	public String getDoc_type() {
		return doc_type;
	}

	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}

	public String getMime_type() {
		return mime_type;
	}

	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getFolderId() {
		return fileName;
	}

	public void setFolderId(String folderId) {
		this.fileName = folderId;
	}

	@Override
	public String toString() {
		return "InvoiceAttachmentDetail [doc_type=" + doc_type + ", mime_type=" + mime_type + ", base64=" + base64
				+ ", fileName=" + fileName + "]";
	}

}