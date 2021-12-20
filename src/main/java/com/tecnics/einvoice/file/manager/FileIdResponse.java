package com.tecnics.einvoice.file.manager;

public class FileIdResponse {
	private String fileId;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public FileIdResponse(String fileId) {
		super();
		this.fileId = fileId;
	}

	public FileIdResponse() {
		super();
	}

}
