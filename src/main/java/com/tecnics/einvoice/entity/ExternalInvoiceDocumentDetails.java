
package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.json.JSONPropertyIgnore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tecnics.einvoice.model.InvoiceAction;
import com.tecnics.einvoice.model.MailAddresses;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the invoice_document_details database table.
 * 
 */
@Entity
@Table(name = "external_invoice_document_details", schema = "einvoicing")
@DynamicUpdate
@JsonIgnoreProperties
public class ExternalInvoiceDocumentDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String document_ref_id;	  
	private String partner_id;  
	private String irn;
	private String invoice_type_code;
	private String invoicenum;	
	@Temporal(TemporalType.DATE)
	private Date invoicedate;
	private String invoice_currency_code;
	private String invoice_status;
	private String doc_source;
	private String transaction_type;
	private String created_by;
	@CreationTimestamp
	@Column(name = "created_date",nullable = false, updatable = false, insertable = false)	
	private Timestamp created_date;	
	private String last_updated_by;
	private Timestamp last_updated_date;
	private Boolean is_dispatched;
	private String dispatch_mode;
	private Timestamp dispatched_on;
	private String status;
	private String supplier_note;
	private String ecm_folder_id;
	private String tax_scheme;
	private String supply_type_code;
	private Boolean reverse_charge;
	private String recipient_company_name;
	private String recipient_gstin;
	private String recipient_address;
	private String invoice_reference_no;
	private BigDecimal subtotal;
	private BigDecimal taxrate;
	private BigDecimal taxamount;
	private BigDecimal total_invoice_value;
	private String assigned_to;
	private int creditdays;
	private String partner_gstin;
	private String partner_address;
	private String partner_company_name;
	
	private String recipient_partner_id;
	
	public String getRecipient_partner_id() {
		return recipient_partner_id;
	}


	public void setRecipient_partner_id(String recipient_partner_id) {
		this.recipient_partner_id = recipient_partner_id;
	}


	public String getPartner_company_name() {
		return partner_company_name;
	}


	public void setPartner_company_name(String partner_company_name) {
		this.partner_company_name = partner_company_name;
	}


	public String getPartner_address() {
		return partner_address;
	}


	public void setPartner_address(String partner_address) {
		this.partner_address = partner_address;
	}


	public String getPartner_gstin() {
		return partner_gstin;
	}


	public void setPartner_gstin(String partner_gstin) {
		this.partner_gstin = partner_gstin;
	}


	public int getCreditdays() {
		return creditdays;
	}


	public void setCreditdays(int creditdays) {
		this.creditdays = creditdays;
	}


	public ExternalInvoiceDocumentDetails() {
	}


	public String getDocument_ref_id() {
		return document_ref_id;
	}

	public void setDocument_ref_id(String document_ref_id) {
		this.document_ref_id = document_ref_id;
	}

	public String getPartner_id() {
		return partner_id;
	}


	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}


	public String getIrn() {
		return irn;
	}


	public void setIrn(String irn) {
		this.irn = irn;
	}


	public String getInvoice_type_code() {
		return invoice_type_code;
	}


	public void setInvoice_type_code(String invoice_type_code) {
		this.invoice_type_code = invoice_type_code;
	}

	public String getInvoicenum() {
		return invoicenum;
	}


	public void setInvoicenum(String invoicenum) {
		this.invoicenum = invoicenum;
	}


	public Date getInvoicedate() {
		return invoicedate;
	}


	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
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


	public String getDoc_source() {
		return doc_source;
	}


	public void setDoc_source(String doc_source) {
		this.doc_source = doc_source;
	}


	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public String getLast_updated_by() {
		return last_updated_by;
	}

	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	public Timestamp getLast_updated_date() {
		return last_updated_date;
	}

	public void setLast_updated_date(Timestamp last_updated_date) {
		this.last_updated_date = last_updated_date;
	}

	public Boolean getIs_dispatched() {
		return is_dispatched;
	}

	public void setIs_dispatched(Boolean is_dispatched) {
		this.is_dispatched = is_dispatched;
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

	public String getEcm_folder_id() {
		return ecm_folder_id;
	}

	public void setEcm_folder_id(String ecm_folder_id) {
		this.ecm_folder_id = ecm_folder_id;
	}

	public String getTax_scheme() {
		return tax_scheme;
	}

	public void setTax_scheme(String tax_scheme) {
		this.tax_scheme = tax_scheme;
	}

	public String getSupply_type_code() {
		return supply_type_code;
	}

	public void setSupply_type_code(String supply_type_code) {
		this.supply_type_code = supply_type_code;
	}

	public Boolean getReverse_charge() {
		return reverse_charge;
	}

	public void setReverse_charge(Boolean reverse_charge) {
		this.reverse_charge = reverse_charge;
	}

	public String getRecipient_company_name() {
		return recipient_company_name;
	}

	public void setRecipient_company_name(String recipient_company_name) {
		this.recipient_company_name = recipient_company_name;
	}

	public String getRecipient_gstin() {
		return recipient_gstin;
	}

	public void setRecipient_gstin(String recipient_gstin) {
		this.recipient_gstin = recipient_gstin;
	}

	public String getRecipient_address() {
		return recipient_address;
	}

	public void setRecipient_address(String recipient_address) {
		this.recipient_address = recipient_address;
	}

	public String getInvoice_reference_no() {
		return invoice_reference_no;
	}

	public void setInvoice_reference_no(String invoice_reference_no) {
		this.invoice_reference_no = invoice_reference_no;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(BigDecimal taxrate) {
		this.taxrate = taxrate;
	}

	public BigDecimal getTaxamount() {
		return taxamount;
	}

	public void setTaxamount(BigDecimal taxamount) {
		this.taxamount = taxamount;
	}

	public BigDecimal getTotal_invoice_value() {
		return total_invoice_value;
	}

	public void setTotal_invoice_value(BigDecimal total_invoice_value) {
		this.total_invoice_value = total_invoice_value;
	}

	public String getAssigned_to() {
		return assigned_to;
	}

	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}

	@Override
	public String toString() {
		return "InvoiceDocumentDetail [document_ref_id=" + document_ref_id + ", subtotal=" + subtotal + ", taxrate="
				+ taxrate + ", created_date=" + created_date + ", taxamount=" + taxamount + ", dispatch_mode="
				+ dispatch_mode + ", dispatched_on=" + dispatched_on + ", doc_source=" + doc_source
				+ ", ecm_folder_id=" + ecm_folder_id + ", recipient_gstin="	+ recipient_gstin 
				+ ", invoice_currency_code=" + invoice_currency_code + ", invoice_status=" + invoice_status
				+ ", invoice_type_code=" + invoice_type_code + ", invoicedate=" + invoicedate + ", invoicenum="
				+ invoicenum + ", irn=" + irn + ", is_dispatched=" + is_dispatched + ", last_updated_date="
				+ last_updated_date + ", reverse_charge=" + reverse_charge
				+ ", status=" + status + ", supplier_note=" + supplier_note + ", supply_type_code=" + supply_type_code
				+ ", tax_scheme=" + tax_scheme + ", total_invoice_value=" + total_invoice_value  
				+ ", created_by=" + created_by + ", last_updated_by=" + last_updated_by+ "]";
	}}
