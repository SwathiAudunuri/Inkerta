package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the recipient_mapping database table.
 * 
 */
@Entity
@Table(name = "recipient_mapping", schema = "einvoicing")
public class RecipientMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "delivery_mode")
	private String deliveryMode;

	private String description;

	@Column(name = "is_active")
	private Boolean isActive;

	//
//	//bi-directional many-to-one association to InvoiceDetail
//	@OneToMany(fetch = FetchType.LAZY,mappedBy="recipientMapping1")
//	private List<InvoiceDetail> invoiceDetails1;
//
//	//bi-directional many-to-one association to InvoiceDetail
//	@OneToMany(fetch = FetchType.LAZY,mappedBy="recipientMapping2")
//	private List<InvoiceDetail> invoiceDetails2;
//
	// bi-directional many-to-one association to RecipientActivity
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipientMapping")
	private List<RecipientActivity> recipientActivities;

	//
//	//bi-directional many-to-one association to RecipientEmailMapping
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipientMapping")
	private List<RecipientEmailMapping> recipientEmailMappings;
//
//	//bi-directional many-to-one association to RecipientFtpMapping
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipientMapping")
	private List<RecipientFtpMapping> recipientFtpMappings;
//
//	//bi-directional many-to-one association to RecipientGstinMapping
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipientMapping")
	private List<RecipientGstinMapping> recipientGstinMappings;
//
	// //bi-directional many-to-many association to UserManagement
	// @ManyToMany(fetch = FetchType.LAZY,mappedBy="recipientMappings")
	// private List<UserManagement> userManagements;
	//
	// //bi-directional many-to-one association to UserRecipientMapping
	// @OneToMany(fetch = FetchType.LAZY,mappedBy="recipientMapping")
	// private List<UserRecipientMapping> userRecipientMappings;

	@Id
	@Column(name = "recipient_id")
	private String recipientId;

	@Column(name = "recipient_tag")
	private String recipientTag;
//
//	//bi-directional many-to-one association to PartnerDetail
//	@ManyToOne
//	@JoinColumn(name="partner_id")
//	private PartnerDetail partnerDetail;
//
//	//bi-directional many-to-one association to RecipientWebserviceMapping
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipientMapping")
	private List<RecipientWebserviceMapping> recipientWebserviceMappings;

	@Column(name = "delivery_mechanism")
	private String deliveryMechanism;

	@Column(name = "address1")
	private String address1;

	@Column(name = "address2")
	private String address2;

	@Column(name = "pincode")
	private Integer pincode;

	@Column(name = "state")
	private String state;

	public RecipientMapping() {
	}

	public RecipientMapping(String recipientId, String deliveryMode, String description, Boolean isActive,
			String recipientTag, String deliveryMechanism, String address1, String address2, Integer pincode,
			String state) {
		super();
		this.recipientId = recipientId;
		this.deliveryMode = deliveryMode;
		this.description = description;
		this.isActive = isActive;
		this.recipientTag = recipientTag;
		this.deliveryMechanism = deliveryMechanism;
		this.address1 = address1;
		this.address2 = address2;
		this.pincode = pincode;
		this.state = state;

	}

	public String getDeliveryMechanism() {
		return deliveryMechanism;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public Integer getPincode() {
		return pincode;
	}

	public String getState() {
		return state;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public List<RecipientActivity> getRecipientActivities() {
		return recipientActivities;
	}

	public List<RecipientEmailMapping> getRecipientEmailMappings() {
		return recipientEmailMappings;
	}

	public List<RecipientFtpMapping> getRecipientFtpMappings() {
		return recipientFtpMappings;
	}

	public List<RecipientGstinMapping> getRecipientGstinMappings() {
		return recipientGstinMappings;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public String getRecipientTag() {
		return recipientTag;
	}

	public List<RecipientWebserviceMapping> getRecipientWebserviceMappings() {
		return recipientWebserviceMappings;
	}

	public void setDeliveryMechanism(String deliveryMechanism) {
		this.deliveryMechanism = deliveryMechanism;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setRecipientActivities(List<RecipientActivity> recipientActivities) {
		this.recipientActivities = recipientActivities;
	}

	public void setRecipientEmailMappings(List<RecipientEmailMapping> recipientEmailMappings) {
		this.recipientEmailMappings = recipientEmailMappings;
	}

	public void setRecipientFtpMappings(List<RecipientFtpMapping> recipientFtpMappings) {
		this.recipientFtpMappings = recipientFtpMappings;
	}

	public void setRecipientGstinMappings(List<RecipientGstinMapping> recipientGstinMappings) {
		this.recipientGstinMappings = recipientGstinMappings;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public void setRecipientTag(String recipientTag) {
		this.recipientTag = recipientTag;
	}

	public void setRecipientWebserviceMappings(List<RecipientWebserviceMapping> recipientWebserviceMappings) {
		this.recipientWebserviceMappings = recipientWebserviceMappings;
	}

	@Override
	public String toString() {
		return "RecipientMapping [deliveryMode=" + deliveryMode + ", description=" + description + ", isActive="
				+ isActive + ", recipientActivities=" + recipientActivities + ", recipientEmailMappings="
				+ recipientEmailMappings + ", recipientFtpMappings=" + recipientFtpMappings
				+ ", recipientGstinMappings=" + recipientGstinMappings + ", recipientId=" + recipientId
				+ ", recipientTag=" + recipientTag + ", recipientWebserviceMappings=" + recipientWebserviceMappings
				+ ", deliveryMechanism=" + deliveryMechanism + ", address1=" + address1 + ", address2=" + address2
				+ ", pincode=" + pincode + ", state=" + state + "]";
	}

}