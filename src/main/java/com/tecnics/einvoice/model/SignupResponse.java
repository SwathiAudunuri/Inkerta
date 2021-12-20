package com.tecnics.einvoice.model;

public class SignupResponse {
	
	private String partnerId;
	
	private String userId;
	
	private String Password;
	
	private String alias;

	private String userRole;
	
	private String passwordresetLink;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SignupResponse [partnerId=");
		builder.append(partnerId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", Password=");
		builder.append(Password);
		builder.append(", alias=");
		builder.append(alias);
		builder.append(", userRole=");
		builder.append(userRole);
		builder.append("]");
		return builder.toString();
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getPasswordresetLink() {
		return passwordresetLink;
	}

	public void setPasswordresetLink(String passwordresetLink) {
		this.passwordresetLink = passwordresetLink;
	}

	
}
