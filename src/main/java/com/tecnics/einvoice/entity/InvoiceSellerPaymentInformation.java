package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name="invoice_seller_payment_details", schema = "einvoicing")
public class InvoiceSellerPaymentInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id; 
	private String credit_transfer;
	private BigDecimal creditdays;
	private String direct_debit;
	private String financial_institution_branch;
	private String modeofpayment;
	private BigDecimal paid_amount;
	private String payee_financial_account;
	private String payee_name;
	private BigDecimal payment_due;
	private String payment_instruction;
	private String payment_terms;
	private String document_ref_id;

	public InvoiceSellerPaymentInformation() {
		super();
	}

	public InvoiceSellerPaymentInformation(int id, String credit_transfer, BigDecimal creditdays, String direct_debit,
			String financial_institution_branch, String modeofpayment, BigDecimal paid_amount,
			String payee_financial_account, String payee_name, BigDecimal payment_due, String payment_instruction,
			String payment_terms, String document_ref_id) {
		super();
		this.id = id;
		this.credit_transfer = credit_transfer;
		this.creditdays = creditdays;
		this.direct_debit = direct_debit;
		this.financial_institution_branch = financial_institution_branch;
		this.modeofpayment = modeofpayment;
		this.paid_amount = paid_amount;
		this.payee_financial_account = payee_financial_account;
		this.payee_name = payee_name;
		this.payment_due = payment_due;
		this.payment_instruction = payment_instruction;
		this.payment_terms = payment_terms;
		this.document_ref_id = document_ref_id;
	}

	public String getCredit_transfer() {
		return credit_transfer;
	}

	public BigDecimal getCreditdays() {
		return creditdays;
	}

	public String getDirect_debit() {
		return direct_debit;
	}
	
	
	public String getDocument_ref_id() {
		return document_ref_id;
	}

	public String getFinancial_institution_branch() {
		return financial_institution_branch;
	}

	public int getId() {
		return id;
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

	public String getPayment_instruction() {
		return payment_instruction;
	}

	public String getPayment_terms() {
		return payment_terms;
	}

	public void setCredit_transfer(String credit_transfer) {
		this.credit_transfer = credit_transfer;
	}

	public void setCreditdays(BigDecimal creditdays) {
		this.creditdays = creditdays;
	}

	public void setDirect_debit(String direct_debit) {
		this.direct_debit = direct_debit;
	}

	public void setDocument_ref_id(String document_ref_id) {
		this.document_ref_id = document_ref_id;
	}

	public void setFinancial_institution_branch(String financial_institution_branch) {
		this.financial_institution_branch = financial_institution_branch;
	}

	public void setId(int id) {
		this.id = id;
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

	public void setPayment_instruction(String payment_instruction) {
		this.payment_instruction = payment_instruction;
	}

	public void setPayment_terms(String payment_terms) {
		this.payment_terms = payment_terms;
	}

	@Override
	public String toString() {
		return "InvoiceSellerPaymentInformation [id=" + id + ", credit_transfer=" + credit_transfer + ", creditdays="
				+ creditdays + ", direct_debit=" + direct_debit + ", financial_institution_branch="
				+ financial_institution_branch + ", modeofpayment=" + modeofpayment + ", paid_amount=" + paid_amount
				+ ", payee_financial_account=" + payee_financial_account + ", payee_name=" + payee_name
				+ ", payment_due=" + payment_due + ", payment_instruction=" + payment_instruction + ", payment_terms="
				+ payment_terms + ", document_ref_id=" + document_ref_id + "]";
	}


}