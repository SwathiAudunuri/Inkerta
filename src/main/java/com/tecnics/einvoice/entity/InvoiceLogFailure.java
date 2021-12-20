package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


/**
 * The persistent class for the invoice_log_failures database table.
 * 
 */
@Entity
@Table(name="invoice_log_failures", schema = "einvoicing")
public class InvoiceLogFailure implements Serializable {
	private static final long serialVersionUID = 1L;

	@CreationTimestamp
	@Column(name = "created_date",nullable = false, updatable = false, insertable = false)
	private Timestamp createdDate;

	@Column(name="failure_message")
	private String failureMessage;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="reason_code")
	private String reasonCode;

	
	public InvoiceLogFailure() {
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getFailureMessage() {
		return this.failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReasonCode() {
		return this.reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public InvoiceLogFailure(String failureMessage, String reasonCode) {
		this.failureMessage = failureMessage;
		this.reasonCode = reasonCode;
	}

	

}