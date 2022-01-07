package com.tecnics.einvoice.model;

import java.io.Serializable;
import com.tecnics.einvoice.entity.PartnerDetails;


/**
 * The persistent class for the partner_profile_activities database table.
 * 
 */
public class PartnerProfileActivityModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer transactionId;

	private String activityStatus;

	private String activityType;

	private String partnerId;

	private  PartnerDetails profileJsonDetails;

	public PartnerProfileActivityModel() {
	}

	public Integer getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public String getActivityStatus() {
		return this.activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getActivityType() {
		return this.activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public PartnerDetails getProfileJsonDetails() {
		return profileJsonDetails;
	}

	public void setProfileJsonDetails(PartnerDetails profileJsonDetails) {
		this.profileJsonDetails = profileJsonDetails;
	}

	@Override
	public String toString() {
		return "PartnerProfileActivityModel [transactionId=" + transactionId + ", activityStatus=" + activityStatus
				+ ", activityType=" + activityType + ", partnerId=" + partnerId + ", profileJsonDetails="
				+ profileJsonDetails + "]";
	}



}