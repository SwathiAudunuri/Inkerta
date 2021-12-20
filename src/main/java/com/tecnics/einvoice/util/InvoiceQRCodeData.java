package com.tecnics.einvoice.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvoiceQRCodeData {
	 @JsonProperty("data")
	public InvoiceQRCode data;
	public String iss;
	public boolean isqrveified;
	public String qrverifiedstatus;
	public String reasonforfailure;
	public String base64;
	
	
	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
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

	public InvoiceQRCode getData() {
		return data;
	}

	public void setData(InvoiceQRCode data) {
		System.out.println("Set data is called");
		this.data = data;
	}

}
