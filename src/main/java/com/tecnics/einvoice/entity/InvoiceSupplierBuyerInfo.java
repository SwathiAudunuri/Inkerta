package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the invoice_supplier_buyer_info database table.
 * 
 */
@Entity
@Table(name = "invoice_supplier_buyer_details", schema = "einvoicing")
public class InvoiceSupplierBuyerInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	private String document_ref_id;
	private String billing_address1;
	private String billing_address2;
	private String billing_email;
	private String billing_gstin;
	private String billing_legal_name;
	private String billing_location;
	private String billing_phone;
	private BigDecimal billing_pincode;
	private String billing_state;
	private String billing_trade_name;
	private String supplier_address1;
	private String supplier_address2;
	private String supplier_email;
	private String supplier_gstin;
	private String supplier_legal_name;
	private String supplier_location;
	private String supplier_phone;
	private BigDecimal supplier_pincode;
	private String supplier_state;
	private String supplier_trading_name;



	public InvoiceSupplierBuyerInfo() {
	}



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



	public String getBilling_address1() {
		return billing_address1;
	}



	public void setBilling_address1(String billing_address1) {
		this.billing_address1 = billing_address1;
	}



	public String getBilling_address2() {
		return billing_address2;
	}



	public void setBilling_address2(String billing_address2) {
		this.billing_address2 = billing_address2;
	}



	public String getBilling_email() {
		return billing_email;
	}



	public void setBilling_email(String billing_email) {
		this.billing_email = billing_email;
	}



	public String getBilling_gstin() {
		return billing_gstin;
	}



	public void setBilling_gstin(String billing_gstin) {
		this.billing_gstin = billing_gstin;
	}



	public String getBilling_legal_name() {
		return billing_legal_name;
	}



	public void setBilling_legal_name(String billing_legal_name) {
		this.billing_legal_name = billing_legal_name;
	}



	public String getBilling_location() {
		return billing_location;
	}



	public void setBilling_location(String billing_location) {
		this.billing_location = billing_location;
	}



	public String getBilling_phone() {
		return billing_phone;
	}



	public void setBilling_phone(String billing_phone) {
		this.billing_phone = billing_phone;
	}



	public BigDecimal getBilling_pincode() {
		return billing_pincode;
	}



	public void setBilling_pincode(BigDecimal billing_pincode) {
		this.billing_pincode = billing_pincode;
	}



	public String getBilling_state() {
		return billing_state;
	}



	public void setBilling_state(String billing_state) {
		this.billing_state = billing_state;
	}



	public String getBilling_trade_name() {
		return billing_trade_name;
	}



	public void setBilling_trade_name(String billing_trade_name) {
		this.billing_trade_name = billing_trade_name;
	}



	public String getSupplier_address1() {
		return supplier_address1;
	}



	public void setSupplier_address1(String supplier_address1) {
		this.supplier_address1 = supplier_address1;
	}



	public String getSupplier_address2() {
		return supplier_address2;
	}



	public void setSupplier_address2(String supplier_address2) {
		this.supplier_address2 = supplier_address2;
	}



	public String getSupplier_email() {
		return supplier_email;
	}



	public void setSupplier_email(String supplier_email) {
		this.supplier_email = supplier_email;
	}



	public String getSupplier_gstin() {
		return supplier_gstin;
	}



	public void setSupplier_gstin(String supplier_gstin) {
		this.supplier_gstin = supplier_gstin;
	}



	public String getSupplier_legal_name() {
		return supplier_legal_name;
	}



	public void setSupplier_legal_name(String supplier_legal_name) {
		this.supplier_legal_name = supplier_legal_name;
	}



	public String getSupplier_location() {
		return supplier_location;
	}



	public void setSupplier_location(String supplier_location) {
		this.supplier_location = supplier_location;
	}



	public String getSupplier_phone() {
		return supplier_phone;
	}



	public void setSupplier_phone(String supplier_phone) {
		this.supplier_phone = supplier_phone;
	}



	public BigDecimal getSupplier_pincode() {
		return supplier_pincode;
	}



	public void setSupplier_pincode(BigDecimal supplier_pincode) {
		this.supplier_pincode = supplier_pincode;
	}



	public String getSupplier_trading_name() {
		return supplier_trading_name;
	}



	public void setSupplier_trading_name(String supplier_trading_name) {
		this.supplier_trading_name = supplier_trading_name;
	}
	
	public String getSupplier_state() {
		return supplier_state;
	}



	public void setSupplier_state(String supplier_state) {
		this.supplier_state = supplier_state;
	}
	

	public InvoiceSupplierBuyerInfo(int id, String document_ref_id, String billing_address1, String billing_address2,
			String billing_email, String billing_gstin, String billing_legal_name, String billing_location,
			String billing_phone, BigDecimal billing_pincode, String billing_state, String billing_trade_name,
			String supplier_address1, String supplier_address2, String supplier_email, String supplier_gstin,
			String supplier_legal_name, String supplier_location, String supplier_phone, BigDecimal supplier_pincode,
			String supplier_trading_name) {
		super();
		this.id = id;
		this.document_ref_id = document_ref_id;
		this.billing_address1 = billing_address1;
		this.billing_address2 = billing_address2;
		this.billing_email = billing_email;
		this.billing_gstin = billing_gstin;
		this.billing_legal_name = billing_legal_name;
		this.billing_location = billing_location;
		this.billing_phone = billing_phone;
		this.billing_pincode = billing_pincode;
		this.billing_state = billing_state;
		this.billing_trade_name = billing_trade_name;
		this.supplier_address1 = supplier_address1;
		this.supplier_address2 = supplier_address2;
		this.supplier_email = supplier_email;
		this.supplier_gstin = supplier_gstin;
		this.supplier_legal_name = supplier_legal_name;
		this.supplier_location = supplier_location;
		this.supplier_phone = supplier_phone;
		this.supplier_pincode = supplier_pincode;
		this.supplier_trading_name = supplier_trading_name;
	}



	@Override
	public String toString() {
		return "InvoiceSupplierBuyerInfo [id=" + id + ", document_ref_id=" + document_ref_id + ", billing_address1="
				+ billing_address1 + ", billing_address2=" + billing_address2 + ", billing_email=" + billing_email
				+ ", billing_gstin=" + billing_gstin + ", billing_legal_name=" + billing_legal_name
				+ ", billing_location=" + billing_location + ", billing_phone=" + billing_phone + ", billing_pincode="
				+ billing_pincode + ", billing_state=" + billing_state + ", billing_trade_name=" + billing_trade_name
				+ ", supplier_address1=" + supplier_address1 + ", supplier_address2=" + supplier_address2
				+ ", supplier_email=" + supplier_email + ", supplier_gstin=" + supplier_gstin + ", supplier_legal_name="
				+ supplier_legal_name + ", supplier_location=" + supplier_location + ", supplier_phone="
				+ supplier_phone + ", supplier_pincode=" + supplier_pincode + ", supplier_trading_name="
				+ supplier_trading_name + "]";
	}

	


}