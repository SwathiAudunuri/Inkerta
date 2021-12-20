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
@Table(name="vendor_mapping_activities", schema = "einvoicing")
public class VendorMappingActivity {
	@Column(name="action_comments", length=100)
	private String actionComments;

	@CreationTimestamp
	@Column(name = "action_on",nullable = false, updatable = false, insertable = false)
	private Timestamp actionOn;

	@Column(name="action_taken", length=10)
	private String actionTaken;

	
	@Column(name="action_by")
	private String actionBy;
	
	
	
	/**
	 * @return the actionBy
	 */
	public String getActionBy() {
		return actionBy;
	}


	/**
	 * @param actionBy the actionBy to set
	 */
	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}


	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;


	//bi-directional many-to-one association to VendorMapping
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mapping_id")
	private VendorMapping vendorMapping;


	public VendorMappingActivity() {
		super();
	}


	public VendorMappingActivity(Integer id, String actionComments, Timestamp actionOn, String actionTaken,
			VendorMapping vendorMapping) {
		super();
		this.id = id;
		this.actionComments = actionComments;
		this.actionOn = actionOn;
		this.actionTaken = actionTaken;
		this.vendorMapping = vendorMapping;
	}


	public String getActionComments() {
		return actionComments;
	}


	public Timestamp getActionOn() {
		return actionOn;
	}


	public String getActionTaken() {
		return actionTaken;
	}


	public Integer getId() {
		return id;
	}





	public void setActionComments(String actionComments) {
		this.actionComments = actionComments;
	}


	public void setActionOn(Timestamp actionOn) {
		this.actionOn = actionOn;
	}


	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public void setVendorMapping(VendorMapping vendorMapping) {
		this.vendorMapping = vendorMapping;
	}


	@Override
	public String toString() {
		return "VendorMappingActivity [id=" + id + ", actionComments=" + actionComments + ", actionOn=" + actionOn
				+ ", actionTaken=" + actionTaken + ", vendorMapping=" + vendorMapping + "]";
	}
	
	
	
}
