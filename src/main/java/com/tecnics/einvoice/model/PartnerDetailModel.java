package com.tecnics.einvoice.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecnics.einvoice.entity.CompanyBankDetail;
import com.tecnics.einvoice.entity.CompanyGstinDetail;
import com.tecnics.einvoice.entity.CompanyMsmeDetail;
import com.tecnics.einvoice.entity.ContactDetail;
import com.tecnics.einvoice.entity.PartnerDetails;


/**
 * Model Object to map request body for Partner Registration
 */
public class PartnerDetailModel {


/**
 * Primary partner Details
 */
	private String partnerId;
	private String businessFunction;
	private String companyName;
	private String country;
	private Date establishmentYear;
	private Boolean exposeToAllVendors;
	private String firmType;
	private String natureOfBusiness;
	private String noOfInvoiceExpected;
	private Integer noOfPortalUsersAllowed;
	private String offeredServices;
	private String panNo;
	private String partnerType;
	private String status;
	private String webSite;

	@JsonIgnore
	private String folderID;
	@JsonIgnore
	private Results results;
	@JsonIgnore
	private List<GSTinDocuments> gstinDocuments;
	@JsonIgnore
	private List<MsmeDocuments> msmeDocuments;


	/**
	 *  partner bank Details
	 */
	private String branchAddress;
	private String bankName;
	private String accountType;
	private String payeeName;
	
	/**
	 *  partner gstin Details
	 */
	private String gstin;
	private String businessName;
	private String gstinCertId;
	private boolean isParent;
	private Date registrationYear;
	private String stateCode;
	
	 // partner msme Details
	private String msmeRegNo;
	private String additionalInfo;
	private String msmeDocId;
	private Date msmeRegDate;
	private String msmeType;	

	
	//  partner contact Details
	private String address;
	private String city;
	private String email;
	private String personCountry;
	private Long mobileNumber;
	private String personName;
	private String pinCode;
	private String state;
	private String stdCodePhoneNumber;
	
	
	@JsonIgnore
	private String invDescription;
	public String getInvDescription() {
		return invDescription;
	}


	public void setInvDescription(String invDescription) {
		this.invDescription = invDescription;
	}


	public String getNoOfInvoiceExpected() {
		return noOfInvoiceExpected;
	}


	public void setNoOfInvoiceExpected(String noOfInvoiceExpected) {
		this.noOfInvoiceExpected = noOfInvoiceExpected;
	}


	public Long getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public String getPersonName() {
		return personName;
	}


	public void setPersonName(String personName) {
		this.personName = personName;
	}


	public String getPinCode() {
		return pinCode;
	}


	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getStdCodePhoneNumber() {
		return stdCodePhoneNumber;
	}


	public void setStdCodePhoneNumber(String stdCodePhoneNumber) {
		this.stdCodePhoneNumber = stdCodePhoneNumber;
	}
	public List<GSTinDocuments> getGstinDocuments() {
		return gstinDocuments;
	}
	

	public void setGstinDocuments(List<GSTinDocuments> gstinDocuments) {
		this.gstinDocuments = gstinDocuments;
	}

	public Results getResults() {
		return results;
	}


	public void setResults(Results results) {
		this.results = results;
	}


	
	
