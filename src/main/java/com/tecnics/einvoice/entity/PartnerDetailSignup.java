package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the partner_details database table.
 * 
 */
@Entity
@Table(name = "partner_details", schema = "einvoicing")
public class PartnerDetailSignup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "partner_id", unique = true, nullable = false)
	private String partnerId;

	@Transient
	@Column(name = "business_function")
	private String businessFunction;

	@Column(name = "company_name")
	private String companyName;

	private String country;
	@Transient
	@Temporal(TemporalType.DATE)
	@Column(name = "establishment_year")
	private Date establishmentYear;

	@Column(name = "expose_to_all_vendors")
	private Boolean exposeToAllVendors;

	@Column(name = "firm_type")
	private String firmType;

	@Column(name = "nature_of_business")
	private String natureOfBusiness;

	@Column(name = "no_of_invoices_expected")
	private String noOfInvoicesExpected;

	@Column(name = "no_of_portal_users_allowed")
	private Integer noOfPortalUsersAllowed;
	@Column(name = "payment_terms")
	private String paymentTerms;

	@Column(name = "offered_services")
	private String offeredServices;
	@Column(name = "reporting_currency")
	private String reportingCurrency;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "partner_type")
	private String partnerType;

	private String status;

	@Column(name = "sales_enquiry_email_id")
	private String salesEnquiryEmailId;

	@Column(name = "website")
	private String webSite;
	@Column(name = "type_of_invoices")
	private String typeOfInvoices;
	@Column(name = "key_product_categories")
	private String keyProductCategories;
	
	@Column(name = "incorporationyear")
	private String incorporationyear;
	
	@Column(name = "companysize")
	private String companysize;
	
	@Column(name = "companydetails")
	private String companydetails;
	
	@Column(name = "industry")
	private String industry;
	
	
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

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

	@Column(name = "onboarded_on")
	private Date onboardedOn;
	
	public Date getOnboardedOn() {
		return onboardedOn;
	}

	public void setOnboardedOn(Date onboardedOn) {
		this.onboardedOn = onboardedOn;
	}

	private String annualturnover;

	public String getAnnualturnover() {
		return annualturnover;
	}

	public void setAnnualturnover(String annualturnover) {
		this.annualturnover = annualturnover;
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

	public String getSalesEnquiryEmailId() {
		return salesEnquiryEmailId;
	}

	public void setSalesEnquiryEmailId(String salesEnquiryEmailId) {
		this.salesEnquiryEmailId = salesEnquiryEmailId;
	}

	public String getTypeOfInvoices() {
		return typeOfInvoices;
	}

	public void setTypeOfInvoices(String typeOfInvoices) {
		this.typeOfInvoices = typeOfInvoices;
	}

	public String getKeyProductCategories() {
		return keyProductCategories;
	}

	public void setKeyProductCategories(String keyProductCategories) {
		this.keyProductCategories = keyProductCategories;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getBusinessFunction() {
		return businessFunction;
	}

	public void setBusinessFunction(String businessFunction) {
		this.businessFunction = businessFunction;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getEstablishmentYear() {
		return establishmentYear;
	}

	public void setEstablishmentYear(Date establishmentYear) {
		this.establishmentYear = establishmentYear;
	}

	public Boolean getExposeToAllVendors() {
		return exposeToAllVendors;
	}

	public void setExposeToAllVendors(Boolean exposeToAllVendors) {
		this.exposeToAllVendors = exposeToAllVendors;
	}

	public String getFirmType() {
		return firmType;
	}

	public void setFirmType(String firmType) {
		this.firmType = firmType;
	}

	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getNoOfInvoicesExpected() {
		return noOfInvoicesExpected;
	}

	public void setNoOfInvoicesExpected(String noOfInvoicesExpected) {
		this.noOfInvoicesExpected = noOfInvoicesExpected;
	}

	public Integer getNoOfPortalUsersAllowed() {
		return noOfPortalUsersAllowed;
	}

	public void setNoOfPortalUsersAllowed(Integer noOfPortalUsersAllowed) {
		this.noOfPortalUsersAllowed = noOfPortalUsersAllowed;
	}

	public String getOfferedServices() {
		return offeredServices;
	}

	public void setOfferedServices(String offeredServices) {
		this.offeredServices = offeredServices;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public PartnerDetailSignup() {
	}

}