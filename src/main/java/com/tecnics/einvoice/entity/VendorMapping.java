package com.tecnics.einvoice.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;



@Entity
@Table(name="vendor_mapping", schema = "einvoicing")
public class VendorMapping {

	
	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="customer_partner_id")
	private String customerPartnerId;

	@Column(length=250)
	private String description;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="mapping_id", unique=true, nullable=false)
	private Integer mappingId;
	 
	private Boolean status; 
	
	public Boolean getStatus() {
		return status;
	}
	

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY,mappedBy="vendorMapping")
	private List<VendorMappingActivity> vendorMappingActivities;


	@Column(name="vendor_partner_id")
	private String vendorPartnerId;
	
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false, insertable = false)
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public String getCustomerPartnerId() {
		return customerPartnerId;
	}

	public String getDescription() {
		return description;
	}
	public Integer getMappingId() {
		return mappingId;
	}

	public List<VendorMappingActivity> getVendorMappingActivities() {
		return vendorMappingActivities;
	}

	public String getVendorPartnerId() {
		return vendorPartnerId;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setCustomerPartnerId(String customerPartnerId) {
		this.customerPartnerId = customerPartnerId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}

	public void setVendorMappingActivities(List<VendorMappingActivity> vendorMappingActivities) {
		this.vendorMappingActivities = vendorMappingActivities;
	}

	public void setVendorPartnerId(String vendorPartnerId) {
		this.vendorPartnerId = vendorPartnerId;
	}

	@Override
	public String toString() {
		return "VendorMapping [mappingId=" + mappingId + ", createdOn=" + createdOn + ", description=" + description
				+ ", vendorPartnerId=" + vendorPartnerId + ", customerPartnerId=" + customerPartnerId
				+ ", vendorMappingActivities=" + vendorMappingActivities + "]";
	}

}
