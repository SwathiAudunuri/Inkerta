package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.json.JSONPropertyIgnore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the invoice_document_details database table.
 * 
 */
@Entity
@Table(name = "invoice_document_details", schema = "einvoicing")
@DynamicUpdate
@JsonIgnoreProperties
public class InvoiceDocumentDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String document_ref_id;

	@JsonIgnore
	private BigDecimal cessvalue;

	private BigDecimal cgstvalue;
	@CreationTimestamp
	@Column(name = "created_date",nullable = false, updatable = false, insertable = false)
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

	@Temporal(TemporalType.DATE)
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
	
	private String assigned_to;
	
	private Integer creditdays;
	
	

	public Integer getCreditdays() {
		return creditdays;
	}






	public void setCreditdays(Integer creditdays) {
		this.creditdays = creditdays;
	}






	public String getAssigned_to() {
		return assigned_to;
	}






	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}


	@Transient
	private List<InvoiceDispatchShiptoDetail> invoiceDispatchShiptoDetails;

	@Transient
	private List<InvoiceAttachmentDetail> invoiceAttachmentDetails;


	@Transient
	private List<InvoiceEwayBillDetail> invoiceEwayBillDetails;
	
	@Transient
	private List<InvoiceReferenceDetails> invoiceReferenceDetails;
	

	@Transient
	private List<InvoiceSellerPaymentInformation> invoiceSellerPaymentInformations;

	@Transient
	private InvoiceSupplierBuyerInfo invoiceSupplierBuyerInfo;
	
	
	@Transient
	private List<InvoiceItemList> lineItems;

	public InvoiceDocumentDetail() {
	}






	public BigDecimal getCessvalue() {
		return cessvalue;
	}

	public BigDecimal getCgstvalue() {
		return cgstvalue;
	}

	public String getCreated_by() {
		return created_by;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public String getCustomer_partner_id() {
		return customer_partner_id;
	}

	public String getCustomer_recipient_code() {
		return customer_recipient_code;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public String getDispatch_mode() {
		return dispatch_mode;
	}

	public Timestamp getDispatched_on() {
		return dispatched_on;
	}

	public String getDoc_source() {
		return doc_source;
	}

	public String getDoc_source_ref_id() {
		return doc_source_ref_id;
	}

	public String getDocument_ref_id() {
		return document_ref_id;
	}

	public String getEcm_folder_id() {
		return ecm_folder_id;
	}

	public String getEcom_gstin() {
		return ecom_gstin;
	}

	public Boolean getIgst_on_intra() {
		return igst_on_intra;
	}

	public BigDecimal getIgstvalue() {
		return igstvalue;
	}

	public String getInvoice_currency_code() {
		return invoice_currency_code;
	}

	public String getInvoice_status() {
		return invoice_status;
	}


	public List<InvoiceAttachmentDetail> getInvoiceAttachmentDetails() {
		return invoiceAttachmentDetails;
	}

	public Date getInvoicedate() {
		return invoicedate;
	}

	public List<InvoiceDispatchShiptoDetail> getInvoiceDispatchShiptoDetails() {
		return this.invoiceDispatchShiptoDetails;
	}

	public List<InvoiceEwayBillDetail> getInvoiceEwayBillDetails() {
		return this.invoiceEwayBillDetails;
	}

	public String getInvoicenum() {
		return invoicenum;
	}

	public List<InvoiceSellerPaymentInformation> getInvoiceSellerPaymentInformations() {
		return this.invoiceSellerPaymentInformations;
	}

	public InvoiceSupplierBuyerInfo getInvoiceSupplierBuyerInfo() {
		return invoiceSupplierBuyerInfo;
	}

	public String getIrn() {
		return irn;
	}

	public Boolean getIs_dispatched() {
		return is_dispatched;
	}

	public String getLast_updated_by() {
		return last_updated_by;
	}





	public List<InvoiceItemList> getLineItems() {
		return lineItems;
	}


	public void setLineItems(List<InvoiceItemList> lineItems) {
		this.lineItems = lineItems;
	}


	public Timestamp getLast_updated_date() {
		return last_updated_date;
	}

	public BigDecimal getOther_charges() {
		return other_charges;
	}

	public Boolean getReverse_charge() {
		return reverse_charge;
	}

	public BigDecimal getRoundoff() {
		return roundoff;
	}

	public BigDecimal getSgstvalue() {
		return sgstvalue;
	}

	public BigDecimal getStatecessvalue() {
		return statecessvalue;
	}

	public String getStatus() {
		return status;
	}

	public String getSupplier_note() {
		return supplier_note;
	}


	public String getTax_scheme() {
		return tax_scheme;
	}

	public BigDecimal getTotal_assessable_value() {
		return total_assessable_value;
	}

	public BigDecimal getTotal_invoice_value() {
		return total_invoice_value;
	}

	public BigDecimal getTotal_invoice_value_fc() {
		return total_invoice_value_fc;
	}

	public String getVendor_partner_id() {
		return vendor_partner_id;
	}

	public String getVendor_recipient_code() {
		return vendor_recipient_code;
	}






	public void setCessvalue(BigDecimal cessvalue) {
		this.cessvalue = cessvalue;
	}

	public void setCgstvalue(BigDecimal cgstvalue) {
		this.cgstvalue = cgstvalue;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public void setCustomer_partner_id(String customer_partner_id) {
		this.customer_partner_id = customer_partner_id;
	}

	public void setCustomer_recipient_code(String customer_recipient_code) {
		this.customer_recipient_code = customer_recipient_code;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public void setDispatch_mode(String dispatch_mode) {
		this.dispatch_mode = dispatch_mode;
	}

	public void setDispatched_on(Timestamp dispatched_on) {
		this.dispatched_on = dispatched_on;
	}

	public void setDoc_source(String doc_source) {
		this.doc_source = doc_source;
	}

	public void setDoc_source_ref_id(String doc_source_ref_id) {
		this.doc_source_ref_id = doc_source_ref_id;
	}

	public void setDocument_ref_id(String document_ref_id) {
		this.document_ref_id = document_ref_id;
	}

	public void setEcm_folder_id(String ecm_folder_id) {
		this.ecm_folder_id = ecm_folder_id;
	}

	public void setEcom_gstin(String ecom_gstin) {
		this.ecom_gstin = ecom_gstin;
	}

	public void setIgst_on_intra(Boolean igst_on_intra) {
		this.igst_on_intra = igst_on_intra;
	}

	public void setIgstvalue(BigDecimal igstvalue) {
		this.igstvalue = igstvalue;
	}

	public void setInvoice_currency_code(String invoice_currency_code) {
		this.invoice_currency_code = invoice_currency_code;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}


	public void setInvoiceAttachmentDetails(List<InvoiceAttachmentDetail> invoiceAttachmentDetails) {
		this.invoiceAttachmentDetails = invoiceAttachmentDetails;
	}

	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}

	public void setInvoiceDispatchShiptoDetails(List<InvoiceDispatchShiptoDetail> invoiceDispatchShiptoDetails) {
		this.invoiceDispatchShiptoDetails = invoiceDispatchShiptoDetails;
	}

	public void setInvoiceEwayBillDetails(List<InvoiceEwayBillDetail> invoiceEwayBillDetails) {
		this.invoiceEwayBillDetails = invoiceEwayBillDetails;
	}
	
	public List<InvoiceReferenceDetails> getInvoiceReferenceDetails() {
		return this.invoiceReferenceDetails;
	}
	
	
	public void setInvoiceReferenceDetails(List<InvoiceReferenceDetails> invoiceReferenceDetails) {
		this.invoiceReferenceDetails = invoiceReferenceDetails;
	}
	

	public void setInvoicenum(String invoicenum) {
		this.invoicenum = invoicenum;
	}

	public void setInvoiceSellerPaymentInformations(
			List<InvoiceSellerPaymentInformation> invoiceSellerPaymentInformations) {
		this.invoiceSellerPaymentInformations = invoiceSellerPaymentInformations;
	}

	public void setInvoiceSupplierBuyerInfo(InvoiceSupplierBuyerInfo invoiceSupplierBuyerInfo) {
		this.invoiceSupplierBuyerInfo = invoiceSupplierBuyerInfo;
	}

	public void setIrn(String irn) {
		this.irn = irn;
	}

	public void setIs_dispatched(Boolean is_dispatched) {
		this.is_dispatched = is_dispatched;
	}

	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	public void setLast_updated_date(Timestamp last_updated_date) {
		this.last_updated_date = last_updated_date;
	}

	public void setOther_charges(BigDecimal other_charges) {
		this.other_charges = other_charges;
	}

	public void setReverse_charge(Boolean reverse_charge) {
		this.reverse_charge = reverse_charge;
	}

//	public PartnerDetail getPartnerDetail1() {
//		return this.partnerDetail1;
//	}
//
//	public void setPartnerDetail1(PartnerDetail partnerDetail1) {
//		this.partnerDetail1 = partnerDetail1;
//	}
//
//	public PartnerDetail getPartnerDetail2() {
//		return this.partnerDetail2;
//	}
//
//	public void setPartnerDetail2(PartnerDetail partnerDetail2) {
//		this.partnerDetail2 = partnerDetail2;
//	}
//
//	public RecipientMapping getRecipientMapping1() {
//		return this.recipientMapping1;
//	}
//
//	public void setRecipientMapping1(RecipientMapping recipientMapping1) {
//		this.recipientMapping1 = recipientMapping1;
//	}
//
//	public RecipientMapping getRecipientMapping2() {
//		return this.recipientMapping2;
//	}
//
//	public void setRecipientMapping2(RecipientMapping recipientMapping2) {
//		this.recipientMapping2 = recipientMapping2;
//	}
//
//	public UserManagement getUserManagement1() {
//		return this.userManagement1;
//	}
//
//	public void setUserManagement1(UserManagement userManagement1) {
//		this.userManagement1 = userManagement1;
//	}
//
//	public UserManagement getUserManagement2() {
//		return this.userManagement2;
//	}
//
//	public void setUserManagement2(UserManagement userManagement2) {
//		this.userManagement2 = userManagement2;
//	}

	public void setRoundoff(BigDecimal roundoff) {
		this.roundoff = roundoff;
	}

	public void setSgstvalue(BigDecimal sgstvalue) {
		this.sgstvalue = sgstvalue;
	}

	public void setStatecessvalue(BigDecimal statecessvalue) {
		this.statecessvalue = statecessvalue;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSupplier_note(String supplier_note) {
		this.supplier_note = supplier_note;
	}


	public void setTax_scheme(String tax_scheme) {
		this.tax_scheme = tax_scheme;
	}

	public void setTotal_assessable_value(BigDecimal total_assessable_value) {
		this.total_assessable_value = total_assessable_value;
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

	public void setTotal_invoice_value(BigDecimal total_invoice_value) {
		this.total_invoice_value = total_invoice_value;
	}

	public void setTotal_invoice_value_fc(BigDecimal total_invoice_value_fc) {
		this.total_invoice_value_fc = total_invoice_value_fc;
	}

	public void setVendor_partner_id(String vendor_partner_id) {
		this.vendor_partner_id = vendor_partner_id;
	}

	public void setVendor_recipient_code(String vendor_recipient_code) {
		this.vendor_recipient_code = vendor_recipient_code;
	}
	
	
	@Override
	public String toString() {
		return "InvoiceDocumentDetail [document_ref_id=" + document_ref_id + ", cessvalue=" + cessvalue + ", cgstvalue="
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
				+ vendor_recipient_code + ", created_by=" + created_by + ", last_updated_by=" + last_updated_by
				+ ", invoiceDispatchShiptoDetails=" + invoiceDispatchShiptoDetails + ", invoiceAttachmentDetails="
				+ invoiceAttachmentDetails + ", invoiceEwayBillDetails=" + invoiceEwayBillDetails
				+ ", invoiceSellerPaymentInformations=" + invoiceSellerPaymentInformations
				+ ", invoiceReferenceDetails=" + invoiceReferenceDetails
				+ ", invoiceSupplierBuyerInfo=" + invoiceSupplierBuyerInfo + "]";
	}









}