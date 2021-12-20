package com.tecnics.einvoice.model;

import javax.persistence.Column;

public class CustomerInfoModel {

	
	private String company_name;
	private String country;
	private String nature_of_business;
	private String pan_no;
	private String partner_id;
	private String vendor_partner_id;
	@Column(name = "recipient_id")
	private String id;
	@Override
	public String toString() {
		return "CustomerInfoModel [company_name=" + company_name + ", country=" + country + ", nature_of_business="
				+ nature_of_business + ", pan_no=" + pan_no + ", partner_id=" + partner_id + ", vendor_partner_id="
				+ vendor_partner_id + ", recipient_id=" + id + "]";
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getNature_of_business() {
		return nature_of_business;
	}
	public void setNature_of_business(String nature_of_business) {
		this.nature_of_business = nature_of_business;
	}
	public String getPan_no() {
		return pan_no;
	}
	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public String getVendor_partner_id() {
		return vendor_partner_id;
	}
	public void setVendor_partner_id(String vendor_partner_id) {
		this.vendor_partner_id = vendor_partner_id;
	}
	public String getRecipient_id() {
		return id;
	}
	public void setRecipient_id(String recipient_id) {
		this.id = recipient_id;
	}
	public CustomerInfoModel(String company_name, String country, String nature_of_business, String pan_no,
			String partner_id, String vendor_partner_id, String recipient_id) {
		super();
		this.company_name = company_name;
		this.country = country;
		this.nature_of_business = nature_of_business;
		this.pan_no = pan_no;
		this.partner_id = partner_id;
		this.vendor_partner_id = vendor_partner_id;
		this.id = recipient_id;
	}
	public CustomerInfoModel() {
		super();
	}


}
