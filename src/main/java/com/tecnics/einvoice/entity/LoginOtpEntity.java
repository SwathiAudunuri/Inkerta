package com.tecnics.einvoice.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_login_otp", schema = "einvoicing")
public class LoginOtpEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="primary_phone")
	private String primaryPhone;
	private Long secondary_phone;
	private String email;
	private String otp_key;
	private String status;
	private Timestamp created_on;
	private Timestamp validated_on;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPrimaryPhone() {
		return primaryPhone;
	}
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}
	public Long getSecondary_phone() {
		return secondary_phone;
	}
	public void setSecondary_phone(Long secondary_phone) {
		this.secondary_phone = secondary_phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp_key() {
		return otp_key;
	}
	public void setOtp_key(String otp_key) {
		this.otp_key = otp_key;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Timestamp created_on) {
		this.created_on = created_on;
	}
	public Timestamp getValidated_on() {
		return validated_on;
	}
	public void setValidated_on(Timestamp validated_on) {
		this.validated_on = validated_on;
	}
	@Override
	public String toString() {
		return "LoginOtpEntity [id=" + id + ", primary_phone=" + primaryPhone + ", secondary_phone=" + secondary_phone
				+ ", email=" + email + ", otp_key=" + otp_key + ", status=" + status + ", created_on=" + created_on
				+ ", validated_on=" + validated_on + "]";
	}
	public LoginOtpEntity() {
		super();
	}

	
}
