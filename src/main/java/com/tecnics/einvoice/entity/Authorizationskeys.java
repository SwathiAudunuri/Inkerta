package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_authorization_keys database table.
 * 
 */
@Entity
@Table(name="user_authorization_keys" , schema = "einvoicing")
public class Authorizationskeys implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="authorization_key")
	private String authorizationKey;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	//bi-directional many-to-one association to UserManagement
	//@ManyToOne
	@Column(name="user_id")
	private String userId;

	public Authorizationskeys() {
	}

	public String getAuthorizationKey() {
		return this.authorizationKey;
	}

	public void setAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser_id() {
		return userId;
	}

	public void setUser_id(String user_id) {
		this.userId = user_id;
	}

	

}