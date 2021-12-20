
package com.tecnics.einvoice.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "partner_profile_activities", schema = "einvoicing")

public class PartnerProfileActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "activity_status")
	private String activityStatus;

	@Column(name = "activity_type")
	private String activityType;

	@Column(name = "partner_id")
	private String partnerId;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "partnerProfileActivity")
	private List<PartnerProfileTransaction> partnerProfileTransactions = new ArrayList<PartnerProfileTransaction>();

	@Column(name = "profile_json_details")
	private String profileJsonDetails;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "transaction_id")
	private Integer transactionId;

	public String getActivityStatus() {
		return this.activityStatus;
	}

	public String getActivityType() {
		return this.activityType;
	}

	public String getPartnerId() {
		return this.partnerId;
	}

	public List<PartnerProfileTransaction> getPartnerProfileTransactions() {
		return this.partnerProfileTransactions;
	}

	public String getProfileJsonDetails() {
		return profileJsonDetails;
	}

	public Integer getTransactionId() {
		return this.transactionId;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public void setPartnerProfileTransactions(List<PartnerProfileTransaction> partnerProfileTransactions) {
		this.partnerProfileTransactions = partnerProfileTransactions;
	}

	public void setProfileJsonDetails(String profileJsonDetails) {
		this.profileJsonDetails = profileJsonDetails;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "PartnerProfileActivity [transactionId=" + transactionId + ", activityStatus=" + activityStatus
				+ ", activityType=" + activityType + ", partnerId=" + partnerId + ", profileJsonDetails="
				+ profileJsonDetails + ", partnerProfileTransactions=" + partnerProfileTransactions + "]";
	}
	

}