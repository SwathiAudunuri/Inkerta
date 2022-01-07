package com.tecnics.einvoice.model;

import com.tecnics.einvoice.entity.OutboundConnectorsItemDeliveryEmailMapping;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.tecnics.einvoice.entity.OutboundConnectorsItemDeliveryWebserviceMapping;
import com.tecnics.einvoice.entity.OutboundConnectorsGSTIN;
import com.tecnics.einvoice.entity.OutboundConnectorsAutopost;

public class OutboundConnectorsModel {
	
	
	@Column(name = "created_on")
	@CreationTimestamp	
	private Timestamp createdOn;
	
	@Column(name = "created_by")
	private String createdBy;	
	@Column(name = "connector_id")
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
	
	private List<OutboundConnectorsGSTIN> outboundConnectorsGSTIN=null;
	private List<OutboundConnectorsItemDeliveryWebserviceMapping> outboundConnectorsItemDeliveryWebserviceMapping=null;
	private List<OutboundConnectorsItemDeliveryEmailMapping> outboundConnectorsItemDeliveryEmailMapping=null;
	private OutboundConnectorsAutopost outboundConnectorsAutopost=null;
	
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

	

	

	public List<OutboundConnectorsGSTIN> getOutboundConnectorsGSTIN() {
		return outboundConnectorsGSTIN;
	}
	public void setOutboundConnectorsGSTIN(List<OutboundConnectorsGSTIN> outboundConnectorsGSTIN) {
		this.outboundConnectorsGSTIN = outboundConnectorsGSTIN;
	}
	public List<OutboundConnectorsItemDeliveryWebserviceMapping> getOutboundConnectorsItemDeliveryWebserviceMapping() {
		return outboundConnectorsItemDeliveryWebserviceMapping;
	}
	public void setOutboundConnectorsItemDeliveryWebserviceMapping(
			List<OutboundConnectorsItemDeliveryWebserviceMapping> outboundConnectorsItemDeliveryWebserviceMapping) {
		this.outboundConnectorsItemDeliveryWebserviceMapping = outboundConnectorsItemDeliveryWebserviceMapping;
	}
	public List<OutboundConnectorsItemDeliveryEmailMapping> getOutboundConnectorsItemDeliveryEmailMapping() {
		return outboundConnectorsItemDeliveryEmailMapping;
	}
	public void setOutboundConnectorsItemDeliveryEmailMapping(
			List<OutboundConnectorsItemDeliveryEmailMapping> outboundConnectorsItemDeliveryEmailMapping) {
		this.outboundConnectorsItemDeliveryEmailMapping = outboundConnectorsItemDeliveryEmailMapping;
	}
	public OutboundConnectorsAutopost getOutboundConnectorsAutopost() {
		return outboundConnectorsAutopost;
	}
	public void setOutboundConnectorsAutopost(OutboundConnectorsAutopost outboundConnectorsAutopost) {
		this.outboundConnectorsAutopost = outboundConnectorsAutopost;
	}

	

}
