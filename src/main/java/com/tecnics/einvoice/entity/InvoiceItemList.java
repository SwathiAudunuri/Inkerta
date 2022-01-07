package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the invoice_item_list database table.
 * 
 */
@Entity
@Table(name="invoice_item_list", schema = "einvoicing")
public class InvoiceItemList implements Serializable {
	private static final long serialVersionUID = 1L;

	private String barcode;

	@Temporal(TemporalType.DATE)
	private Date batch_expiry_date;

	private String batch_name;

	private BigDecimal cgst_amt;

	private BigDecimal comp_cess_amt_ad_valorem;

	private BigDecimal comp_cess_amt_non_ad_valorem;

	private BigDecimal comp_cess_rate_ad_valorem;

	private BigDecimal free_qty;

	private BigDecimal gross_amount;

	private BigDecimal gst_rate;

	private String hsn_code;

	private BigDecimal igst_amt;

	private Boolean is_service;

	private String item_description;

	private BigDecimal item_discount_amount;

	private BigDecimal item_price;

	private BigDecimal item_taxable_value;

	private BigDecimal item_total_amt;

	private BigDecimal other_charges_item_level;

	private BigDecimal pre_tax_value;

	private BigDecimal quantity;

	private BigDecimal sgst_utgst_amt;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private BigDecimal state_cess_amt_ad_valorem;

	private BigDecimal state_cess_amt_non_ad_valorem;

	private BigDecimal state_cess_rate_ad_valorem;

	private String unit_of_measurement;

	@Temporal(TemporalType.DATE)
	private Date warranty_date;

	private String document_ref_id;

	@Override
	public String toString() {
		return "InvoiceItemList [barcode=" + barcode + ", batch_expiry_date=" + batch_expiry_date + ", batch_number="
				+ batch_name + ", cgst_amt=" + cgst_amt + ", comp_cess_amt_ad_valorem=" + comp_cess_amt_ad_valorem
				+ ", comp_cess_amt_non_ad_valorem=" + comp_cess_amt_non_ad_valorem + ", comp_cess_rate_ad_valorem="
				+ comp_cess_rate_ad_valorem + ", free_qty=" + free_qty + ", gross_amount=" + gross_amount
				+ ", gst_rate=" + gst_rate + ", hsn_code=" + hsn_code + ", igst_amt=" + igst_amt + ", is_service="
				+ is_service + ", item_description=" + item_description + ", item_discount_amount="
				+ item_discount_amount + ", item_price=" + item_price + ", item_taxable_value=" + item_taxable_value
				+ ", item_total_amt=" + item_total_amt + ", other_charges_item_level=" + other_charges_item_level
				+ ", pre_tax_value=" + pre_tax_value + ", quantity=" + quantity + ", sgst_utgst_amt=" + sgst_utgst_amt
				+ ", sl_no=" + id + ", state_cess_amt_ad_valorem=" + state_cess_amt_ad_valorem
				+ ", state_cess_amt_non_ad_valorem=" + state_cess_amt_non_ad_valorem + ", state_cess_rate_ad_valorem="
				+ state_cess_rate_ad_valorem + ", unit_of_measurement=" + unit_of_measurement + ", warranty_date="
				+ warranty_date + ", document_ref_id=" + document_ref_id + "]";
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Date getBatch_expiry_date() {
		return batch_expiry_date;
	}

	public void setBatch_expiry_date(Date batch_expiry_date) {
		this.batch_expiry_date = batch_expiry_date;
	}

	public String getBatch_name() {
		return batch_name;
	}

	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}

	public BigDecimal getCgst_amt() {
		return cgst_amt;
	}

	public void setCgst_amt(BigDecimal cgst_amt) {
		this.cgst_amt = cgst_amt;
	}

	public BigDecimal getComp_cess_amt_ad_valorem() {
		return comp_cess_amt_ad_valorem;
	}

	public void setComp_cess_amt_ad_valorem(BigDecimal comp_cess_amt_ad_valorem) {
		this.comp_cess_amt_ad_valorem = comp_cess_amt_ad_valorem;
	}

	public BigDecimal getComp_cess_amt_non_ad_valorem() {
		return comp_cess_amt_non_ad_valorem;
	}

	public void setComp_cess_amt_non_ad_valorem(BigDecimal comp_cess_amt_non_ad_valorem) {
		this.comp_cess_amt_non_ad_valorem = comp_cess_amt_non_ad_valorem;
	}

