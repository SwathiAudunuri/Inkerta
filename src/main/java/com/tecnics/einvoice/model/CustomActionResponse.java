package com.tecnics.einvoice.model;

public class CustomActionResponse {
	
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
	
	private String response_body;
	
	private String transformed_body;
	
	public String getTransformed_body() {
		return transformed_body;
	}

	public void setTransformed_body(String transformed_body) {
		this.transformed_body = transformed_body;
	}

	public String getResponse_body() {
		return response_body;
	}

	public void setResponse_body(String response_body) {
		this.response_body = response_body;
	}

	@Override
	public String toString() {
		return "CustomActionResponse [resp_message=" + resp_message + ", resp_doc_number=" + resp_doc_number + ", failure_message=" + failure_message
				+ "]";
	}

}