	public PartnerDetailModel() {
		super();
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
		return noOfInvoiceExpected;
	}
	public void setNoOfInvoicesExpected(String noOfInvoicesExpected) {
		this.noOfInvoiceExpected = noOfInvoicesExpected;
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
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getGstin() {
		return gstin;
	}
	public void setGstin(String gstin) {
		this.gstin = gstin;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getGstinCertId() {
		return gstinCertId;
	}


	public void setGstinCertId(String gstinCertId) {
		this.gstinCertId = gstinCertId;
	}


	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	public Date getRegistrationYear() {
		return registrationYear;
	}
	public void setRegistrationYear(Date registrationYear) {
		this.registrationYear = registrationYear;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getMsmeRegNo() {
		return msmeRegNo;
	}
	public void setMsmeRegNo(String msmeRegNo) {
		this.msmeRegNo = msmeRegNo;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getMsmeDocId() {
		return msmeDocId;
	}
	public void setMsmeDocId(String msmeDocId) {
		this.msmeDocId = msmeDocId;
	}
	public Date getMsmeRegDate() {
		return msmeRegDate;
	}
	public void setMsmeRegDate(Date msmeRegDate) {
		this.msmeRegDate = msmeRegDate;
	}
	public String getMsmeType() {
		return msmeType;
	}
	public void setMsmeType(String msmeType) {
		this.msmeType = msmeType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	


	


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFolderID() {
		return folderID;
	}


	public void setFolderID(String folderID) {
		this.folderID = folderID;
	}


	public List<MsmeDocuments> getMsmeDocuments() {
		return msmeDocuments;
	}


	public void setMsmeDocuments(List<MsmeDocuments> msmeDocuments) {
		this.msmeDocuments = msmeDocuments;
	}


	public String getPersonCountry() {
		return personCountry;
	}


	public void setPersonCountry(String personCountry) {
		this.personCountry = personCountry;
	}
	
	/**
	 * Method returns the Contactdetail Object with values set from request 
	 * @return
	 */
	public ContactDetail getContactDetail() {
		ContactDetail contactDetail = new ContactDetail();
		contactDetail.setAddress(this.address);
		contactDetail.setCity(this.city);
		contactDetail.setCountry(this.personCountry);
		contactDetail.setPersonName(this.personName);
		contactDetail.setEmail(this.email);
		contactDetail.setState(this.state);
		//contactDetail.setPincode(this.pinCode);
		contactDetail.setStdcodePhoneno(this.stdCodePhoneNumber);
		contactDetail.setPrimaryPhone(this.mobileNumber+"");
		//contactDetail.setPartnerDetail(getParnerDetail());
		return contactDetail;
	}
	
	/**
	 * Method returns the PartnerDetail Object with values set from request 
	 * @return
	 */
	public PartnerDetails getParnerDetail() {
		PartnerDetails partnerDetail = new PartnerDetails();
		partnerDetail.setPartnerId(this.partnerId);
		//partnerDetail.setBusinessFunction(this.businessFunction);
		partnerDetail.setCompanyName(this.companyName);
		partnerDetail.setCountry(this.country);
		//partnerDetail.setEstablishmentYear(this.establishmentYear);
		partnerDetail.setExposeToAllVendors(this.exposeToAllVendors);
		partnerDetail.setFirmType(this.firmType);
		partnerDetail.setNatureOfBusiness(this.natureOfBusiness);
		partnerDetail.setNoOfInvoicesExpected(this.noOfInvoiceExpected);
		partnerDetail.setNoOfPortalUsersAllowed(this.getNoOfPortalUsersAllowed());
		partnerDetail.setOfferedServices(this.offeredServices);
		partnerDetail.setPanNo(this.panNo);
		partnerDetail.setPartnerType(this.partnerType);
		partnerDetail.setStatus(this.status);
		partnerDetail.setWebsite(this.webSite);
		return partnerDetail;
		
	}
	
	/**
	 * Method returns the CompanyBankDetail Object with values set from request 
	 * @return
	 */
	public CompanyBankDetail getcompanyBankDetail() {
		CompanyBankDetail companyBankDetail = new CompanyBankDetail();
		companyBankDetail.setAccountType(this.accountType);
		companyBankDetail.setBankName(this.bankName);
		companyBankDetail.setBranchAddress(this.branchAddress);
		companyBankDetail.setPayeeName(this.payeeName);
		return companyBankDetail;
	}
	
	/**
	 * Method returns the CompanyGstinDetail Object with values set from request 
	 * @return
	 */
	public CompanyGstinDetail getcompanyGSTNDetail() {
		CompanyGstinDetail companyGstinDetail= new CompanyGstinDetail();
		companyGstinDetail.setBusinessName(this.businessName);
		companyGstinDetail.setGstin(this.gstin);
		//companyGstinDetail.setGstinCertDocId(this.gstinCertId);
		companyGstinDetail.setIsParent(this.isParent);
		companyGstinDetail.setRegistrationYear(this.registrationYear);
		companyGstinDetail.setStateCode(this.stateCode);
		return companyGstinDetail;
	}
	/**
	 * Method returns the CompanyMsmeDetail Object with values set from request 
	 * @return
	 */
	public CompanyMsmeDetail getcompanyMSMEDetail() {
		CompanyMsmeDetail companyMsmeDetail = new CompanyMsmeDetail();
		companyMsmeDetail.setAdditionalInfo(this.additionalInfo);
		companyMsmeDetail.setMsmeDocId(msmeDocId);
		companyMsmeDetail.setMsmeRegDate(msmeRegDate);
		companyMsmeDetail.setMsmeRegNo(msmeRegNo);
		companyMsmeDetail.setMsmeType(msmeType);
		return companyMsmeDetail;
	}


	
	
	@Override
	public String toString() {
		return "PartnerDetailModel [partnerId=" + partnerId + ", businessFunction=" + businessFunction
				+ ", companyName=" + companyName + ", country=" + country + ", establishmentYear=" + establishmentYear
				+ ", exposeToAllVendors=" + exposeToAllVendors + ", firmType=" + firmType + ", natureOfBusiness="
				+ natureOfBusiness + ", noOfInvoiceExpected=" + noOfInvoiceExpected + ", noOfPortalUsersAllowed="
				+ noOfPortalUsersAllowed + ", offeredServices=" + offeredServices + ", panNo=" + panNo
				+ ", partnerType=" + partnerType + ", status=" + status + ", webSite=" + webSite + ", branchAddress="
				+ branchAddress + ", bankName=" + bankName + ", accountType=" + accountType + ", payeeName=" + payeeName
				+ ", gstin=" + gstin + ", businessName=" + businessName + ", gstinCertId=" + gstinCertId
				+ ", isParent=" + isParent + ", registrationYear=" + registrationYear + ", stateCode=" + stateCode
				+ ", msmeRegNo=" + msmeRegNo + ", additionalInfo=" + additionalInfo + ", msmeDocId=" + msmeDocId
				+ ", msmeRegDate=" + msmeRegDate + ", msmeType=" + msmeType + ", address=" + address + ", city=" + city
				+ ", email=" + email + ", personCountry=" + personCountry + ", mobileNumber=" + mobileNumber
				+ ", personName=" + personName + ", pinCode=" + pinCode + ", state=" + state + ", stdCodePhoneNumber="
				+ stdCodePhoneNumber + "]";
	}
	
}
