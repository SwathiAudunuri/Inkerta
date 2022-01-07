package com.tecnics.einvoice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the external_partner_details database table.
 * 
 */
@Entity
@Table(name = "external_partner_details", schema = "einvoicing")
public class ExternalPartnerDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Id
	@Column(name = "partner_id", unique = true, nullable = false, length = 7)
	private String partnerId;

	@Column(name = "company_name", length = 75)
	private String companyName;
	
	private String gstin;

	private String country;	
	
	@Column(name = "firm_type")
	private String firmType;

	private String industry;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	private String title;
	@Column(name = "primary_phone_number")
	private String primaryPhoneNumber;
	@Column(name = "secondary_phone_number")
	private String secondaryPhoneNumber;
	private String email;	
	@Column(name = "nature_of_business")
	private String natureOfBusiness;

	
	@Column(name = "pan_no", length = 10)
	private String panNo;

	@Column(name = "partner_type", length = 20)
	private String partnerType;

	@Column(name = "payment_terms")
	private String paymentTerms;

	@Column(name = "reporting_currency")
	private String reportingCurrency;
	
	private String website;
	
	@Column(name = "partner_to")
	private String partnerTo;
	
	@Column(name = "partner_address")
	private String partnerAddress;
	
	
	
	

	public ExternalPartnerDetails() {
	}
	
	

	public String getPartnerAddress() {
		return partnerAddress;
	}



	public void setPartnerAddress(String partnerAddress) {
		this.partnerAddress = partnerAddress;
	}



	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFirmType() {
		return firmType;
	}

	public void setFirmType(String firmType) {
		this.firmType = firmType;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
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

	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getReportingCurrency() {
		return reportingCurrency;
	}

	public void setReportingCurrency(String reportingCurrency) {
		this.reportingCurrency = reportingCurrency;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPartnerTo() {
		return partnerTo;
	}

	public void setPartnerTo(String partnerTo) {
		this.partnerTo = partnerTo;
	}

	

}
