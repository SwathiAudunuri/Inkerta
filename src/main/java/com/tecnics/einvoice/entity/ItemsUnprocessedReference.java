package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the items_unprocessed_references database table.
 * 
 */
@Entity
@Table(name="items_unprocessed_references", schema = "einvoicing")
public class ItemsUnprocessedReference implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name="parent_batch_id")
	private Integer parentBatchId;

	//bi-directional many-to-one association to ItemsExchangeQueue
	@ManyToOne
	@JoinColumn(name="batch_id")
	private ItemsExchangeQueue itemsExchangeQueue;

	public ItemsUnprocessedReference() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentBatchId() {
		return this.parentBatchId;
	}

	public void setParentBatchId(Integer parentBatchId) {
		this.parentBatchId = parentBatchId;
	}

	public ItemsExchangeQueue getItemsExchangeQueue() {
		return this.itemsExchangeQueue;
	}

	public void setItemsExchangeQueue(ItemsExchangeQueue itemsExchangeQueue) {
		this.itemsExchangeQueue = itemsExchangeQueue;
	}

}