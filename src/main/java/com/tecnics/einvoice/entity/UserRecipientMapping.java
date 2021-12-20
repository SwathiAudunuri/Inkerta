package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_recipient_mapping database table.
 * 
 */
@Entity
@Table(name="user_recipient_mapping", schema = "einvoicing")
public class UserRecipientMapping implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	//bi-directional many-to-one association to RecipientMapping
	@ManyToOne
	@JoinColumn(name="recipient_id")
	private RecipientMapping recipientMapping;

	//bi-directional many-to-one association to UserManagement
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserManagement userManagement;

	public UserRecipientMapping() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RecipientMapping getRecipientMapping() {
		return this.recipientMapping;
	}

	public void setRecipientMapping(RecipientMapping recipientMapping) {
		this.recipientMapping = recipientMapping;
	}

	public UserManagement getUserManagement() {
		return this.userManagement;
	}

	public void setUserManagement(UserManagement userManagement) {
		this.userManagement = userManagement;
	}

}