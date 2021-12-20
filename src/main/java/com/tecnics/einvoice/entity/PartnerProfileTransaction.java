package com.tecnics.einvoice.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "partner_profile_transactions", schema = "einvoicing")
public class PartnerProfileTransaction {

	@Column(name = "action_by")
	private String actionBy;

	@Column(name = "action_comments")
	private String actionComments;

	@CreationTimestamp
	@Column(name = "action_on",nullable = false, updatable = false, insertable = false)
	private Timestamp actionOn;

	@Column(name = "action_taken")
	private String actionTaken;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @JoinColumn(name = "transaction_id")
	@ManyToOne( fetch = FetchType.LAZY)
	PartnerProfileActivity partnerProfileActivity;

	public PartnerProfileTransaction() {
	}

	public String getActionBy() {
		return this.actionBy;
	}



	public String getActionComments() {
		return this.actionComments;
	}



	public String getActionTaken() {
		return this.actionTaken;
	}

	public Integer getId() {
		return this.id;
	}

	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}



	public void setActionComments(String actionComments) {
		this.actionComments = actionComments;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public void setPartnerProfileActivity(PartnerProfileActivity partnerProfileActivity) {
		this.partnerProfileActivity = partnerProfileActivity;
	}

	public Timestamp getActionOn() {
		return actionOn;
	}

	public void setActionOn(Timestamp actionOn) {
		this.actionOn = actionOn;
	}

	@Override
	public String toString() {
		return "PartnerProfileTransaction [actionBy=" + actionBy + ", actionComments=" + actionComments + ", actionOn="
				+ actionOn + ", actionTaken=" + actionTaken + ", id=" + id + ", partnerProfileActivity="
				+ partnerProfileActivity + "]";
	}



}
