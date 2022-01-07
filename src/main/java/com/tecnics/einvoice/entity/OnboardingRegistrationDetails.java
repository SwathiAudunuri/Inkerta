package com.tecnics.einvoice.entity;


import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the onboarding_registration_details database table.
 * 
 */
@Entity
@Table(name="onboarding_registration_details", schema = "einvoicing")

public class OnboardingRegistrationDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer registrationid;


	private String annualturnover;

	private String averagedailyinvoices;

	private Boolean becomeinkreta;

	private String blockname;

	private String buildingnumber;

	private Boolean canenableadditionaldetailsnext;

	private Boolean canenablecontactnext;

	private String city;

	private String companydetails;

	private String companyname;

	private String companysize;

	private String companywebsite;

	private String ctb;

	private String ctj;

	private String ctjcd;

	private String cxdt;

	private String district;

	private String dty;

	private String email;

	private String enquiryemailid;

	private String firmtype;

	private String floornumber;

	private String frequencytype;

	private String gstcertificate;

	private String gstcertificatename;

	private String gstin;

	private String gstincertificatemimetype;

	private String incorporationyear;

	private String industriesserviced;

	private Timestamp initiateddate;

	private String keyproductcategories;

	private String lastname;

	private Timestamp lastupdateon;

	private String lg;

	private String lineofbusiness;

	private String location;

	private String lt;

	private String mobilenumber;

	private String msmecategories;

	private Boolean msmeregistered;

	private Timestamp msmeregistrationdate;

	@Transient
	private String[] natureofbusiness_arr;
	
	
	public String[] getNatureofbusiness_arr() {
		return natureofbusiness_arr;
	}

	public void setNatureofbusiness_arr(String[] natureofbusiness_arr) {
		this.natureofbusiness_arr = natureofbusiness_arr;
	}
	
	@JsonSerialize
	@JsonDeserialize
	@Transient
	private List<AttachmentDetails> attachmentDetails;

	public List<AttachmentDetails> getAttachmentDetails() {
		return attachmentDetails;
	}

	public void setAttachmentDetails(List<AttachmentDetails> attachmentDetails) {
		this.attachmentDetails = attachmentDetails;
	}

	private String natureofbusiness;

	private String onboardingstatus;


	private String pincode;



	private String reportingcurrency;

	private String standardpaymentterms;

	private String statecode;

	private String stj;

	private String stjcd;

	private String street;

	private String tradename;

	private Boolean validenquiryemailid;

	private Boolean verifiedmail;

	private Boolean verifiedphone;
	
	private String rgdt;
	private String sts;
	private String lstupdt;
	private String pan;
	
	private String firstname;
	private String partnertype;
	
	private Boolean isbusinesspartner;
	
	private String adminusername;
	private String adminpassword;
	

	public String getAdminusername() {
		return adminusername;
	}

	public void setAdminusername(String adminusername) {
		this.adminusername = adminusername;
	}

	public String getAdminpassword() {
		return adminpassword;
	}

	public void setAdminpassword(String adminpassword) {
		this.adminpassword = adminpassword;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPartnertype() {
		return partnertype;
	}

	public void setPartnertype(String partnertype) {
		this.partnertype = partnertype;
	}

	public Boolean getIsbusinesspartner() {
		return isbusinesspartner;
	}

	public void setIsbusinesspartner(Boolean isbusinesspartner) {
		this.isbusinesspartner = isbusinesspartner;
	}

	public String getRgdt() {
		return rgdt;
	}

	public void setRgdt(String rgdt) {
		this.rgdt = rgdt;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getLstupdt() {
		return lstupdt;
	}

	public void setLstupdt(String lstupdt) {
		this.lstupdt = lstupdt;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public OnboardingRegistrationDetails() {
	}

	public String getAnnualturnover() {
		return this.annualturnover;
	}

	public void setAnnualturnover(String annualturnover) {
		this.annualturnover = annualturnover;
	}

	public String getAveragedailyinvoices() {
		return this.averagedailyinvoices;
	}

	public void setAveragedailyinvoices(String averagedailyinvoices) {
		this.averagedailyinvoices = averagedailyinvoices;
	}

	public Boolean getBecomeinkreta() {
		return this.becomeinkreta;
	}

	public void setBecomeinkreta(Boolean becomeinkreta) {
		this.becomeinkreta = becomeinkreta;
	}

	public String getBlockname() {
		return this.blockname;
	}

	public void setBlockname(String blockname) {
		this.blockname = blockname;
	}

	public String getBuildingnumber() {
		return this.buildingnumber;
	}

	public void setBuildingnumber(String buildingnumber) {
		this.buildingnumber = buildingnumber;
	}

	public Boolean getCanenableadditionaldetailsnext() {
		return this.canenableadditionaldetailsnext;
	}

	public void setCanenableadditionaldetailsnext(Boolean canenableadditionaldetailsnext) {
		this.canenableadditionaldetailsnext = canenableadditionaldetailsnext;
	}

	public Boolean getCanenablecontactnext() {
		return this.canenablecontactnext;
	}

	public void setCanenablecontactnext(Boolean canenablecontactnext) {
		this.canenablecontactnext = canenablecontactnext;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompanydetails() {
		return this.companydetails;
	}

	public void setCompanydetails(String companydetails) {
		this.companydetails = companydetails;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanysize() {
		return this.companysize;
	}

	public void setCompanysize(String companysize) {
		this.companysize = companysize;
	}

	public String getCompanywebsite() {
		return this.companywebsite;
	}

	public void setCompanywebsite(String companywebsite) {
		this.companywebsite = companywebsite;
	}

	public String getCtb() {
		return this.ctb;
	}

	public void setCtb(String ctb) {
		this.ctb = ctb;
	}

	public String getCtj() {
		return this.ctj;
	}

	public void setCtj(String ctj) {
		this.ctj = ctj;
	}

	public String getCtjcd() {
		return this.ctjcd;
	}

	public void setCtjcd(String ctjcd) {
		this.ctjcd = ctjcd;
	}

	public String getCxdt() {
		return this.cxdt;
	}

	public void setCxdt(String cxdt) {
		this.cxdt = cxdt;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDty() {
		return this.dty;
	}

	public void setDty(String dty) {
		this.dty = dty;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnquiryemailid() {
		return this.enquiryemailid;
	}

	public void setEnquiryemailid(String enquiryemailid) {
		this.enquiryemailid = enquiryemailid;
	}

	public String getFirmtype() {
		return this.firmtype;
	}

	public void setFirmtype(String firmtype) {
		this.firmtype = firmtype;
	}

	public String getFloornumber() {
		return this.floornumber;
	}

	public void setFloornumber(String floornumber) {
		this.floornumber = floornumber;
	}

	public String getFrequencytype() {
		return this.frequencytype;
	}

	public void setFrequencytype(String frequencytype) {
		this.frequencytype = frequencytype;
	}

	public String getGstcertificate() {
		return this.gstcertificate;
	}

	public void setGstcertificate(String gstcertificate) {
		this.gstcertificate = gstcertificate;
	}

	public String getGstcertificatename() {
		return this.gstcertificatename;
	}

	public void setGstcertificatename(String gstcertificatename) {
		this.gstcertificatename = gstcertificatename;
	}

	public String getGstin() {
		return this.gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getGstincertificatemimetype() {
		return this.gstincertificatemimetype;
	}

	public void setGstincertificatemimetype(String gstincertificatemimetype) {
		this.gstincertificatemimetype = gstincertificatemimetype;
	}

	public String getIncorporationyear() {
		return this.incorporationyear;
	}

	public void setIncorporationyear(String incorporationyear) {
		this.incorporationyear = incorporationyear;
	}

	public String getIndustriesserviced() {
		return this.industriesserviced;
	}

	public void setIndustriesserviced(String industriesserviced) {
		this.industriesserviced = industriesserviced;
	}

	public Timestamp getInitiateddate() {
		return this.initiateddate;
	}

	public void setInitiateddate(Timestamp initiateddate) {
		this.initiateddate = initiateddate;
	}

	public String getKeyproductcategories() {
		return this.keyproductcategories;
	}

	public void setKeyproductcategories(String keyproductcategories) {
		this.keyproductcategories = keyproductcategories;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Timestamp getLastupdateon() {
		return this.lastupdateon;
	}

	public void setLastupdateon(Timestamp lastupdateon) {
		this.lastupdateon = lastupdateon;
	}

	public String getLg() {
		return this.lg;
	}

	public void setLg(String lg) {
		this.lg = lg;
	}

	public String getLineofbusiness() {
		return this.lineofbusiness;
	}

	public void setLineofbusiness(String lineofbusiness) {
		this.lineofbusiness = lineofbusiness;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLt() {
		return this.lt;
	}

	public void setLt(String lt) {
		this.lt = lt;
	}

	public String getMobilenumber() {
		return this.mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getMsmecategories() {
		return this.msmecategories;
	}

	public void setMsmecategories(String msmecategories) {
		this.msmecategories = msmecategories;
	}

	public Boolean getMsmeregistered() {
		return this.msmeregistered;
	}

	public void setMsmeregistered(Boolean msmeregistered) {
		this.msmeregistered = msmeregistered;
	}

	public Timestamp getMsmeregistrationdate() {
		return this.msmeregistrationdate;
	}

	public void setMsmeregistrationdate(Timestamp msmeregistrationdate) {
		this.msmeregistrationdate = msmeregistrationdate;
	}

	public String getNatureofbusiness() {
		return this.natureofbusiness;
	}

	public void setNatureofbusiness(String natureofbusiness) {
		this.natureofbusiness = natureofbusiness;
	} 

	public String getOnboardingstatus() {
		return this.onboardingstatus;
	}

	public void setOnboardingstatus(String onboardingstatus) {
		this.onboardingstatus = onboardingstatus;
	}

	

	public String getPincode() {
		return this.pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Integer getRegistrationid() {
		return this.registrationid;
	}

	public void setRegistrationid(Integer registrationid) {
		this.registrationid = registrationid;
	}

	public String getReportingcurrency() {
		return this.reportingcurrency;
	}

	public void setReportingcurrency(String reportingcurrency) {
		this.reportingcurrency = reportingcurrency;
	}

	public String getStandardpaymentterms() {
		return this.standardpaymentterms;
	}

	public void setStandardpaymentterms(String standardpaymentterms) {
		this.standardpaymentterms = standardpaymentterms;
	}

	public String getStatecode() {
		return this.statecode;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}

	public String getStj() {
		return this.stj;
	}

	public void setStj(String stj) {
		this.stj = stj;
	}

	public String getStjcd() {
		return this.stjcd;
	}

	public void setStjcd(String stjcd) {
		this.stjcd = stjcd;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTradename() {
		return this.tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public Boolean getValidenquiryemailid() {
		return this.validenquiryemailid;
	}

	public void setValidenquiryemailid(Boolean validenquiryemailid) {
		this.validenquiryemailid = validenquiryemailid;
	}

	public Boolean getVerifiedmail() {
		return this.verifiedmail;
	}

	public void setVerifiedmail(Boolean verifiedmail) {
		this.verifiedmail = verifiedmail;
	}

	public Boolean getVerifiedphone() {
		return this.verifiedphone;
	}

	public void setVerifiedphone(Boolean verifiedphone) {
		this.verifiedphone = verifiedphone;
	}

}