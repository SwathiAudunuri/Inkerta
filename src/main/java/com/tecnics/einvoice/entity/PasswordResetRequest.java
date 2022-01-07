package com.tecnics.einvoice.entity;

public class PasswordResetRequest {

	private String user_id;
	
	private String newPassword;
	
	private String oldPassword;
	
	private String aliasName;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public PasswordResetRequest(String user_id, String oldPassword, String newPassword,String aliasName) {
		super();
		this.user_id = user_id;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.aliasName = aliasName;
	}
	
	

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public PasswordResetRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "PasswordResetRequest [user_id=" + user_id + ", old password=" + oldPassword + ", aliasName=" + aliasName + "]";
	}
	
}
