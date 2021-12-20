package com.tecnics.einvoice.entity;

import java.util.List;

public class InvoicePullResponse {
	List<InvoiceRequestModel> invoices;
	public boolean moreBatchesAvailable;

	@Override
	public String toString() {
		return "InvoicePullResponse [invoices=" + invoices + ", moreBatchesAvailable=" + moreBatchesAvailable + "]";
	}

	public List<InvoiceRequestModel> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<InvoiceRequestModel> invoices) {
		this.invoices = invoices;
	}

	public boolean isMoreBatchesAvailable() {
		return moreBatchesAvailable;
	}

	public void setMoreBatchesAvailable(boolean moreBatchesAvailable) {
		this.moreBatchesAvailable = moreBatchesAvailable;
	}

	public InvoicePullResponse(List<InvoiceRequestModel> invoices, boolean moreBatchesAvailable) {
		super();
		this.invoices = invoices;
		this.moreBatchesAvailable = moreBatchesAvailable;
	}

	public InvoicePullResponse() {
		super();
	}

}
