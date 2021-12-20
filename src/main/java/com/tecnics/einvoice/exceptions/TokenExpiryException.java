package com.tecnics.einvoice.exceptions;

public class TokenExpiryException extends RuntimeException {
	private static final long serialVersionUID = 1L;
		private String code;
	    private String message;
	    private Throwable throwable;

	    public Throwable getThrowable() {
	        return throwable;
	    }

	    public TokenExpiryException() {
	        super();
	    }

	    public TokenExpiryException (String code,String message) {
	        this.code = code;
	        this.message =  message;
	    }

	    public TokenExpiryException (String code,String message,Throwable e) {
	        this.code = code;
	        this.message =  message;
	        this.throwable = e;
	    }

	    public String getCode() {
	        return this.code;
	    }

	    public String getMessage() {
	        return this.message;
	    }

}
