/**
 * 
 */
package com.tecnics.einvoice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_authorization_keys", schema = "einvoicing")
public class UserAuthorizationKey {
	public UserAuthorizationKey() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserManagement userManagement;
	
	@Column(name="authorization_key")
	private String authorizationKey;

	@Override
	public String toString() {
		return "UserAuthorizationKey [id=" + id + ", userManagement=" + userManagement + ", authorizationKey="
				+ authorizationKey + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public UserAuthorizationKey(Integer id, UserManagement userManagement, String authorizationKey) {
		super();
		this.id = id;
		this.userManagement = userManagement;
		this.authorizationKey = authorizationKey;
	}

	public UserManagement getUserManagement() {
		return userManagement;
	}

	public void setUserManagement(UserManagement userManagement) {
		this.userManagement = userManagement;
	}

	public String getAuthorizationKey() {
		return authorizationKey;
	}

	public void setAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
	}

	
}
