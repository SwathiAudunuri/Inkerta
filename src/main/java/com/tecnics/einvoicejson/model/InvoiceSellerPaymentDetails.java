package com.tecnics.einvoicejson.model;
public class InvoiceSellerPaymentDetails {
	public String payee_name;
	 public String payee_financial_account;
	 public String modeofpayment;
	 public String financial_institution_branch;
	 public String payment_terms;
	 public String payment_instruction;
	 public String credit_transfer;
	 public String direct_debit;
	 public int creditdays;
	 public int paid_amount;
	 public int payment_due;
	public String getPayee_name() {
		return payee_name;
	}
	public void setPayee_name(String payee_name) {
		this.payee_name = payee_name;
	}
	public String getPayee_financial_account() {
		return payee_financial_account;
	}
	public void setPayee_financial_account(String payee_financial_account) {
		this.payee_financial_account = payee_financial_account;
	}
	public String getModeofpayment() {
		return modeofpayment;
	}
	public void setModeofpayment(String modeofpayment) {
		this.modeofpayment = modeofpayment;
	}
	public String getFinancial_institution_branch() {
		return financial_institution_branch;
	}
	public void setFinancial_institution_branch(String financial_institution_branch) {
		this.financial_institution_branch = financial_institution_branch;
	}
	public String getPayment_terms() {
		return payment_terms;
	}
	public void setPayment_terms(String payment_terms) {
		this.payment_terms = payment_terms;
	}
	public String getPayment_instruction() {
		return payment_instruction;
	}
	public void setPayment_instruction(String payment_instruction) {
		this.payment_instruction = payment_instruction;
	}
	public String getCredit_transfer() {
		return credit_transfer;
	}
	public void setCredit_transfer(String credit_transfer) {
		this.credit_transfer = credit_transfer;
	}
	public String getDirect_debit() {
		return direct_debit;
	}
	public void setDirect_debit(String direct_debit) {
		this.direct_debit = direct_debit;
	}
	public int getCreditdays() {
		return creditdays;
	}
	public void setCreditdays(int creditdays) {
		this.creditdays = creditdays;
	}
	public int getPaid_amount() {
		return paid_amount;
	}
	public void setPaid_amount(int paid_amount) {
		this.paid_amount = paid_amount;
	}
	public int getPayment_due() {
		return payment_due;
	}
	public void setPayment_due(int payment_due) {
		this.payment_due = payment_due;
	}
	@Override
	public String toString() {
		return "InvoiceSellerPaymentDetails [payee_name=" + payee_name + ", payee_financial_account="
				+ payee_financial_account + ", modeofpayment=" + modeofpayment + ", financial_institution_branch="
				+ financial_institution_branch + ", payment_terms=" + payment_terms + ", payment_instruction="
				+ payment_instruction + ", credit_transfer=" + credit_transfer + ", direct_debit=" + direct_debit
				+ ", creditdays=" + creditdays + ", paid_amount=" + paid_amount + ", payment_due=" + payment_due + "]";
	}
	public InvoiceSellerPaymentDetails(String payee_name, String payee_financial_account, String modeofpayment,
			String financial_institution_branch, String payment_terms, String payment_instruction,
			String credit_transfer, String direct_debit, int creditdays, int paid_amount, int payment_due) {
		super();
		this.payee_name = payee_name;
		this.payee_financial_account = payee_financial_account;
		this.modeofpayment = modeofpayment;
		this.financial_institution_branch = financial_institution_branch;
		this.payment_terms = payment_terms;
		this.payment_instruction = payment_instruction;
		this.credit_transfer = credit_transfer;
		this.direct_debit = direct_debit;
		this.creditdays = creditdays;
		this.paid_amount = paid_amount;
		this.payment_due = payment_due;
	}
	public InvoiceSellerPaymentDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
}
