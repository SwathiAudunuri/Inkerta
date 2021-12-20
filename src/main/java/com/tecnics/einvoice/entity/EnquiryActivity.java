package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


/**
 * The persistent class for the enquiry_activities database table.
 * 
 */
@Entity
@Table(name="enquiry_activities", schema = "einvoicing")
public class EnquiryActivity implements Serializable {




	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String action;

	@Column(name="action_by")
	private String actionBy;

	@CreationTimestamp
	@Column(name = "action_on",nullable = false, updatable = false, insertable = false)
	private Timestamp actionOn;

	private String remarks;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name="enq_ref_id")
	private Enquiry enquiry;

	public EnquiryActivity() {
	}

	public String getAction() {
		return this.action;
	}

	public String getActionBy() {
		return this.actionBy;
	}

	public Timestamp getActionOn() {
		return this.actionOn;
	}

	public Integer getId() {
		return this.id;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}

	public void setActionOn(Timestamp actionOn) {
		this.actionOn = actionOn;
	}

	public void setEnquiry(Enquiry enquiry) {
		this.enquiry = enquiry;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}