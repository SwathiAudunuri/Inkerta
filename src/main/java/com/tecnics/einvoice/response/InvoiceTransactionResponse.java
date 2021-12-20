package com.tecnics.einvoice.response;

import java.util.List;

public class InvoiceTransactionResponse {
	private Integer NoOfInvoiceSubmitted ;
	private Integer NoOfInvoicesProcessed ;
	private Integer NoOfInvoicesFailed ;
	
	private List<InvoiceResponseModel> invRespone;
	



	public Integer getNoOfInvoiceSubmitted() {
		return NoOfInvoiceSubmitted;
	}


	public void setNoOfInvoiceSubmitted(Integer noOfInvoiceSubmitted) {
		NoOfInvoiceSubmitted = noOfInvoiceSubmitted;
	}


	public Integer getNoOfInvoicesProcessed() {
		return NoOfInvoicesProcessed;
	}


	public void setNoOfInvoicesProcessed(Integer noOfInvoicesProcessed) {
		NoOfInvoicesProcessed = noOfInvoicesProcessed;
	}


	public Integer getNoOfInvoicesFailed() {
		return NoOfInvoicesFailed;
	}


	public void setNoOfInvoicesFailed(Integer noOfInvoicesFailed) {
		NoOfInvoicesFailed = noOfInvoicesFailed;
	}

	

	public List<InvoiceResponseModel> getInvRespone() {
		return invRespone;
	}


	public void setInvRespone(List<InvoiceResponseModel> invRespone) {
		this.invRespone = invRespone;
	}


	@Override
	public String toString() {
		return "InvoiceTransactionResponse [NoOfInvoiceSubmitted=" + NoOfInvoiceSubmitted + ", NoOfInvoicesProcessed="
				+ NoOfInvoicesProcessed + ", NoOfInvoicesFailed=" + NoOfInvoicesFailed + ", invRespone=" + invRespone
				+ "]";
	}


	

	
	
}
