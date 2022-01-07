package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
@Entity
@Table(name="invoice_paid_details", schema = "einvoicing")
public class InvoicePaidDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	private String financial_institution_branch;
	private String modeofpayment;
	private BigDecimal paid_amount;
	private String payee_financial_account;
	private String payee_name;
	private BigDecimal payment_due;
	private Timestamp created_date;
	private Timestamp paid_on;

	private String created_by;

	@Column(name="document_ref_id")
	private String documentRefId;
	@Column(name="paidby_partner_id")
	private String paidByPartnerId;
	
	@Column(name="paidto_partner_id")
	private String paidToPartnerId;
	
	private String reason_for_partial_payment;
	
	private String status;
	
	@Transient
	private String action_comments;
	
	@Column(name="is_external_invoice")
	private boolean externalInvoice;
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAction_comments() {
		return action_comments;
	}

	public void setAction_comments(String action_comments) {
		this.action_comments = action_comments;
	}

	

	public boolean isExternalInvoice() {
		return externalInvoice;
	}

	public void setExternalInvoice(boolean externalInvoice) {
		this.externalInvoice = externalInvoice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason_for_partial_payment() {
		return reason_for_partial_payment;
	}

	public void setReason_for_partial_payment(String reason_for_partial_payment) {
		this.reason_for_partial_payment = reason_for_partial_payment;
	}

	public String getPaidByPartnerId() {
		return paidByPartnerId;
	}

	public void setPaidByPartnerId(String paidByPartnerId) {
		this.paidByPartnerId = paidByPartnerId;
	}

	public String getPaidToPartnerId() {
		return paidToPartnerId;
	}

	public void setPaidToPartnerId(String paidToPartnerId) {
		this.paidToPartnerId = paidToPartnerId;
	}

	public String getDocumentRefId() {
		return documentRefId;
	}

	public void setDocumentRefId(String documentRefId) {
		this.documentRefId = documentRefId;
	}



	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public Timestamp getPaid_on() {
		return paid_on;
	}

	public void setPaid_on(Timestamp paid_on) {
		this.paid_on = paid_on;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}



	public InvoicePaidDetails() {
		super();
	}



	public String getFinancial_institution_branch() {
		return financial_institution_branch;
	}

	

	public String getModeofpayment() {
		return modeofpayment;
	}

	public BigDecimal getPaid_amount() {
		return paid_amount;
	}

	public String getPayee_financial_account() {
		return payee_financial_account;
	}

	public String getPayee_name() {
		return payee_name;
	}

	public BigDecimal getPayment_due() {
		return payment_due;
	}



	public void setFinancial_institution_branch(String financial_institution_branch) {
		this.financial_institution_branch = financial_institution_branch;
	}

	

	public void setModeofpayment(String modeofpayment) {
		this.modeofpayment = modeofpayment;
	}

	public void setPaid_amount(BigDecimal paid_amount) {
		this.paid_amount = paid_amount;
	}

	public void setPayee_financial_account(String payee_financial_account) {
		this.payee_financial_account = payee_financial_account;
	}

	public void setPayee_name(String payee_name) {
		this.payee_name = payee_name;
	}

	public void setPayment_due(BigDecimal payment_due) {
		this.payment_due = payment_due;
	}

	

	@Override
	public String toString() {
		return "InvoiceSellerPaymentInformation [id=" + id + ",  financial_institution_branch="
				+ financial_institution_branch + ", modeofpayment=" + modeofpayment + ", paid_amount=" + paid_amount
				+ ", payee_financial_account=" + payee_financial_account + ", payee_name=" + payee_name
				+ ", payment_due=" + payment_due + ", document_ref_id=" + documentRefId + "]";
	}


}