package com.tecnics.einvoice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="invoice_reference_details", schema = "einvoicing")
public class InvoiceReferenceDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	private String document_ref_id;
	private String receipt_advice_reference;
	private Timestamp receipt_advice_date;
	private String tender_or_lot_reference;
	private String contract_reference;
	private String external_reference;	
	private String project_reference;
	private String po_ref_num;
	private Timestamp po_ref_date;
	private String preceeding_invoice_number;
	private String invoice_document_reference;
	private Timestamp preceeding_invoice_date;
	private Timestamp invoice_period_start_date;
	private Timestamp invoice_period_end_date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocument_ref_id() {
		return document_ref_id;
	}
	public void setDocument_ref_id(String document_ref_id) {
		this.document_ref_id = document_ref_id;
	}
	public String getReceipt_advice_reference() {
		return receipt_advice_reference;
	}
	public void setReceipt_advice_reference(String receipt_advice_reference) {
		this.receipt_advice_reference = receipt_advice_reference;
	}
	public Timestamp getReceipt_advice_date() {
		return receipt_advice_date;
	}
	public void setReceipt_advice_date(Timestamp receipt_advice_date) {
		this.receipt_advice_date = receipt_advice_date;
	}
	public String getTender_or_lot_reference() {
		return tender_or_lot_reference;
	}
	public void setTender_or_lot_reference(String tender_or_lot_reference) {
		this.tender_or_lot_reference = tender_or_lot_reference;
	}
	public String getContract_reference() {
		return contract_reference;
	}
	public void setContract_reference(String contract_reference) {
		this.contract_reference = contract_reference;
	}
	public String getExternal_reference() {
		return external_reference;
	}
	public void setExternal_reference(String external_reference) {
		this.external_reference = external_reference;
	}
	public String getProject_reference() {
		return project_reference;
	}
	public void setProject_reference(String project_reference) {
		this.project_reference = project_reference;
	}
	public String getPo_ref_num() {
		return po_ref_num;
	}
	public void setPo_ref_num(String po_ref_num) {
		this.po_ref_num = po_ref_num;
	}
	public Timestamp getPo_ref_date() {
		return po_ref_date;
	}
	public void setPo_ref_date(Timestamp po_ref_date) {
		this.po_ref_date = po_ref_date;
	}
	public String getPreceeding_invoice_number() {
		return preceeding_invoice_number;
	}
	public void setPreceeding_invoice_number(String preceeding_invoice_number) {
		this.preceeding_invoice_number = preceeding_invoice_number;
	}
	public String getInvoice_document_reference() {
		return invoice_document_reference;
	}
	public void setInvoice_document_reference(String invoice_document_reference) {
		this.invoice_document_reference = invoice_document_reference;
	}
	public Timestamp getPreceeding_invoice_date() {
		return preceeding_invoice_date;
	}
	public void setPreceeding_invoice_date(Timestamp preceeding_invoice_date) {
		this.preceeding_invoice_date = preceeding_invoice_date;
	}
	public Timestamp getInvoice_period_start_date() {
		return invoice_period_start_date;
	}
	public void setInvoice_period_start_date(Timestamp invoice_period_start_date) {
		this.invoice_period_start_date = invoice_period_start_date;
	}
	public Timestamp getInvoice_period_end_date() {
		return invoice_period_end_date;
	}
	public void setInvoice_period_end_date(Timestamp invoice_period_end_date) {
		this.invoice_period_end_date = invoice_period_end_date;
	}

	
	@Override
	public String toString() {
		return "InvoiceReferenceDetail [document_ref_id=" + document_ref_id + ", receipt_advice_reference=" + receipt_advice_reference 
				+ ", receipt_advice_date" + receipt_advice_date + ", tender_or_lot_reference " + tender_or_lot_reference
				+ ", contract_reference" + contract_reference + ", external_reference " + external_reference
				+ ", project_reference" + project_reference + ", po_ref_num " + po_ref_num
				+ ", po_ref_date" + po_ref_date + ", preceeding_invoice_number " + preceeding_invoice_number
				+ ", invoice_document_reference" + invoice_document_reference + ", preceeding_invoice_date " + preceeding_invoice_date
				+ ", contract_reference" + contract_reference + ", external_reference " + external_reference				
				+ ", invoice_period_start_date=" + invoice_period_start_date + ", invoice_period_end_date"+ invoice_period_end_date + "]";
	}
	

}
