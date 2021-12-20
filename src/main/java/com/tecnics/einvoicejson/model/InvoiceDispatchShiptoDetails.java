package com.tecnics.einvoicejson.model;

public class InvoiceDispatchShiptoDetails {
	public String dispatch_company_name;
	 public String dispatch_address1;
	 public String dispatch_address2;
	 public String dispatch_location;
	 public int dispatch_pincode;
	 public String dispatch_state;
	 public String shippingto_gstin;
	 public String shippingto_legal_name;
	 public String shippingto_trade_name;
	 public String shippingto_address1;
	 public String shippingto_address2;
	 public String shippingto_location;
	 public int shippingto_pincode;
	 public String shippintto_state;
	public String getDispatch_company_name() {
		return dispatch_company_name;
	}
	public void setDispatch_company_name(String dispatch_company_name) {
		this.dispatch_company_name = dispatch_company_name;
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
	public String getDispatch_location() {
		return dispatch_location;
	}
	public void setDispatch_location(String dispatch_location) {
		this.dispatch_location = dispatch_location;
	}
	public int getDispatch_pincode() {
		return dispatch_pincode;
	}
	public void setDispatch_pincode(int dispatch_pincode) {
		this.dispatch_pincode = dispatch_pincode;
	}
	public String getDispatch_state() {
		return dispatch_state;
	}
	public void setDispatch_state(String dispatch_state) {
		this.dispatch_state = dispatch_state;
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
	public String getShippingto_trade_name() {
		return shippingto_trade_name;
	}
	public void setShippingto_trade_name(String shippingto_trade_name) {
		this.shippingto_trade_name = shippingto_trade_name;
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
	public String getShippingto_location() {
		return shippingto_location;
	}
	public void setShippingto_location(String shippingto_location) {
		this.shippingto_location = shippingto_location;
	}
	public int getShippingto_pincode() {
		return shippingto_pincode;
	}
	public void setShippingto_pincode(int shippingto_pincode) {
		this.shippingto_pincode = shippingto_pincode;
	}
	public String getShippintto_state() {
		return shippintto_state;
	}
	public void setShippintto_state(String shippintto_state) {
		this.shippintto_state = shippintto_state;
	}
	public InvoiceDispatchShiptoDetails(String dispatch_company_name, String dispatch_address1,
			String dispatch_address2, String dispatch_location, int dispatch_pincode, String dispatch_state,
			String shippingto_gstin, String shippingto_legal_name, String shippingto_trade_name,
			String shippingto_address1, String shippingto_address2, String shippingto_location, int shippingto_pincode,
			String shippintto_state) {
		super();
		this.dispatch_company_name = dispatch_company_name;
		this.dispatch_address1 = dispatch_address1;
		this.dispatch_address2 = dispatch_address2;
		this.dispatch_location = dispatch_location;
		this.dispatch_pincode = dispatch_pincode;
		this.dispatch_state = dispatch_state;
		this.shippingto_gstin = shippingto_gstin;
		this.shippingto_legal_name = shippingto_legal_name;
		this.shippingto_trade_name = shippingto_trade_name;
		this.shippingto_address1 = shippingto_address1;
		this.shippingto_address2 = shippingto_address2;
		this.shippingto_location = shippingto_location;
		this.shippingto_pincode = shippingto_pincode;
		this.shippintto_state = shippintto_state;
	}
	public InvoiceDispatchShiptoDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "InvoiceDispatchShiptoDetails [dispatch_company_name=" + dispatch_company_name + ", dispatch_address1="
				+ dispatch_address1 + ", dispatch_address2=" + dispatch_address2 + ", dispatch_location="
				+ dispatch_location + ", dispatch_pincode=" + dispatch_pincode + ", dispatch_state=" + dispatch_state
				+ ", shippingto_gstin=" + shippingto_gstin + ", shippingto_legal_name=" + shippingto_legal_name
				+ ", shippingto_trade_name=" + shippingto_trade_name + ", shippingto_address1=" + shippingto_address1
				+ ", shippingto_address2=" + shippingto_address2 + ", shippingto_location=" + shippingto_location
				+ ", shippingto_pincode=" + shippingto_pincode + ", shippintto_state=" + shippintto_state + "]";
	}
	 
}
