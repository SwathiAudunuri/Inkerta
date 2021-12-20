package com.tecnics.einvoice.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "credential_reset", schema = "einvoicing")
public class PasswordReset implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String user_id;
	@CreationTimestamp
	@Column(name = "created_on", nullable = false, updatable = false, insertable = false)
	private Timestamp created_on;
	private Timestamp expiry_date;
	private Timestamp activated_on;
	private Boolean activation_status;

	public PasswordReset() {

	}

	public PasswordReset(String id, String user_id, Timestamp created_on, Timestamp expiry_date, Timestamp activated_on,
			Boolean activation_status) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.created_on = created_on;
		this.expiry_date = expiry_date;
		this.activated_on = activated_on;
		this.activation_status = activation_status;
	}

	public Timestamp getActivated_on() {
		return activated_on;
	}

	public Boolean getActivation_status() {
		return activation_status;
	}

	public Timestamp getCreated_on() {
		return created_on;
	}

	public Timestamp getExpiry_date() {
		return expiry_date;
	}

	public String getId() {
		return id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setActivated_on(Timestamp activated_on) {
		this.activated_on = activated_on;
	}

	public void setActivation_status(Boolean activation_status) {
		this.activation_status = activation_status;
	}

	public void setCreated_on(Timestamp created_on) {
		this.created_on = created_on;
	}

	public void setExpiry_date(Timestamp expiry_date) {
		this.expiry_date = expiry_date;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
