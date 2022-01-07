package com.tecnics.einvoice.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class InvoiceGridResponse {

	
	private String invoice_status;
	private BigDecimal total_invoice_value;
	private Date invoicedate;
	private Date invoiceduedate;
	private int creditdays;
	private String invoicenum;
	private String status;
	private String document_ref_id;

	private String company_name;

	private String assigned_to;
	private String invoice_currency_code;
	private boolean is_external;

	public boolean getIs_external() {
		return is_external;
	}

	public void setIs_external(boolean is_external) {
		this.is_external = is_external;
	}

	public String getInvoice_currency_code() {
		return invoice_currency_code;
	}

	public void setInvoice_currency_code(String invoice_currency_code) {
		this.invoice_currency_code = invoice_currency_code;
	}

	public String getAssigned_to() {
		return assigned_to;
	}

	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}


	
	public String getCompany_name() {
		return company_name;
	}

	public String getDocument_ref_id() {
		return document_ref_id;
	}
	public String getInvoice_status() {
		return invoice_status;
	}

	public Date getInvoicedate() {
		return invoicedate;
	}
	public String getInvoicenum() {
		return invoicenum;
	}
	public String getStatus() {
		return status;
	}

	public BigDecimal getTotal_invoice_value() {
		return total_invoice_value;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public void setDocument_ref_id(String document_ref_id) {
		
		this.document_ref_id = document_ref_id;
	}
	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}
	public void setInvoicedate(Date invoicedate) {
		
		this.invoicedate = invoicedate;
	}
	
	public Date getInvoiceduedate() {
		return invoiceduedate;
	}
	public void setInvoiceduedate(Date invoiceduedate) {
		
	this.invoiceduedate=invoiceduedate;
	/*	 Calendar cal = Calendar.getInstance();
	        cal.setTime(invoiceduedate);
	        cal.add(Calendar.DATE, this.creditdays); //minus number would decrement the days
	        this.invoiceduedate= cal.getTime();*/
		
		
	    
	}
	
	public int getCreditdays() {
		return creditdays;
	}
	public void setCreditdays(int creditdays) {

		this.creditdays = creditdays;
		//setInvoiceduedate(this.invoicedate);
	
	}

	
	public void setInvoicenum(String invoicenum) {
		this.invoicenum = invoicenum;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public void setTotal_invoice_value(BigDecimal total_invoice_value) {
		this.total_invoice_value = total_invoice_value;
	}
	
	
	
	@Override
	public String toString() {
		return "InvoiceGridResponse [invoice_status=" + invoice_status + ", total_invoice_value=" + total_invoice_value
				+ ", invoicedate=" + invoicedate + ", invoicenum=" + invoicenum + ", status=" + status +"]";
	}

	
}
