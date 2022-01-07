package com.tecnics.einvoice.model;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tecnics.einvoice.util.APIError;

public class ResponseMessage {

	private APIError errors;

	private boolean hasError = false;
	private Object results;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date timestamp = new Date();

	public ResponseMessage(APIError errors) {
		super();
		this.setHasError(true);
		this.setErrors(errors);
		this.setResults(results);
	}

	public ResponseMessage(Object results) {
		super();
		this.setResults(results);
	}
	
	public ResponseMessage() {
		super();
		
	}
	

	

	public APIError getErrors() {
		return errors;
	}

	public Object getResults() {
		return results;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setErrors(APIError errors) {
		this.errors = errors;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public void setResults(Object results) {
		this.results = results;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
