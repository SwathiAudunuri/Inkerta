package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the recipient_webservice_mapping database table.
 * 
 */
@Entity
@Table(name="recipient_webservice_mapping", schema = "einvoicing")
public class RecipientWebserviceMapping implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String password;

	private String url;

	@Column(name="user_name")
	private String userName;

	//bi-directional many-to-one association to RecipientMapping
	@ManyToOne
	@JoinColumn(name="recipient_id")
	private RecipientMapping recipientMapping;

	public RecipientWebserviceMapping() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

//	public RecipientMapping getRecipientMapping() {
//		return this.recipientMapping;
//	}

	public void setRecipientMapping(RecipientMapping recipientMapping) {
		this.recipientMapping = recipientMapping;
	}

}