package com.tecnics.einvoice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "partner_contact_details", schema = "einvoicing")
public class PartnerContactDetails implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTypeOfContact() {
		return typeOfContact;
	}


	public void setTypeOfContact(String typeOfContact) {
		this.typeOfContact = typeOfContact;
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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="type_of_contact")
	private String typeOfContact;
	
	@Column(name="partner_id")
	private String partnerId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="title")
	private String title;	

	@Column(name="primary_phone_number")
	private String primaryPhoneNumber;
	
	@Column(name="secondary_phone_number")
	private String secondaryPhoneNumber;
	
	@Column(name="email")
	private String email;
	

	@Column(name="last_name")
	private String lastName;
	

}
