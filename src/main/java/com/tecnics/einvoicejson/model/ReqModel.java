package com.tecnics.einvoicejson.model;
import java.util.List;

public class ReqModel {

	public InvoiceDetails invoiceDetails;
	public InvoiceSupplierBuyerDetails invoiceSupplierBuyerDetails;
	public InvoiceDispatchShiptoDetails invoiceDispatchShiptoDetails;
	public InvoiceSellerPaymentDetails invoiceSellerPaymentDetails;
	public InvoiceEwayBillDetails invoiceEwayBillDetails;
	public List<InvoiceAttachmentDetail> invoiceAttachmentDetails;


	public List<InvoiceAttachmentDetail> getInvoiceAttachmentDetails() {
		return invoiceAttachmentDetails;
	}

	public void setInvoiceAttachmentDetails(List<InvoiceAttachmentDetail> invoiceAttachmentDetails) {
		this.invoiceAttachmentDetails = invoiceAttachmentDetails;
	}

	public InvoiceDetails getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(InvoiceDetails invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public InvoiceSupplierBuyerDetails getInvoiceSupplierBuyerDetails() {
		return invoiceSupplierBuyerDetails;
	}

	public void setInvoiceSupplierBuyerDetails(InvoiceSupplierBuyerDetails invoiceSupplierBuyerDetails) {
		this.invoiceSupplierBuyerDetails = invoiceSupplierBuyerDetails;
	}

	public InvoiceDispatchShiptoDetails getInvoiceDispatchShiptoDetails() {
		return invoiceDispatchShiptoDetails;
	}

	public void setInvoiceDispatchShiptoDetails(InvoiceDispatchShiptoDetails invoiceDispatchShiptoDetails) {
		this.invoiceDispatchShiptoDetails = invoiceDispatchShiptoDetails;
	}

	public InvoiceSellerPaymentDetails getInvoiceSellerPaymentDetails() {
		return invoiceSellerPaymentDetails;
	}

	public void setInvoiceSellerPaymentDetails(InvoiceSellerPaymentDetails invoiceSellerPaymentDetails) {
		this.invoiceSellerPaymentDetails = invoiceSellerPaymentDetails;
	}

	public InvoiceEwayBillDetails getInvoiceEwayBillDetails() {
		return invoiceEwayBillDetails;
	}

	public void setInvoiceEwayBillDetails(InvoiceEwayBillDetails invoiceEwayBillDetails) {
		this.invoiceEwayBillDetails = invoiceEwayBillDetails;
	}

	public ReqModel(InvoiceDetails invoiceDetails, InvoiceSupplierBuyerDetails invoiceSupplierBuyerDetails,
			InvoiceDispatchShiptoDetails invoiceDispatchShiptoDetails,
			InvoiceSellerPaymentDetails invoiceSellerPaymentDetails, InvoiceEwayBillDetails invoiceEwayBillDetails) {
		super();
		this.invoiceDetails = invoiceDetails;
		this.invoiceSupplierBuyerDetails = invoiceSupplierBuyerDetails;
		this.invoiceDispatchShiptoDetails = invoiceDispatchShiptoDetails;
		this.invoiceSellerPaymentDetails = invoiceSellerPaymentDetails;
		this.invoiceEwayBillDetails = invoiceEwayBillDetails;
	}

	public ReqModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ReqModel [invoiceDetails=" + invoiceDetails + ", invoiceSupplierBuyerDetails="
				+ invoiceSupplierBuyerDetails + ", invoiceDispatchShiptoDetails=" + invoiceDispatchShiptoDetails
				+ ", invoiceSellerPaymentDetails=" + invoiceSellerPaymentDetails + ", invoiceEwayBillDetails="
				+ invoiceEwayBillDetails + "]";
	}

}
