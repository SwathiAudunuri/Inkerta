package com.tecnics.einvoice.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {
		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null)
			errorMessageDescription = ex.toString();
		ErrorMessage apiError = new ErrorMessage(errorMessageDescription);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.OK) ;
	}
	@ExceptionHandler(value = InternalException.class)
	@ResponseBody
	public ResponseEntity<Object> handleInternalException(InternalException ex) {
		System.err.println("Internal Exception");
		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null)
			errorMessageDescription = ex.toString();
		ErrorMessage apiError = new ErrorMessage(errorMessageDescription);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND) ;
	}
	@ExceptionHandler(value = TokenExpiryException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public String handleCustomeException(TokenExpiryException ex) {
		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null)
			errorMessageDescription = ex.toString();
		ErrorMessage apiError = new ErrorMessage(errorMessageDescription);
		return "error";
	}

}