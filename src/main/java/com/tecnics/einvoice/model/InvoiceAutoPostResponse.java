package com.tecnics.einvoice.model;

public class InvoiceAutoPostResponse {
	
	private String failure_message;
	
	public String getFailure_message() {
		return failure_message;
	}

	public void setFailure_message(String failure_message) {
		this.failure_message = failure_message;
	}

	public String getResp_message() {
		return resp_message;
	}

	public void setResp_message(String resp_message) {
		this.resp_message = resp_message;
	}

	public String getResp_doc_number() {
		return resp_doc_number;
	}

	public void setResp_doc_number(String resp_doc_number) {
		this.resp_doc_number = resp_doc_number;
	}

	private String resp_message;
	
	private String resp_doc_number;
	
	@Override
	public String toString() {
		return "InvoiceAutoPostResponse [resp_message=" + resp_message + ", resp_doc_number=" + resp_doc_number + ", failure_message=" + failure_message
				+ "]";
	}

}




