package com.tecnics.einvoice.exceptions;

public class ErrorMessage {
private String message;
private int statusCode;
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public int getStatusCode() {
	return statusCode;
}
public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
}
public ErrorMessage(String message) {
	super();
	this.message = message;
	
}
public ErrorMessage() {
	super();
}


}
