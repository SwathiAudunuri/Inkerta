package com.tecnics.einvoice.model;

import java.util.List;

public class UserLoginDetails {

	private String securityToken;
	private String userId;
	private String userAlias;
	private List<String> roles;
	private List<String> partnerRoles;
	private String email;
	private String partnerName;
	private String partnerId;
	private String partnerType;
	private String firstName;

	private String lastName;
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public UserLoginDetails() {
		super();
	}

	private UserLoginDetails details;

	public UserLoginDetails(UserLoginDetails response) {
		this.details = response;
	}

	@Override
	public String toString() {
		return "UserLoginDetails [securityToken=" + securityToken + ", userId=" + userId + ", roles=" + roles
				+ ", email=" + email + ", partnerName=" + partnerName + "]";
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserAlias() {
	        return userAlias;
	}

	public void setUserAlias(String userAlias) {
	        this.userAlias = userAlias;
	 }


	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public List<String> getPartnerRoles() {
		return partnerRoles;
	}

	public void setPartnerRoles(List<String> partnerRoles) {
		this.partnerRoles = partnerRoles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserLoginDetails getDetails() {
		return details;
	}

	public void setDetails(UserLoginDetails details) {
		this.details = details;
	}
	
	
	public UserLoginDetails(String securityToken, String userId, List<String> roles, String email, String partnerName) {
		super();
		this.securityToken = securityToken;
		this.userId = userId;
		this.roles = roles;
		this.email = email;
		this.partnerName = partnerName;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}


}
