package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the items_exchange_errors database table.
 * 
 */
@Entity
@Table(name="items_exchange_errors" , schema = "einvoicing")
public class ItemsExchangeError implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="delivery_attempt")
	private Integer deliveryAttempt;

	@Column(name="error_msg")
	private String errorMsg;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	//bi-directional many-to-one association to ItemsExchangeQueue
	@ManyToOne
	@JoinColumn(name="batch_id")
	private ItemsExchangeQueue itemsExchangeQueue;

	public ItemsExchangeError() {
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getDeliveryAttempt() {
		return this.deliveryAttempt;
	}

	public void setDeliveryAttempt(Integer deliveryAttempt) {
		this.deliveryAttempt = deliveryAttempt;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ItemsExchangeQueue getItemsExchangeQueue() {
		return this.itemsExchangeQueue;
	}

	public void setItemsExchangeQueue(ItemsExchangeQueue itemsExchangeQueue) {
		this.itemsExchangeQueue = itemsExchangeQueue;
	}

}