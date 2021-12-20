package com.tecnics.einvoice.exceptions;

public class CustomExceptionHandler extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6725369600304532735L;

	public CustomExceptionHandler(String message) {
		
		super(message);
	}
}
