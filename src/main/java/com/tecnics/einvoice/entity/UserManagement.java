package com.tecnics.einvoice.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the user_management database table.
 * 
 */
@Entity
@Table(name="user_management", schema = "einvoicing")
public class UserManagement implements Serializable {



	@Id
	@Column(name="user_id")
	private String userId;

	@Column(name="created_on")
	private Timestamp createdOn;

	private String email;

	@Column(name="first_name")
	private String firstName;

	@Column(name="is_locked")
	private Boolean isLocked;

	@Column(name="last_name")
	private String lastName;

	private String location;

	@Column(name="primary_phone_number")
	private String primaryPhoneNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="password_reset_on")
	private Date passwordResetOn;

	@Column(name="secondary_phone_number")
	private String secondaryPhoneNumber;

	private String title;

	@JoinColumn(name="partner_id")
	private String partnerId;
	
	
	private String user_alias;
	
	
	public String getUser_alias() {
		return user_alias;
	}


	public void setUser_alias(String user_alias) {
		this.user_alias = user_alias;
	}


	public UserManagement() {
	}

 
	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public Boolean getIsLocked() {
		return this.isLocked;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getLocation() {
		return this.location;
	}


	public String getPartnerId() {
		return partnerId;
	}

	public Date getPasswordResetOn() {
		return this.passwordResetOn;
	}

	
	public String getTitle() {
		return this.title;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public void setPasswordResetOn(Date passwordResetOn) {
		this.passwordResetOn = passwordResetOn;
	}



	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}


	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		this.primaryPhoneNumber = primaryPhoneNumber;
	}


	public String getSecondaryPhoneNumber() {
		return secondaryPhoneNumber;
	}


	public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
		this.secondaryPhoneNumber = secondaryPhoneNumber;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserManagement [userId=");
		builder.append(userId);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", email=");
		builder.append(email);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", isLocked=");
		builder.append(isLocked);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", location=");
		builder.append(location);
		builder.append(", primaryPhoneNumber=");
		builder.append(primaryPhoneNumber);
		builder.append(", passwordResetOn=");
		builder.append(passwordResetOn);
		builder.append(", secondaryPhoneNumber=");
		builder.append(secondaryPhoneNumber);
		builder.append(", title=");
		builder.append(title);
		builder.append(", partnerId=");
		builder.append(partnerId);
		builder.append("]");
		return builder.toString();
	}



}