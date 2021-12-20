package com.tecnics.einvoice.entity;

public class PasswordResetRequest {

	private String user_id;
	
	private String password;
	
	private String aliasName;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public PasswordResetRequest(String user_id, String password, String aliasName) {
		super();
		this.user_id = user_id;
		this.password = password;
		this.aliasName = aliasName;
	}

	public PasswordResetRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "PasswordResetRequest [user_id=" + user_id + ", password=" + password + ", aliasName=" + aliasName + "]";
	}
	
}
