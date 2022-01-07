package com.tecnics.einvoice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles", schema = "einvoicing")
public class UserRoles implements Serializable {

	@Column(name = "user_id")
	private String userId;
	@Column(name = "user_role")
	private String userRole;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "role_id")
	private int roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public UserRoles(String userId, String userRole, int id, int roleId) {
		super();
		this.userId = userId;
		this.userRole = userRole;
		this.id = id;
		this.roleId = roleId;
	}

	public UserRoles() {
	}
	
	
}
