package com.tecnics.einvoice.entity;
import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name="invoice_status", schema = "einvoicing")
public class InvoiceStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="partner_type", length=10)
	private String partnerType;

	@Column(name="status_key", length=20)
	private String statusKey;
	
	

	@Column(name="status_value", length=20)
	private String statusValue;



	@Column(name="status_description", length=6)
	private String statusDescription;

	public InvoiceStatus() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartnerType() {
		return this.partnerType;
				}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public String getStatusKey() {
		return this.statusKey;
	}

	public void setStatusKey(String statusKey) {
		this.statusKey = statusKey;
	}

	public String getStatusValue() {
		return this.statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
	
	public String getStatusDescription() {
		return this.statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

}
