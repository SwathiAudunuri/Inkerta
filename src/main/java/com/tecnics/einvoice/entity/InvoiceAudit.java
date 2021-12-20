package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the invoice_audit database table.
 * 
 */
@Entity
@Table(name="invoice_audit", schema = "einvoicing")
@NamedQuery(name="InvoiceAudit.findAll", query="SELECT i FROM InvoiceAudit i")
public class InvoiceAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="audit_id")
	private Integer auditId;

	@Column(name="action_date")
	private Timestamp actionDate;

	@Column(name="audit_origination")
	private String auditOrigination;

	@Column(name="audit_type")
	private String auditType;
	
	@Column(name="ref_id")
	private String refId;
	
	@Column(name="action_comments")
	private String actionComments;
	
	@Column(name="action_by")
	private String actionBy;




	public InvoiceAudit() {
	}

	public Integer getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public Timestamp getActionDate() {
		return this.actionDate;
	}

	public void setActionDate(Timestamp actionDate) {
		this.actionDate = actionDate;
	}

	public String getAuditOrigination() {
		return this.auditOrigination;
	}

	public void setAuditOrigination(String auditOrigination) {
		this.auditOrigination = auditOrigination;
	}

	public String getAuditType() {
		return this.auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	
	public String getRefId() {
		return this.refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	public String getActionComments() {
		return this.actionComments;
	}

	public void setActionComments(String actionComments) {
		this.actionComments = actionComments;
	}
	
	public String getActionBy() {
		return this.actionBy;
	}

	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}


	

}