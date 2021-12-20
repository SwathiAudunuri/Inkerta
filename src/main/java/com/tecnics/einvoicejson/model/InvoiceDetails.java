package com.tecnics.einvoicejson.model;

public class InvoiceDetails {
	 public String tax_scheme;
	 public String supply_type_code;
	 public String ecom_gstin;
	 public String igst_on_intra;
	 public String invoice_type_code;
	 public String invoicenum;
	 public String invoicedate;
	 public String irn;
	 public double total_assessable_value;
	 public int cgstvalue;
	 public int sgstvalue;
	 public double igstvalue;
	 public double cessvalue;
	 public double statecessvalue;
	 public int discount;
	 public int other_charges;
	 public double roundoff;
	 public int total_invoice_value;
	 public double total_invoice_value_fc;
	 public String recipientCode;
	public String getRecipientCode() {
		return recipientCode;
	}
	public void setRecipientCode(String recipientCode) {
		this.recipientCode = recipientCode;
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
	public String getEcom_gstin() {
		return ecom_gstin;
	}
	public void setEcom_gstin(String ecom_gstin) {
		this.ecom_gstin = ecom_gstin;
	}
	public String getIgst_on_intra() {
		return igst_on_intra;
	}
	public void setIgst_on_intra(String igst_on_intra) {
		this.igst_on_intra = igst_on_intra;
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
	public String getInvoicedate() {
		return invoicedate;
	}
	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
	}
	public String getIrn() {
		return irn;
	}
	public void setIrn(String irn) {
		this.irn = irn;
	}
	public double getTotal_assessable_value() {
		return total_assessable_value;
	}
	public void setTotal_assessable_value(double total_assessable_value) {
		this.total_assessable_value = total_assessable_value;
	}
	public int getCgstvalue() {
		return cgstvalue;
	}
	public void setCgstvalue(int cgstvalue) {
		this.cgstvalue = cgstvalue;
	}
	public int getSgstvalue() {
		return sgstvalue;
	}
	public void setSgstvalue(int sgstvalue) {
		this.sgstvalue = sgstvalue;
	}
	public double getIgstvalue() {
		return igstvalue;
	}
	public void setIgstvalue(double igstvalue) {
		this.igstvalue = igstvalue;
	}
	public double getCessvalue() {
		return cessvalue;
	}
	public void setCessvalue(double cessvalue) {
		this.cessvalue = cessvalue;
	}
	public double getStatecessvalue() {
		return statecessvalue;
	}
	public void setStatecessvalue(double statecessvalue) {
		this.statecessvalue = statecessvalue;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getOther_charges() {
		return other_charges;
	}
	public void setOther_charges(int other_charges) {
		this.other_charges = other_charges;
	}
	public double getRoundoff() {
		return roundoff;
	}
	public void setRoundoff(double roundoff) {
		this.roundoff = roundoff;
	}
	public int getTotal_invoice_value() {
		return total_invoice_value;
	}
	public void setTotal_invoice_value(int total_invoice_value) {
		this.total_invoice_value = total_invoice_value;
	}
	public double getTotal_invoice_value_fc() {
		return total_invoice_value_fc;
	}
	public void setTotal_invoice_value_fc(double total_invoice_value_fc) {
		this.total_invoice_value_fc = total_invoice_value_fc;
	}
	public InvoiceDetails(String tax_scheme, String supply_type_code, String ecom_gstin, String igst_on_intra,
			String invoice_type_code, String invoicenum, String invoicedate, String irn, double total_assessable_value,
			int cgstvalue, int sgstvalue, double igstvalue, double cessvalue, double statecessvalue, int discount,
			int other_charges, double roundoff, int total_invoice_value, double total_invoice_value_fc) {
		super();
		this.tax_scheme = tax_scheme;
		this.supply_type_code = supply_type_code;
		this.ecom_gstin = ecom_gstin;
		this.igst_on_intra = igst_on_intra;
		this.invoice_type_code = invoice_type_code;
		this.invoicenum = invoicenum;
		this.invoicedate = invoicedate;
		this.irn = irn;
		this.total_assessable_value = total_assessable_value;
		this.cgstvalue = cgstvalue;
		this.sgstvalue = sgstvalue;
		this.igstvalue = igstvalue;
		this.cessvalue = cessvalue;
		this.statecessvalue = statecessvalue;
		this.discount = discount;
		this.other_charges = other_charges;
		this.roundoff = roundoff;
		this.total_invoice_value = total_invoice_value;
		this.total_invoice_value_fc = total_invoice_value_fc;
	}
	public InvoiceDetails() {
		super();
	}
	@Override
	public String toString() {
		return "InvoiceDetails [tax_scheme=" + tax_scheme + ", supply_type_code=" + supply_type_code + ", ecom_gstin="
				+ ecom_gstin + ", igst_on_intra=" + igst_on_intra + ", invoice_type_code=" + invoice_type_code
				+ ", invoicenum=" + invoicenum + ", invoicedate=" + invoicedate + ", irn=" + irn
				+ ", total_assessable_value=" + total_assessable_value + ", cgstvalue=" + cgstvalue + ", sgstvalue="
				+ sgstvalue + ", igstvalue=" + igstvalue + ", cessvalue=" + cessvalue + ", statecessvalue="
				+ statecessvalue + ", discount=" + discount + ", other_charges=" + other_charges + ", roundoff="
				+ roundoff + ", total_invoice_value=" + total_invoice_value + ", total_invoice_value_fc="
				+ total_invoice_value_fc + "]";
	}
	 
}
