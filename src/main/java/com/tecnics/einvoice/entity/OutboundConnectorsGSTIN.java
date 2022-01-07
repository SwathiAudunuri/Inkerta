
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
@Table(name = "outbound_connectors_gstin", schema = "einvoicing")
public class OutboundConnectorsGSTIN {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "gstin_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String gstinId;
	

	@Column(name = "connector_id")
	private Integer connectorId;
	

	@Column(name = "gstin")
	private String gstin;
	

	public String getGstinId() {
		return gstinId;
	}


	public void setGstinId(String gstinId) {
		this.gstinId = gstinId;
	}


	public Integer getConnectorId() {
		return connectorId;
	}


	public void setConnectorId(Integer connectorId) {
		this.connectorId = connectorId;
	}


	public String getGstin() {
		return gstin;
	}


	public void setGstin(String gstin) {
		this.gstin = gstin;
	}


	
}

