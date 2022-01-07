package com.tecnics.einvoice.entity;

import java.util.List;

import com.tecnics.einvoice.model.InvoiceAction;
import com.tecnics.einvoice.model.InvoiceMetaDataModel;

public class InvoiceRequestModel  {
	private InvoiceMetaDataModel invoiceDetails;

	private InvoiceSupplierBuyerInfo invoiceSupplierBuyerDetails;

	private InvoiceDispatchShiptoDetail invoiceDispatchShiptoDetails;
	
	private List<InvoiceAttachmentDetail> invoiceAttachmentDetails;
	
	private InvoiceEwayBillDetail invoiceEwayBillDetails;
	
	private InvoiceReferenceDetails invoiceReferenceDetails;
	
	private InvoiceSellerPaymentInformation invoiceSellerPaymentDetails;
	
	private InvoiceAction actionDetails;

	private List<InvoiceItemList> lineItemDetails;


	public InvoiceRequestModel() {
		super();
	}
	
	
	public InvoiceRequestModel(InvoiceMetaDataModel invoiceDetails,
			InvoiceSupplierBuyerInfo invoiceSupplierBuyerDetails,
			InvoiceDispatchShiptoDetail invoiceDispatchShiptoDetails,
			List<InvoiceAttachmentDetail> invoiceAttachmentDetails, InvoiceEwayBillDetail invoiceEwayBillDetails,
			InvoiceSellerPaymentInformation invoiceSellerPaymentDetails, InvoiceAction actionDetails,
			List<InvoiceItemList> lineItemDetails, InvoiceReferenceDetails invoiceReferenceDetails) {
		super();
		this.invoiceDetails = invoiceDetails;
		this.invoiceSupplierBuyerDetails = invoiceSupplierBuyerDetails;
		this.invoiceDispatchShiptoDetails = invoiceDispatchShiptoDetails;
		this.invoiceAttachmentDetails = invoiceAttachmentDetails;
		this.invoiceEwayBillDetails = invoiceEwayBillDetails;
		this.invoiceSellerPaymentDetails = invoiceSellerPaymentDetails;
		this.actionDetails = actionDetails;
		this.lineItemDetails = lineItemDetails;
		this.invoiceReferenceDetails=invoiceReferenceDetails;
	}

	public InvoiceAction getActionDetails() {
		return actionDetails;
	}

	public List<InvoiceAttachmentDetail> getInvoiceAttachmentDetails() {
		return invoiceAttachmentDetails;
	}
	
	

	public InvoiceMetaDataModel getInvoiceDetails() {
		return invoiceDetails;
	}


	public InvoiceDispatchShiptoDetail getInvoiceDispatchShiptoDetails() {
		return invoiceDispatchShiptoDetails;
	}


	public InvoiceEwayBillDetail getInvoiceEwayBillDetails() {
		return invoiceEwayBillDetails;
	}


	public InvoiceSellerPaymentInformation getInvoiceSellerPaymentDetails() {
		return invoiceSellerPaymentDetails;
	}


	public InvoiceSupplierBuyerInfo getInvoiceSupplierBuyerDetails() {
		return invoiceSupplierBuyerDetails;
	}


	public List<InvoiceItemList> getLineItemDetails() {
		return lineItemDetails;
	}

	public InvoiceReferenceDetails getInvoiceReferenceDetails() {
		return invoiceReferenceDetails;
	}
	
	public void setInvoiceReferenceDetails(InvoiceReferenceDetails invoiceReferenceDetails) {
		this.invoiceReferenceDetails = invoiceReferenceDetails;
	}

	public void setActionDetails(InvoiceAction actionDetails) {
		this.actionDetails = actionDetails;
	}


	public void setInvoiceAttachmentDetails(List<InvoiceAttachmentDetail> invoiceAttachmentDetails) {
		this.invoiceAttachmentDetails = invoiceAttachmentDetails;
	}


	public void setInvoiceDetails(InvoiceMetaDataModel invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}


	public void setInvoiceDispatchShiptoDetails(InvoiceDispatchShiptoDetail invoiceDispatchShiptoDetails) {
		this.invoiceDispatchShiptoDetails = invoiceDispatchShiptoDetails;
	}


	public void setInvoiceEwayBillDetails(InvoiceEwayBillDetail invoiceEwayBillDetails) {
		this.invoiceEwayBillDetails = invoiceEwayBillDetails;
	}


	public void setInvoiceSellerPaymentDetails(InvoiceSellerPaymentInformation invoiceSellerPaymentDetails) {
		this.invoiceSellerPaymentDetails = invoiceSellerPaymentDetails;
	}


	public void setInvoiceSupplierBuyerDetails(InvoiceSupplierBuyerInfo invoiceSupplierBuyerDetails) {
		this.invoiceSupplierBuyerDetails = invoiceSupplierBuyerDetails;
	}


	public void setLineItemDetails(List<InvoiceItemList> lineItemDetails) {
		this.lineItemDetails = lineItemDetails;
	}


	@Override
	public String toString() {
		return "InvoiceRequestModel [invoiceDetails=" + invoiceDetails + ", invoiceSupplierBuyerDetails="
				+ invoiceSupplierBuyerDetails + ", invoiceDispatchShiptoDetails=" + invoiceDispatchShiptoDetails
				+ ", invoiceAttachmentDetails=" + invoiceAttachmentDetails + ", invoiceEwayBillDetails="
				+ invoiceEwayBillDetails + ", invoiceSellerPaymentDetails=" + invoiceSellerPaymentDetails
				+ ", actionDetails=" + actionDetails + ", lineItemDetails=" + lineItemDetails 
				+ ", invoiceReferenceDetails=" + invoiceReferenceDetails + "]";
	}




 
	

}