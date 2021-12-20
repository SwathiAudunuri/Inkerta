package com.tecnics.einvoice.file.manager;

public class CustomeResponseEntity {
	private boolean hasError;
	private String errorCode;
	private String errorMessage;
	private Object results;
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	

	public boolean getHasError() {
		return hasError;
	}

	public void setHasError(boolean b) {
		this.hasError = b;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object getResults() {
		return results;
	}

	public void setResults(Object results) {
		this.results = results;
	}

	public CustomeResponseEntity(boolean hasError, String errorCode, Object results,String errorMessage) {
		super();
		this.hasError = hasError;
		this.errorCode = errorCode;
		this.results = results;
		this.errorMessage=errorMessage;
	}

	public CustomeResponseEntity() {
		super();
	}

}
