package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="invoice_dispatch_shipto_details", schema = "einvoicing")
public class InvoiceDispatchShiptoDetail implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	private String dispatch_address1;
	private String dispatch_address2;
	private String dispatch_company_name;
	private String dispatch_location;
	private BigDecimal dispatch_pincode;
	private String shippingto_address1;
	private String shippingto_address2;
	private String shippingto_gstin;
	private String shippingto_legal_name;
	private String shippingto_location;
	private BigDecimal shippingto_pincode;
	private String shippingto_trade_name;
	private String document_ref_id;
	private String dispatch_state;
	private String shippintto_state;
	
	
	
	
	public String getDispatch_state() {
		return dispatch_state;
	}
	public void setDispatch_state(String dispatch_state) {
		this.dispatch_state = dispatch_state;
	}
	public String getShippintto_state() {
		return shippintto_state;
	}
	public void setShippintto_state(String shippintto_state) {
		this.shippintto_state = shippintto_state;
	}
	public String getDocument_ref_id() {
		return document_ref_id;
	}
	public void setDocument_ref_id(String document_ref_id) {
		this.document_ref_id = document_ref_id;
	}
	public InvoiceDispatchShiptoDetail() {
		super();
	}
	public InvoiceDispatchShiptoDetail(int id, String dispatch_address1, String dispatch_address2,
			String dispatch_company_name, String dispatch_location, BigDecimal dispatch_pincode,
			String shippingto_address1, String shippingto_address2, String shippingto_gstin,
			String shippingto_legal_name, String shippingto_location, BigDecimal shippingto_pincode,
			String shippingto_trade_name) {
		super();
		this.id = id;
		this.dispatch_address1 = dispatch_address1;
		this.dispatch_address2 = dispatch_address2;
		this.dispatch_company_name = dispatch_company_name;
		this.dispatch_location = dispatch_location;
		this.dispatch_pincode = dispatch_pincode;
		this.shippingto_address1 = shippingto_address1;
		this.shippingto_address2 = shippingto_address2;
		this.shippingto_gstin = shippingto_gstin;
		this.shippingto_legal_name = shippingto_legal_name;
		this.shippingto_location = shippingto_location;
		this.shippingto_pincode = shippingto_pincode;
		this.shippingto_trade_name = shippingto_trade_name;
	}
	@Override
	public String toString() {
		return "InvoiceDispatchShiptoDetail [id=" + id + ", dispatch_address1=" + dispatch_address1
				+ ", dispatch_address2=" + dispatch_address2 + ", dispatch_company_name=" + dispatch_company_name
				+ ", dispatch_location=" + dispatch_location + ", dispatch_pincode=" + dispatch_pincode
				+ ", shippingto_address1=" + shippingto_address1 + ", shippingto_address2=" + shippingto_address2
				+ ", shippingto_gstin=" + shippingto_gstin + ", shippingto_legal_name=" + shippingto_legal_name
				+ ", shippingto_location=" + shippingto_location + ", shippingto_pincode=" + shippingto_pincode
				+ ", shippingto_trade_name=" + shippingto_trade_name + ", document_ref_id=" + document_ref_id
				+ ", dispatch_state=" + dispatch_state + ", shippintto_state=" + shippintto_state + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDispatch_address1() {
		return dispatch_address1;
	}
	public void setDispatch_address1(String dispatch_address1) {
		this.dispatch_address1 = dispatch_address1;
	}
	public String getDispatch_address2() {
		return dispatch_address2;
	}
	public void setDispatch_address2(String dispatch_address2) {
		this.dispatch_address2 = dispatch_address2;
	}
	public String getDispatch_company_name() {
		return dispatch_company_name;
	}
	public void setDispatch_company_name(String dispatch_company_name) {
		this.dispatch_company_name = dispatch_company_name;
	}
	public String getDispatch_location() {
		return dispatch_location;
	}
	public void setDispatch_location(String dispatch_location) {
		this.dispatch_location = dispatch_location;
	}
	public BigDecimal getDispatch_pincode() {
		return dispatch_pincode;
	}
	public void setDispatch_pincode(BigDecimal dispatch_pincode) {
		this.dispatch_pincode = dispatch_pincode;
	}
	public String getShippingto_address1() {
		return shippingto_address1;
	}
	public void setShippingto_address1(String shippingto_address1) {
		this.shippingto_address1 = shippingto_address1;
	}
	public String getShippingto_address2() {
		return shippingto_address2;
	}
	public void setShippingto_address2(String shippingto_address2) {
		this.shippingto_address2 = shippingto_address2;
	}
	public String getShippingto_gstin() {
		return shippingto_gstin;
	}
	public void setShippingto_gstin(String shippingto_gstin) {
		this.shippingto_gstin = shippingto_gstin;
	}
	public String getShippingto_legal_name() {
		return shippingto_legal_name;
	}
	public void setShippingto_legal_name(String shippingto_legal_name) {
		this.shippingto_legal_name = shippingto_legal_name;
	}
	public String getShippingto_location() {
		return shippingto_location;
	}
	public void setShippingto_location(String shippingto_location) {
		this.shippingto_location = shippingto_location;
	}
	public BigDecimal getShippingto_pincode() {
		return shippingto_pincode;
	}
	public void setShippingto_pincode(BigDecimal shippingto_pincode) {
		this.shippingto_pincode = shippingto_pincode;
	}
	public String getShippingto_trade_name() {
		return shippingto_trade_name;
	}
	public void setShippingto_trade_name(String shippingto_trade_name) {
		this.shippingto_trade_name = shippingto_trade_name;
	}
	

}