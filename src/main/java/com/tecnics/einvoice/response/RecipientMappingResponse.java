package com.tecnics.einvoice.response;

import java.util.List;

public class RecipientMappingResponse {

	private String recipientMappingId;
	private List<Integer> recipientMappingActivitiesID;
	private List<Integer> recipientMappingGstnID;
	private Integer totalTransactionsCount;
	private Integer successTransactionsCount;
	private Integer failureTransactionsCount;

	/**
	 * @return the recipientMappingId
	 */
	public String getRecipientMappingId() {
		return recipientMappingId;
	}

	/**
	 * @return the totalTransactionsCount
	 */
	public Integer getTotalTransactionsCount() {
		return totalTransactionsCount;
	}

	/**
	 * @return the recipientMappingActivitiesID
	 */
	public List<Integer> getRecipientMappingActivitiesID() {
		return recipientMappingActivitiesID;
	}

	/**
	 * @param recipientMappingActivitiesID the recipientMappingActivitiesID to set
	 */
	public void setRecipientMappingActivitiesID(List<Integer> recipientMappingActivitiesID) {
		this.recipientMappingActivitiesID = recipientMappingActivitiesID;
	}

	/**
	 * @param recipientMappingId the recipientMappingId to set
	 */
	public void setRecipientMappingId(String recipientMappingId) {
		this.recipientMappingId = recipientMappingId;
	}

	@Override
	public String toString() {
		return "RecipientMappingResponse [recipientMappingId=" + recipientMappingId + ", recipientMappingActivitiesID="
				+ recipientMappingActivitiesID + ", totalTransactionsCount=" + totalTransactionsCount
				+ ", successTransactionsCount=" + successTransactionsCount + ", failureTransactionsCount="
				+ failureTransactionsCount + "]";
	}

	/**
	 * @param totalTransactionsCount the totalTransactionsCount to set
	 */
	public void setTotalTransactionsCount(Integer totalTransactionsCount) {
		this.totalTransactionsCount = totalTransactionsCount;
	}

	/**
	 * @return the successTransactionsCount
	 */
	public Integer getSuccessTransactionsCount() {
		return successTransactionsCount;
	}

	/**
	 * @param successTransactionsCount the successTransactionsCount to set
	 */
	public void setSuccessTransactionsCount(Integer successTransactionsCount) {
		this.successTransactionsCount = successTransactionsCount;
	}

	/**
	 * @return the failureTransactionsCount
	 */
	public Integer getFailureTransactionsCount() {
		return failureTransactionsCount;
	}

	/**
	 * @param failureTransactionsCount the failureTransactionsCount to set
	 */
	public void setFailureTransactionsCount(Integer failureTransactionsCount) {
		this.failureTransactionsCount = failureTransactionsCount;
	}

	/**
	 * @return the recipientMappingGstnID
	 */
	public List<Integer> getRecipientMappingGstnID() {
		return recipientMappingGstnID;
	}

	/**
	 * @param recipientMappingGstnID the recipientMappingGstnID to set
	 */
	public void setRecipientMappingGstnID(List<Integer> recipientMappingGstnID) {
		this.recipientMappingGstnID = recipientMappingGstnID;
	}

}
