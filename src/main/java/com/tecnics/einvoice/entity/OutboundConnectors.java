package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * The persistent class for the outbound connectors database table.
 * 
 */
@Entity
@Table(name = "outbound_connectors", schema = "einvoicing")
public class OutboundConnectors {
	
	private static final long serialVersionUID = 1L;

	@CreationTimestamp
	@Column(name = "created_on", nullable = false, updatable = false, insertable = false)
	private Timestamp createdOn;
	
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Id
	@Column(name = "connector_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer connectorId;

	@Column(name = "partner_id")
	private String partnerId;
	

	@Column(name = "connector_tag")
	private String connectorTag;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "invoiceupload_deliverymode")
	private String invoiceuploadDeliverymode;
	
	
	@Column(name = "invoicequery_deliverymode")
	private String invoicequeryDeliverymode;


	@Column(name = "invoicestatusupdate_deliverymode")
	private String invoicestatusupdateDeliverymode;
	

	@Column(name = "autopost_on_verification")
	private boolean autopostOnVerification;
	
	
	@Column(name = "description")
	private String description;
	

	public Timestamp getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Integer getConnectorId() {
		return connectorId;
	}


	public void setConnectorId(Integer connectorId) {
		this.connectorId = connectorId;
	}


	public String getPartnerId() {
		return partnerId;
	}


	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}


	public String getConnectorTag() {
		return connectorTag;
	}


	public void setConnectorTag(String connectorTag) {
		this.connectorTag = connectorTag;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}


	public String getInvoiceuploadDeliverymode() {
		return invoiceuploadDeliverymode;
	}


	public void setInvoiceuploadDeliverymode(String invoiceuploadDeliverymode) {
		this.invoiceuploadDeliverymode = invoiceuploadDeliverymode;
	}


	public String getInvoicequeryDeliverymode() {
		return invoicequeryDeliverymode;
	}


	public void setInvoicequeryDeliverymode(String invoicequeryDeliverymode) {
		this.invoicequeryDeliverymode = invoicequeryDeliverymode;
	}


	public String getInvoicestatusupdateDeliverymode() {
		return invoicestatusupdateDeliverymode;
	}


	public void setInvoicestatusupdateDeliverymode(String invoicestatusupdateDeliverymode) {
		this.invoicestatusupdateDeliverymode = invoicestatusupdateDeliverymode;
	}


	public boolean isAutopostOnVerification() {
		return autopostOnVerification;
	}


	public void setAutopostOnVerification(boolean autopostOnVerification) {
		this.autopostOnVerification = autopostOnVerification;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	



}

