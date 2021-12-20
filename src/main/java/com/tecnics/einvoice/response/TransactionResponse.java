package com.tecnics.einvoice.response;

import java.util.List;

public class TransactionResponse {

	
	private Integer refid;
	
	private Integer totalTransactionsCount;
	private Integer successTransactionsCount;
	private Integer failureTransactionsCount;
	
	

	/**
	 * @return the refid
	 */
	public Integer getRefid() {
		return refid;
	}
	/**
	 * @param refid the refid to set
	 */
	public void setRefid(Integer refid) {
		this.refid = refid;
	}

	public Integer getTotalTransactionsCount() {
		return totalTransactionsCount;
	}
	public void setTotalTransactionsCount(Integer totalTransactionsCount) {
		this.totalTransactionsCount = totalTransactionsCount;
	}
	public Integer getSuccessTransactionsCount() {
		return successTransactionsCount;
	}
	public void setSuccessTransactionsCount(Integer successTransactionsCount) {
		this.successTransactionsCount = successTransactionsCount;
	}
	public Integer getFailureTransactionsCount() {
		return failureTransactionsCount;
	}
	public void setFailureTransactionsCount(Integer failureTransactionsCount) {
		this.failureTransactionsCount = failureTransactionsCount;
	}

	
}
