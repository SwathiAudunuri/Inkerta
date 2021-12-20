package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the recipient_email_mapping database table.
 * 
 */
@Entity
@Table(name="recipient_email_mapping", schema = "einvoicing")
public class RecipientEmailMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="email_address")
	private String emailAddress;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

//	//bi-directional many-to-one association to RecipientMapping
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name="recipient_id")
//	private RecipientMapping recipientMapping;

	public RecipientEmailMapping() {
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	//bi-directional many-to-one association to RecipientMapping
		@ManyToOne
		@JoinColumn(name="recipient_id")
		private RecipientMapping recipientMapping;

//	public RecipientMapping getRecipientMapping() {
//		return this.recipientMapping;
//	}

	public void setRecipientMapping(RecipientMapping recipientMapping) {
		this.recipientMapping = recipientMapping;
	}

}