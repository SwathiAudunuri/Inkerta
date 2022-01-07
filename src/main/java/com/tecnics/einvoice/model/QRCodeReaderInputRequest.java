package com.tecnics.einvoice.model;


public class QRCodeReaderInputRequest {

	String fileName;
	boolean readFromAllPages;


	public boolean isReadFromAllPages() {
		return readFromAllPages;
	}

	public void setReadFromAllPages(boolean readFromAllPages) {
		this.readFromAllPages = readFromAllPages;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}

