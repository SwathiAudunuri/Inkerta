package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the recipient_gstin_mapping database table.
 * 
 */
@Entity
@Table(name="recipient_gstin_mapping", schema = "einvoicing")
public class RecipientGstinMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	private String gstin;

	@Column(name="gstin_tag")
	private String gstinTag;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	//bi-directional many-to-one association to RecipientMapping
	@ManyToOne
	@JoinColumn(name="recipient_id")
	private RecipientMapping recipientMapping;

	public RecipientGstinMapping() {
	}

	public String getGstin() {
		return this.gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getGstinTag() {
		return this.gstinTag;
	}

	public void setGstinTag(String gstinTag) {
		this.gstinTag = gstinTag;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public RecipientMapping getRecipientMapping() {
//		return this.recipientMapping;
//	}

	public void setRecipientMapping(RecipientMapping recipientMapping) {
		this.recipientMapping = recipientMapping;
	}

}