	public BigDecimal getComp_cess_rate_ad_valorem() {
		return comp_cess_rate_ad_valorem;
	}

	public void setComp_cess_rate_ad_valorem(BigDecimal comp_cess_rate_ad_valorem) {
		this.comp_cess_rate_ad_valorem = comp_cess_rate_ad_valorem;
	}

	public BigDecimal getFree_qty() {
		return free_qty;
	}

	public void setFree_qty(BigDecimal free_qty) {
		this.free_qty = free_qty;
	}

	public BigDecimal getGross_amount() {
		return gross_amount;
	}

	public void setGross_amount(BigDecimal gross_amount) {
		this.gross_amount = gross_amount;
	}

	public BigDecimal getGst_rate() {
		return gst_rate;
	}

	public void setGst_rate(BigDecimal gst_rate) {
		this.gst_rate = gst_rate;
	}

	public String getHsn_code() {
		return hsn_code;
	}

	public void setHsn_code(String hsn_code) {
		this.hsn_code = hsn_code;
	}

	public BigDecimal getIgst_amt() {
		return igst_amt;
	}

	public void setIgst_amt(BigDecimal igst_amt) {
		this.igst_amt = igst_amt;
	}

	public Boolean getIs_service() {
		return is_service;
	}

	public void setIs_service(Boolean is_service) {
		this.is_service = is_service;
	}

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}

	public BigDecimal getItem_discount_amount() {
		return item_discount_amount;
	}

	public void setItem_discount_amount(BigDecimal item_discount_amount) {
		this.item_discount_amount = item_discount_amount;
	}

	public BigDecimal getItem_price() {
		return item_price;
	}

	public void setItem_price(BigDecimal item_price) {
		this.item_price = item_price;
	}

	public BigDecimal getItem_taxable_value() {
		return item_taxable_value;
	}

	public void setItem_taxable_value(BigDecimal item_taxable_value) {
		this.item_taxable_value = item_taxable_value;
	}

	public BigDecimal getItem_total_amt() {
		return item_total_amt;
	}

	public void setItem_total_amt(BigDecimal item_total_amt) {
		this.item_total_amt = item_total_amt;
	}

	public BigDecimal getOther_charges_item_level() {
		return other_charges_item_level;
	}

	public void setOther_charges_item_level(BigDecimal other_charges_item_level) {
		this.other_charges_item_level = other_charges_item_level;
	}

	public BigDecimal getPre_tax_value() {
		return pre_tax_value;
	}

	public void setPre_tax_value(BigDecimal pre_tax_value) {
		this.pre_tax_value = pre_tax_value;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSgst_utgst_amt() {
		return sgst_utgst_amt;
	}

	public void setSgst_utgst_amt(BigDecimal sgst_utgst_amt) {
		this.sgst_utgst_amt = sgst_utgst_amt;
	}


	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getState_cess_amt_ad_valorem() {
		return state_cess_amt_ad_valorem;
	}

	public void setState_cess_amt_ad_valorem(BigDecimal state_cess_amt_ad_valorem) {
		this.state_cess_amt_ad_valorem = state_cess_amt_ad_valorem;
	}

	public BigDecimal getState_cess_amt_non_ad_valorem() {
		return state_cess_amt_non_ad_valorem;
	}

	public void setState_cess_amt_non_ad_valorem(BigDecimal state_cess_amt_non_ad_valorem) {
		this.state_cess_amt_non_ad_valorem = state_cess_amt_non_ad_valorem;
	}

	public BigDecimal getState_cess_rate_ad_valorem() {
		return state_cess_rate_ad_valorem;
	}

	public void setState_cess_rate_ad_valorem(BigDecimal state_cess_rate_ad_valorem) {
		this.state_cess_rate_ad_valorem = state_cess_rate_ad_valorem;
	}

	public String getUnit_of_measurement() {
		return unit_of_measurement;
	}

	public void setUnit_of_measurement(String unit_of_measurement) {
		this.unit_of_measurement = unit_of_measurement;
	}

	public Date getWarranty_date() {
		return warranty_date;
	}

	public void setWarranty_date(Date warranty_date) {
		this.warranty_date = warranty_date;
	}

	public String getDocument_ref_id() {
		return document_ref_id;
	}

	public void setDocument_ref_id(String document_ref_id) {
		this.document_ref_id = document_ref_id;
	}


}