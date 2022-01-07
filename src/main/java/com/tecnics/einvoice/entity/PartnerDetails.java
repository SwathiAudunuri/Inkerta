package com.tecnics.einvoice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the partner_details database table.
 * 
 */
@Entity
@Table(name = "partner_details", schema = "einvoicing")
public class PartnerDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "partner_id", unique = true, nullable = false, length = 7)
	private String partnerId;

	@Column(name = "company_name", length = 75)
	private String companyName;

	private String country;

	@Column(name = "ecm_folder_id")
	private String ecmFolderId;

	@Column(name = "expose_to_all_vendors")
	private Boolean exposeToAllVendors;

	@Column(name = "firm_type")
	private String firmType;

	private String industry;

	@Column(name = "key_product_categories")
	private String keyProductCategories;

	@Column(name = "nature_of_business")
	private String natureOfBusiness;

	@Column(name = "no_of_business_partners")
	private Integer noOfBusinessPartners;

	@Column(name = "no_of_invoices_expected")
	private String noOfInvoicesExpected;

	@Column(name = "no_of_portal_users_allowed")
	private Integer noOfPortalUsersAllowed;

	@Column(name = "offered_services")
	private String offeredServices;

	@Column(name = "pan_no", length = 10)
	private String panNo;

	@Column(name = "partner_type", length = 20)
	private String partnerType;

	@Column(name = "payment_terms")
	private String paymentTerms;

	@Column(name = "reporting_currency")
	private String reportingCurrency;

	@Column(name = "sales_enquiry_email_id")
	private String salesEnquiryEmailId;

	private String status;

	@Column(name = "type_of_invoices")
	private String typeOfInvoices;
	
	private String annualturnover;
	
	@Column(name = "incorporationyear")
	private String incorporationyear;
	
	@Column(name = "companysize")
	private String companysize;
	
	@Column(name = "companydetails")
	private String companydetails;
	
	public String getCompanydetails() {
		return companydetails;
	}

	public void setCompanydetails(String companydetails) {
		this.companydetails = companydetails;
	}

	public String getCompanysize() {
		return companysize;
	}

	public void setCompanysize(String companysize) {
		this.companysize = companysize;
	}

	public String getIncorporationyear() {
		return incorporationyear;
	}

	public void setIncorporationyear(String incorporationyear) {
		this.incorporationyear = incorporationyear;
	}

	public String getAnnualturnover() {
		return annualturnover;
	}

	public void setAnnualturnover(String annualturnover) {
		this.annualturnover = annualturnover;
	}

	private String website;

	public PartnerDetails() {
	}

	public String getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEcmFolderId() {
		return this.ecmFolderId;
	}

	public void setEcmFolderId(String ecmFolderId) {
		this.ecmFolderId = ecmFolderId;
	}

	public Boolean getExposeToAllVendors() {
		return this.exposeToAllVendors;
	}

	public void setExposeToAllVendors(Boolean exposeToAllVendors) {
		this.exposeToAllVendors = exposeToAllVendors;
	}

	public String getFirmType() {
		return this.firmType;
	}

	public void setFirmType(String firmType) {
		this.firmType = firmType;
	}

	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getKeyProductCategories() {
		return this.keyProductCategories;
	}

	public void setKeyProductCategories(String keyProductCategories) {
		this.keyProductCategories = keyProductCategories;
	}

	public String getNatureOfBusiness() {
		return this.natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public Integer getNoOfBusinessPartners() {
		return this.noOfBusinessPartners;
	}

	public void setNoOfBusinessPartners(Integer noOfBusinessPartners) {
		this.noOfBusinessPartners = noOfBusinessPartners;
	}

	public String getNoOfInvoicesExpected() {
		return this.noOfInvoicesExpected;
	}

	public void setNoOfInvoicesExpected(String noOfInvoicesExpected) {
		this.noOfInvoicesExpected = noOfInvoicesExpected;
	}

	public Integer getNoOfPortalUsersAllowed() {
		return this.noOfPortalUsersAllowed;
	}

	public void setNoOfPortalUsersAllowed(Integer noOfPortalUsersAllowed) {
		this.noOfPortalUsersAllowed = noOfPortalUsersAllowed;
	}

	public String getOfferedServices() {
		return this.offeredServices;
	}

	public void setOfferedServices(String offeredServices) {
		this.offeredServices = offeredServices;
	}

	public String getPanNo() {
		return this.panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getPartnerType() {
		return this.partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public String getPaymentTerms() {
		return this.paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getReportingCurrency() {
		return this.reportingCurrency;
	}

	public void setReportingCurrency(String reportingCurrency) {
		this.reportingCurrency = reportingCurrency;
	}

	public String getSalesEnquiryEmailId() {
		return this.salesEnquiryEmailId;
	}

	public void setSalesEnquiryEmailId(String salesEnquiryEmailId) {
		this.salesEnquiryEmailId = salesEnquiryEmailId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeOfInvoices() {
		return this.typeOfInvoices;
	}

	public void setTypeOfInvoices(String typeOfInvoices) {
		this.typeOfInvoices = typeOfInvoices;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}