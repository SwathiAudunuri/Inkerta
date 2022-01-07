package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * The persistent class for the outbound_connectors_autopost database table.
 * 
 */
@Entity
@Table(name = "outbound_connectors_itemdelivery_email_mapping", schema = "einvoicing")
public class OutboundConnectorsItemDeliveryEmailMapping {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	

	@Column(name = "connector_id")
	private Integer connectorId;
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Integer getConnectorId() {
		return connectorId;
	}


	public void setConnectorId(Integer connectorId) {
		this.connectorId = connectorId;
	}


	public String getItemType() {
		return itemType;
	}


	public void setItemType(String itemType) {
		this.itemType = itemType;
	}


	public String getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	@Column(name = "item_type")
	private String itemType;
	

	@Column(name = "email_address")
	private String emailAddress;

	

}
