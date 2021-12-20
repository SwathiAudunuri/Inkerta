package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the invoice_log database table.
 * 
 */
@Entity
@Table(name="invoice_log", schema = "einvoicing")
public class InvoiceLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="log_id")
	private Integer logId;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="log_origination")
	private String logOrigination;

	@Column(name="log_type")
	private String logType;

	@Column(name="ref_id")
	private String refId;

	private String status;

	
	public InvoiceLog() {
	}

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getLogOrigination() {
		return this.logOrigination;
	}

	public void setLogOrigination(String logOrigination) {
		this.logOrigination = logOrigination;
	}

	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getRefId() {
		return this.refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
/**
 * 
 * @param logOrigination
 * @param logType
 * @param refId
 * @param status
 */
	public InvoiceLog( String logOrigination, String logType, String refId,
			String status) {
		super();
		this.logId = logId;
		this.createdDate = createdDate;
		this.logOrigination = logOrigination;
		this.logType = logType;
		this.refId = refId;
		this.status = status;
	}

	
	


	
}