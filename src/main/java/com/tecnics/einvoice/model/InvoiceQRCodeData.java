package com.tecnics.einvoice.model;

import com.fasterxml.jackson.annotation.JsonProperty;



public class InvoiceQRCodeData {

	public InvoiceQRCode qrcodedata;
	public InvoiceQRCode invoicemetadata;
	public String iss;
	public boolean isqrveified;
	public String qrverifiedstatus;
	public String reasonforfailure;
	public String qrcodebase64;
	
	
	public String getQrcodebase64() {
		return qrcodebase64;
	}

	public void setQrcodebase64(String qrcodebase64) {
		this.qrcodebase64 = qrcodebase64;
	}

	public boolean isIsqrveified() {
		return isqrveified;
	}

	public void setIsqrveified(boolean isqrveified) {
		this.isqrveified = isqrveified;
	}

	public String getQrverifiedstatus() {
		return qrverifiedstatus;
	}

	public void setQrverifiedstatus(String qrverifiedstatus) {
		this.qrverifiedstatus = qrverifiedstatus;
	}

	public String getReasonforfailure() {
		return reasonforfailure;
	}

	public void setReasonforfailure(String reasonforfailure) {
		this.reasonforfailure = reasonforfailure;
	}

	public InvoiceQRCodeData()
	{
		
	}

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public InvoiceQRCode getQrcodedata() {
		return qrcodedata;
	}

	public void setQrcodedata(InvoiceQRCode qrcodedata) {
		System.out.println("Set data is called");
		this.qrcodedata = qrcodedata;
	}
	
	public InvoiceQRCode getInvoicemetadata() {
		return invoicemetadata;
	}

	public void setInvoicemetadata(InvoiceQRCode invoicemetadata) {
		System.out.println("Set data is called");
		this.invoicemetadata = invoicemetadata;
	}

}
