package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the items_exchange_queue database table.
 * 
 */
@Entity
@Table(name="items_exchange_queue",schema = "einvoicing")
public class ItemsExchangeQueue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="batch_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer batchId;
	
	@CreationTimestamp
	@Column(name = "created_on",nullable = false, updatable = false, insertable = false)
	private Timestamp createdOn;

	@Column(name="delivery_mechanism")
	private String deliveryMechanism;

	@Column(name="ref_id")
	private String docSourceRefId;

	@Column(name="no_of_attempts")
	private Integer noOfAttempts;

	@Column(name="no_of_items")
	private Integer noOfItems;

	@Column(name="partner_transaction_id")
	private String partnerTransactionId;

	@Column(name="processed_on")
	private Timestamp processedOn;

	@Column(name="queue_name")
	private String queueName;

	private String status;

	//bi-directional many-to-one association to ItemsDeliveryDetail
	@OneToMany(mappedBy="itemsExchangeQueue")
	private List<ItemsDeliveryDetail> itemsDeliveryDetails;

	//bi-directional many-to-one association to ItemsExchangeError
	@OneToMany(mappedBy="itemsExchangeQueue")
	private List<ItemsExchangeError> itemsExchangeErrors;

	private String recipient_id;

	//bi-directional many-to-one association to ItemsUnprocessedReference
	@OneToMany(mappedBy="itemsExchangeQueue")
	private List<ItemsUnprocessedReference> itemsUnprocessedReferences;

	public ItemsExchangeQueue() {
	}

	public Integer getBatchId() {
		return this.batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getDeliveryMechanism() {
		return this.deliveryMechanism;
	}

	public void setDeliveryMechanism(String deliveryMechanism) {
		this.deliveryMechanism = deliveryMechanism;
	}

	public String getDocSourceRefId() {
		return this.docSourceRefId;
	}

	public void setDocSourceRefId(String docSourceRefId) {
		this.docSourceRefId = docSourceRefId;
	}

	public Integer getNoOfAttempts() {
		return this.noOfAttempts;
	}

	public void setNoOfAttempts(Integer noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}

	public Integer getNoOfItems() {
		return this.noOfItems;
	}

	public void setNoOfItems(Integer noOfItems) {
		this.noOfItems = noOfItems;
	}

	public String getPartnerTransactionId() {
		return this.partnerTransactionId;
	}

	public void setPartnerTransactionId(String partnerTransactionId) {
		this.partnerTransactionId = partnerTransactionId;
	}

	public Timestamp getProcessedOn() {
		return this.processedOn;
	}

	public void setProcessedOn(Timestamp processedOn) {
		this.processedOn = processedOn;
	}

	public String getQueueName() {
		return this.queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ItemsDeliveryDetail> getItemsDeliveryDetails() {
		return this.itemsDeliveryDetails;
	}

	public void setItemsDeliveryDetails(List<ItemsDeliveryDetail> itemsDeliveryDetails) {
		this.itemsDeliveryDetails = itemsDeliveryDetails;
	}

	public ItemsDeliveryDetail addItemsDeliveryDetail(ItemsDeliveryDetail itemsDeliveryDetail) {
		getItemsDeliveryDetails().add(itemsDeliveryDetail);
		itemsDeliveryDetail.setItemsExchangeQueue(this);

		return itemsDeliveryDetail;
	}

	public ItemsDeliveryDetail removeItemsDeliveryDetail(ItemsDeliveryDetail itemsDeliveryDetail) {
		getItemsDeliveryDetails().remove(itemsDeliveryDetail);
		itemsDeliveryDetail.setItemsExchangeQueue(null);

		return itemsDeliveryDetail;
	}

	public List<ItemsExchangeError> getItemsExchangeErrors() {
		return this.itemsExchangeErrors;
	}

	public void setItemsExchangeErrors(List<ItemsExchangeError> itemsExchangeErrors) {
		this.itemsExchangeErrors = itemsExchangeErrors;
	}

	public ItemsExchangeError addItemsExchangeError(ItemsExchangeError itemsExchangeError) {
		getItemsExchangeErrors().add(itemsExchangeError);
		itemsExchangeError.setItemsExchangeQueue(this);

		return itemsExchangeError;
	}

	public ItemsExchangeError removeItemsExchangeError(ItemsExchangeError itemsExchangeError) {
		getItemsExchangeErrors().remove(itemsExchangeError);
		itemsExchangeError.setItemsExchangeQueue(null);

		return itemsExchangeError;
	}

	
	public String getRecipient_id() {
		return recipient_id;
	}

	public void setRecipient_id(String recipient_id) {
		this.recipient_id = recipient_id;
	}

	public List<ItemsUnprocessedReference> getItemsUnprocessedReferences() {
		return this.itemsUnprocessedReferences;
	}

	public void setItemsUnprocessedReferences(List<ItemsUnprocessedReference> itemsUnprocessedReferences) {
		this.itemsUnprocessedReferences = itemsUnprocessedReferences;
	}

	public ItemsUnprocessedReference addItemsUnprocessedReference(ItemsUnprocessedReference itemsUnprocessedReference) {
		getItemsUnprocessedReferences().add(itemsUnprocessedReference);
		itemsUnprocessedReference.setItemsExchangeQueue(this);

		return itemsUnprocessedReference;
	}

	public ItemsUnprocessedReference removeItemsUnprocessedReference(ItemsUnprocessedReference itemsUnprocessedReference) {
		getItemsUnprocessedReferences().remove(itemsUnprocessedReference);
		itemsUnprocessedReference.setItemsExchangeQueue(null);

		return itemsUnprocessedReference;
	}

	@Override
	public String toString() {
		return "ItemsExchangeQueue [batchId=" + batchId + ", createdOn=" + createdOn + ", deliveryMechanism="
				+ deliveryMechanism + ", docSourceRefId=" + docSourceRefId + ", noOfAttempts=" + noOfAttempts
				+ ", noOfItems=" + noOfItems + ", partnerTransactionId=" + partnerTransactionId + ", processedOn="
				+ processedOn + ", queueName=" + queueName + ", status=" + status + ", itemsDeliveryDetails="
				+ itemsDeliveryDetails + ", itemsExchangeErrors=" + itemsExchangeErrors + ", recipient_id="
				+ recipient_id + ", itemsUnprocessedReferences=" + itemsUnprocessedReferences + "]";
	}
	
	

}