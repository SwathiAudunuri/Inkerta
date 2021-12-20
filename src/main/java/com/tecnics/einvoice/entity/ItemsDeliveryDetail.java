package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the items_delivery_details database table.
 * 
 */
@Entity
@Table(name="items_delivery_details", schema = "einvoicing")
public class ItemsDeliveryDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="attempted_on")
	private Timestamp attemptedOn;

	@Column(name="delivery_mode")
	private String deliveryMode;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	private String status;

	@Column(name="user_id")
	private String userId;

	//bi-directional many-to-one association to ItemsExchangeQueue
	@ManyToOne
	@JoinColumn(name="batch_id")
	private ItemsExchangeQueue itemsExchangeQueue;

	public ItemsDeliveryDetail() {
	}

	public Timestamp getAttemptedOn() {
		return this.attemptedOn;
	}

	public void setAttemptedOn(Timestamp attemptedOn) {
		this.attemptedOn = attemptedOn;
	}

	public String getDeliveryMode() {
		return this.deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ItemsExchangeQueue getItemsExchangeQueue() {
		return this.itemsExchangeQueue;
	}

	public void setItemsExchangeQueue(ItemsExchangeQueue itemsExchangeQueue) {
		this.itemsExchangeQueue = itemsExchangeQueue;
	}

}