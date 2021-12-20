package com.tecnics.einvoice.model;

public class UserCredential {
	
	private String password;
	
	private String user;

	public UserCredential() {
		super();
	}

	public UserCredential(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getUser() {
		return user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserCredential [user=" + user + ", password=" + password + "]";
	}
	
	

}
