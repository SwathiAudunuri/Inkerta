package com.tecnics.einvoice.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.util.List;

/**
 * The persistent class for the enquiries database table.
 * 
 */
@Entity
@Table(name = "enquiries", schema = "einvoicing")
public class Enquiry implements Serializable {

	public Enquiry() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "enq_ref_id")
	private Integer enqRefId;

	@Column(name = "contact_no")
	private String contactNo;

	private String email;

	private String message;

	private String name;

	private String status;
	
	@CreationTimestamp
	@Column(name = "created_date",nullable = false, updatable = false, insertable = false)
	private Timestamp created_date;

	@Column(name = "partner_type")
	private String partnerType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "enquiry")
	private List<EnquiryActivity> enquiryActivities;

	public String getContactNo() {
		return this.contactNo;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public String getEmail() {
		return this.email;
	}

	public Integer getEnqRefId() {
		return this.enqRefId;
	}

	public List<EnquiryActivity> getEnquiryActivities() {
		return this.enquiryActivities;
	}

	public String getMessage() {
		return this.message;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * @return the partnerType
	 */
	public String getPartnerType() {
		return partnerType;
	}

	public String getStatus() {
		return this.status;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnqRefId(Integer enqRefId) {
		this.enqRefId = enqRefId;
	}

	public void setEnquiryActivities(List<EnquiryActivity> enquiryActivities) {
		this.enquiryActivities = enquiryActivities;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param partnerType the partnerType to set
	 */
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Enquiry [enqRefId=" + enqRefId + ", contactNo=" + contactNo + ", email=" + email + ", message="
				+ message + ", name=" + name + ", status=" + status + ", created_date=" + created_date
				+ ", partnerType=" + partnerType + ", enquiryActivities=" + enquiryActivities + "]";
	}

}