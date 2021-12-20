package com.tecnics.einvoice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the company_gstin_details database table.
 * 
 */
@Entity
@Table(name="company_gstin_details", schema = "einvoicing")
public class CompanyGstinDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="gstin")
	private String gstin;

	@Column(name="business_name")
	private String businessName;

	@Column(name="ecm_doc_id")
	private String ecmDocId;

	@Column(name="is_parent")
	private Boolean isParent;

	@Temporal(TemporalType.DATE)
	@Column(name="registration_year")
	private Date registrationYear;

	@Column(name="state_code")
	private String stateCode;

	//bi-directional many-to-one association to PartnerDetail
	//@Transient
	//@JoinColumn(name="partner_id")
	@Column(name="partner_id")
	private String partnerid;

	public CompanyGstinDetail() {
	}

	public String getGstin() {
		return this.gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getEcmDocId() {
		return this.ecmDocId;
	}

	public void setEcmDocId(String ecmDocId) {
		this.ecmDocId = ecmDocId;
	}

	public Boolean getIsParent() {
		return this.isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Date getRegistrationYear() {
		return this.registrationYear;
	}

	public void setRegistrationYear(Date registrationYear) {
		this.registrationYear = registrationYear;
	}

	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getPartnerId() {
		return partnerid;
	}

	public void setPartnerId(String partnerId) {
		this.partnerid = partnerId;
	}

	

}