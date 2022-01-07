package com.tecnics.einvoice.model;

import java.util.List;

import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.CashFlowResults;

public class CashFlowReceivablesPayablesResults {
	
	List<CashFlowResults> receivables;
	List<CashFlowResults> payables;
	public List<CashFlowResults> getReceivables() {
		return receivables;
	}
	public void setReceivables(List<CashFlowResults> receivables) {
		this.receivables = receivables;
	}
	public List<CashFlowResults> getPayables() {
		return payables;
	}
	public void setPayables(List<CashFlowResults> payables) {
		this.payables = payables;
	}
	

}
