package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.sql.Timestamp;

/**
 * The persistent class for the partner_invitation_details database table.
 * 
 */
@Entity
@Table(name = "partner_invitation_details", schema = "einvoicing")
public class PartnerInvitationDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name = "expiry_date")
	private Date expiryDate;

	@Column(name = "inv_description")
	private String invDescription;

	@Column(name = "inv_link")
	private String invLink;
	
	
	
	@Transient
	private String folderID;

//	@Column(name = "inv_req_raised_by")
//	private String invReqRaisedBy;

	public String getFolderID() {
		return folderID;
	}

	public void setFolderID(String folderID) {
		this.folderID = folderID;
	}
	@CreationTimestamp
	@Column(name = "inv_req_raised_on",nullable = false, updatable = false, insertable = false)
	private Timestamp invReqRaisedOn;

	//@Column(name = "inv_sent_by")
	//private String invSentBy;

	@Column(name = "inv_sent_on")
	private Timestamp invSentOn;

	@Column(name = "last_updated_on")
	private Timestamp lastUpdatedOn;

	@Column(name = "partner_company_name")
	private String companyName;

	@Column(name = "partner_contact_email")
	private String email;

	@Column(name = "partner_contact_mobile_no")
	private Long mobileNumber;

	@Column(name = "partner_contact_person_name")
	private String personName;

	@Column(name = "partner_firm_type")
	private String firmType;

	@Id
	@Column(name = "partner_id")
	private String partnerId;

	@Column(name = "partner_type")
	private String partnerType;

	@Column(name = "reg_id")
	private String regId;

	private String status;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "inv_req_raised_by")
	private UserManagement userManagement1;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "inv_sent_by")
	private UserManagement userManagement2;

	public PartnerInvitationDetail() {
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public String getInvDescription() {
		return this.invDescription;
	}

	public String getInvLink() {
		return this.invLink;
	}



	public Timestamp getInvReqRaisedOn() {
		return this.invReqRaisedOn;
	}

	

	public Timestamp getInvSentOn() {
		return this.invSentOn;
	}

	public Timestamp getLastUpdatedOn() {
		return this.lastUpdatedOn;
	}



	



	public String getPartnerId() {
		return this.partnerId;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public String getRegId() {
		return this.regId;
	}

	public String getStatus() {
		return this.status;
	}



	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setInvDescription(String invDescription) {
		this.invDescription = invDescription;
	}

	public void setInvLink(String invLink) {
		this.invLink = invLink;
	}

	public void setInvReqRaisedOn(Timestamp invReqRaisedOn) {
		this.invReqRaisedOn = invReqRaisedOn;
	}



	

	public void setInvSentOn(Timestamp invSentOn) {
		this.invSentOn = invSentOn;
	}

	public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}


	

	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getFirmType() {
		return firmType;
	}

	public void setFirmType(String firmType) {
		this.firmType = firmType;
	}


	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUserManagement1(UserManagement userManagement1) {
		this.userManagement1 = userManagement1;
	}

	public void setUserManagement2(UserManagement userManagement2) {
		this.userManagement2 = userManagement2;
	}

	


	@Override
	public String toString() {
		return "PartnerInvitationDetail [expiryDate=" + expiryDate + ", invDescription=" + invDescription + ", invLink="
				+ invLink + ", folderID=" + folderID + ", invReqRaisedOn=" + invReqRaisedOn + ", invSentOn=" + invSentOn
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyName=" + companyName + ", contactEmail=" + email
				+ ", mobileNumber=" + mobileNumber + ", personName=" + personName + ", firmType=" + firmType
				+ ", partnerId=" + partnerId + ", partnerType=" + partnerType + ", regId=" + regId + ", status="
				+ status + ", userManagement1=" + userManagement1 + ", userManagement2=" + userManagement2 + "]";
	}

}