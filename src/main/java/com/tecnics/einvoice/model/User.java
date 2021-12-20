package com.tecnics.einvoice.model;

import java.sql.Date;
import java.util.List;


public class User {

    private String userId;
    private String partnerId;
    private String firstName;
    private String securityToken;
    private String refreshToken;
    private String lastName;
    private String title;
    private String phoneNumber;
    private String mobileNumber;
    private String email;
    private String location;
    private Date passwordRestOn;
    private String partnerName;
  

	private Date createdOn;
    private boolean isLocked;
    private String authorizationKey;
    
    
    private String company_name;
    private List<String> roles;
    
    private String partnerType;
    public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }




    public String getAuthorizationKey() {
        return authorizationKey;
    }

    public void setAuthorizationKey(String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getPasswordRestOn() {
        return passwordRestOn;
    }

    public void setPasswordRestOn(Date passwordRestOn) {
        this.passwordRestOn = passwordRestOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", partnerId=" + partnerId + ", firstName=" + firstName + ", securityToken="
				+ securityToken + ", refreshToken=" + refreshToken + ", lastName=" + lastName + ", title=" + title
				+ ", phoneNumber=" + phoneNumber + ", mobileNumber=" + mobileNumber + ", email=" + email + ", location="
				+ location + ", passwordRestOn=" + passwordRestOn + ", partnerName=" + partnerName + ", createdOn="
				+ createdOn + ", isLocked=" + isLocked + ", authorizationKey=" + authorizationKey + "]";
	}


	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

 

}


