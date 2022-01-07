package com.tecnics.einvoice.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class InvoiceMetaDataModel {
	
	private String document_ref_id;

	private BigDecimal cessvalue;

	private BigDecimal cgstvalue;
	private Timestamp created_date;

	private BigDecimal discount;

	private String dispatch_mode;

	private Timestamp dispatched_on;

	private String doc_source;

	private String doc_source_ref_id;

	private String ecm_folder_id;

	private String ecom_gstin;

	private Boolean igst_on_intra;

	private BigDecimal igstvalue;

	private String invoice_currency_code;

	private String invoice_status;

	private String invoice_type_code;

	private Date invoicedate;

	private String invoicenum;

	private String irn;

	private Boolean is_dispatched;

	private Timestamp last_updated_date;

	private BigDecimal other_charges;

	private Boolean reverse_charge;

	private BigDecimal roundoff;

	private BigDecimal sgstvalue;

	private BigDecimal statecessvalue;

	private String status;

	private String supplier_note;

	private String supply_type_code;

	private String tax_scheme;

	private BigDecimal total_assessable_value;

	private BigDecimal total_invoice_value;

	private BigDecimal total_invoice_value_fc;

	private String customer_partner_id;

	private String vendor_partner_id;

	private String customer_recipient_code;

	private String vendor_recipient_code;

	private String created_by;

	private String last_updated_by;
	
	private Integer creditdays;
	

	public Integer getCreditdays() {
		return creditdays;
	}

	public void setCreditdays(Integer creditdays) {
		this.creditdays = creditdays;
	}

	public String getDocument_ref_id() {
		return document_ref_id;
	}

	public void setDocument_ref_id(String document_ref_id) {
		this.document_ref_id = document_ref_id;
	}

	public BigDecimal getCessvalue() {
		return cessvalue;
	}

	public void setCessvalue(BigDecimal cessvalue) {
		this.cessvalue = cessvalue;
	}

	public BigDecimal getCgstvalue() {
		return cgstvalue;
	}

	public void setCgstvalue(BigDecimal cgstvalue) {
		this.cgstvalue = cgstvalue;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getDispatch_mode() {
		return dispatch_mode;
	}

	public void setDispatch_mode(String dispatch_mode) {
		this.dispatch_mode = dispatch_mode;
	}

	public Timestamp getDispatched_on() {
		return dispatched_on;
	}

	public void setDispatched_on(Timestamp dispatched_on) {
		this.dispatched_on = dispatched_on;
	}

	public String getDoc_source() {
		return doc_source;
	}

	public void setDoc_source(String doc_source) {
		this.doc_source = doc_source;
	}

	public String getDoc_source_ref_id() {
		return doc_source_ref_id;
	}

	public void setDoc_source_ref_id(String doc_source_ref_id) {
		this.doc_source_ref_id = doc_source_ref_id;
	}

	public String getEcm_folder_id() {
		return ecm_folder_id;
	}

	public void setEcm_folder_id(String ecm_folder_id) {
		this.ecm_folder_id = ecm_folder_id;
	}

	public String getEcom_gstin() {
		return ecom_gstin;
	}

	public void setEcom_gstin(String ecom_gstin) {
		this.ecom_gstin = ecom_gstin;
	}

	public Boolean getIgst_on_intra() {
		return igst_on_intra;
	}

	public void setIgst_on_intra(Boolean igst_on_intra) {
		this.igst_on_intra = igst_on_intra;
	}

	public BigDecimal getIgstvalue() {
		return igstvalue;
	}

	public void setIgstvalue(BigDecimal igstvalue) {
		this.igstvalue = igstvalue;
	}

	public String getInvoice_currency_code() {
		return invoice_currency_code;
	}

	public void setInvoice_currency_code(String invoice_currency_code) {
		this.invoice_currency_code = invoice_currency_code;
	}

	public String getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}



	public Date getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}

	public String getInvoicenum() {
		return invoicenum;
	}

	public void setInvoicenum(String invoicenum) {
		this.invoicenum = invoicenum;
	}

	public String getIrn() {
		return irn;
	}

	public void setIrn(String irn) {
		this.irn = irn;
	}

	public Boolean getIs_dispatched() {
		return is_dispatched;
	}

	public void setIs_dispatched(Boolean is_dispatched) {
		this.is_dispatched = is_dispatched;
	}

	public Timestamp getLast_updated_date() {
		return last_updated_date;
	}

	public void setLast_updated_date(Timestamp last_updated_date) {
		this.last_updated_date = last_updated_date;
	}

	public BigDecimal getOther_charges() {
		return other_charges;
	}

	public void setOther_charges(BigDecimal other_charges) {
		this.other_charges = other_charges;
	}

	public Boolean getReverse_charge() {
		return reverse_charge;
	}

	public void setReverse_charge(Boolean reverse_charge) {
		this.reverse_charge = reverse_charge;
	}

	public BigDecimal getRoundoff() {
		return roundoff;
	}

	public void setRoundoff(BigDecimal roundoff) {
		this.roundoff = roundoff;
	}

	public BigDecimal getSgstvalue() {
		return sgstvalue;
	}

	public void setSgstvalue(BigDecimal sgstvalue) {
		this.sgstvalue = sgstvalue;
	}

	public BigDecimal getStatecessvalue() {
		return statecessvalue;
	}

	public void setStatecessvalue(BigDecimal statecessvalue) {
		this.statecessvalue = statecessvalue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSupplier_note() {
		return supplier_note;
	}

	public void setSupplier_note(String supplier_note) {
		this.supplier_note = supplier_note;
	}



	public String getTax_scheme() {
		return tax_scheme;
	}

	public void setTax_scheme(String tax_scheme) {
		this.tax_scheme = tax_scheme;
	}

	public BigDecimal getTotal_assessable_value() {
		return total_assessable_value;
	}

	public void setTotal_assessable_value(BigDecimal total_assessable_value) {
		this.total_assessable_value = total_assessable_value;
	}

	public BigDecimal getTotal_invoice_value() {
		return total_invoice_value;
	}

	public void setTotal_invoice_value(BigDecimal total_invoice_value) {
		this.total_invoice_value = total_invoice_value;
	}

	public BigDecimal getTotal_invoice_value_fc() {
		return total_invoice_value_fc;
	}

	public void setTotal_invoice_value_fc(BigDecimal total_invoice_value_fc) {
		this.total_invoice_value_fc = total_invoice_value_fc;
	}

	public String getCustomer_partner_id() {
		return customer_partner_id;
	}

	public void setCustomer_partner_id(String customer_partner_id) {
		this.customer_partner_id = customer_partner_id;
	}

	public String getVendor_partner_id() {
		return vendor_partner_id;
	}

	public void setVendor_partner_id(String vendor_partner_id) {
		this.vendor_partner_id = vendor_partner_id;
	}

	public String getCustomer_recipient_code() {
		return customer_recipient_code;
	}

	public void setCustomer_recipient_code(String customer_recipient_code) {
		this.customer_recipient_code = customer_recipient_code;
	}

	public String getVendor_recipient_code() {
		return vendor_recipient_code;
	}

	public void setVendor_recipient_code(String vendor_recipient_code) {
		this.vendor_recipient_code = vendor_recipient_code;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getLast_updated_by() {
		return last_updated_by;
	}

	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}


	public String getInvoice_type_code() {
		return invoice_type_code;
	}

	public void setInvoice_type_code(String invoice_type_code) {
		this.invoice_type_code = invoice_type_code;
	}

	public String getSupply_type_code() {
		return supply_type_code;
	}

	public void setSupply_type_code(String supply_type_code) {
		this.supply_type_code = supply_type_code;
	}
	
	@Override
	public String toString() {
		return "InvoiceMetaDataModel [document_ref_id=" + document_ref_id + ", cessvalue=" + cessvalue + ", cgstvalue="
				+ cgstvalue + ", created_date=" + created_date + ", discount=" + discount + ", dispatch_mode="
				+ dispatch_mode + ", dispatched_on=" + dispatched_on + ", doc_source=" + doc_source
				+ ", doc_source_ref_id=" + doc_source_ref_id + ", ecm_folder_id=" + ecm_folder_id + ", ecom_gstin="
				+ ecom_gstin + ", igst_on_intra=" + igst_on_intra + ", igstvalue=" + igstvalue
				+ ", invoice_currency_code=" + invoice_currency_code + ", invoice_status=" + invoice_status
				+ ", invoice_type_code=" + invoice_type_code + ", invoicedate=" + invoicedate + ", invoicenum="
				+ invoicenum + ", irn=" + irn + ", is_dispatched=" + is_dispatched + ", last_updated_date="
				+ last_updated_date + ", other_charges=" + other_charges + ", reverse_charge=" + reverse_charge
				+ ", roundoff=" + roundoff + ", sgstvalue=" + sgstvalue + ", statecessvalue=" + statecessvalue
				+ ", status=" + status + ", supplier_note=" + supplier_note + ", supply_type_code=" + supply_type_code
				+ ", tax_scheme=" + tax_scheme + ", total_assessable_value=" + total_assessable_value
				+ ", total_invoice_value=" + total_invoice_value + ", total_invoice_value_fc=" + total_invoice_value_fc
				+ ", customer_partner_id=" + customer_partner_id + ", vendor_partner_id=" + vendor_partner_id
				+ ", customer_recipient_code=" + customer_recipient_code + ", vendor_recipient_code="
				+ vendor_recipient_code + ", created_by=" + created_by + ", last_updated_by=" + last_updated_by + "]";
	}

}
