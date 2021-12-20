package com.tecnics.einvoice.alfresco;

public class InvoiceFolderCreate {
	private String customerPartnerId;
	private String documentRefId;
	private String invoiceNumber;
	private String invoiceType;
	private String vendorPartnerId;

	public String getCustomerPartnerId() {
		return customerPartnerId;
	}

	public void setCustomerPartnerId(String customerPartnerId) {
		this.customerPartnerId = customerPartnerId;
	}

	public String getDocumentRefId() {
		return documentRefId;
	}

	public void setDocumentRefId(String documentRefId) {
		this.documentRefId = documentRefId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getVendorPartnerId() {
		return vendorPartnerId;
	}

	public void setVendorPartnerId(String vendorPartnerId) {
		this.vendorPartnerId = vendorPartnerId;
	}

	public InvoiceFolderCreate(String customerPartnerId, String documentRefId, String invoiceNumber, String invoiceType,
			String vendorPartnerId) {
		super();
		this.customerPartnerId = customerPartnerId;
		this.documentRefId = documentRefId;
		this.invoiceNumber = invoiceNumber;
		this.invoiceType = invoiceType;
		this.vendorPartnerId = vendorPartnerId;
	}

	public InvoiceFolderCreate() {
		super();
	}

	@Override
	public String toString() {
		return "InvoiceFolderCreate [customerPartnerId=" + customerPartnerId + ", documentRefId=" + documentRefId
				+ ", invoiceNumber=" + invoiceNumber + ", invoiceType=" + invoiceType + ", vendorPartnerId="
				+ vendorPartnerId + "]";
	}

}
