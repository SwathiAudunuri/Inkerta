/*
 * Licensed Materials - Property of Tecnics Integration Technologies Pvt Ltd.
 *   (C) Copyright Tecnics Corp. 2021
 */
package com.tecnics.einvoice.exceptions;

/**
 * <p>This class is a custom exception class extends from {@link RuntimeException}</p>
 * @since  1.0
 * @version  1.0
 */
public class InternalException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5373474221365604206L;
	private String code;
    private String message;
    private Throwable throwable;

    public Throwable getThrowable() {
        return throwable;
    }

    public InternalException() {
        super();
    }

    public InternalException (String code,String message) {
        this.code = code;
        this.message =  message;
    }
    public InternalException (String message) {
        this.message =  message;
    }

    public InternalException (String code,String message,Throwable e) {
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
