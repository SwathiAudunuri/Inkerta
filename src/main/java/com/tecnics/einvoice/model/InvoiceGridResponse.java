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
	private String supply_type;
	private String document_ref_id;
	private String vendor_partner_id;
	private String customer_partner_id;
	private String company_name;
	private String ponumber;
	
	public String getPonumber() {
		return ponumber;
	}
	
	public void setPonumber(String ponumber) {
		this.ponumber = ponumber;
	}
	
	
	public String getCompany_name() {
		return company_name;
	}
	public String getVendor_partner_id() {
		return vendor_partner_id;
	}
	public String getCustomer_partner_id() {
		return customer_partner_id;
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
	public String getSupply_type() {
		return supply_type;
	}
	public BigDecimal getTotal_invoice_value() {
		return total_invoice_value;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public void setVendor_partner_id(String vendor_partner_id) {
		this.vendor_partner_id = vendor_partner_id;
	}
	public void setCustomer_partner_id(String customer_partner_id) {
		this.customer_partner_id = customer_partner_id;
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
	public void setSupply_type(String supply_type) {
		this.supply_type = supply_type;
	}
	public void setTotal_invoice_value(BigDecimal total_invoice_value) {
		this.total_invoice_value = total_invoice_value;
	}
	
	
	
	@Override
	public String toString() {
		return "InvoiceGridResponse [invoice_status=" + invoice_status + ", total_invoice_value=" + total_invoice_value
				+ ", invoicedate=" + invoicedate + ", invoicenum=" + invoicenum + ", status=" + status
				+ ", supply_type=" + supply_type + "]";
	}

	
}
