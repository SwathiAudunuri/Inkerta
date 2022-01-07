package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the captcha_keys database table.
 * 
 */

@Entity
@Table(name="captcha_otp_keys", schema = "einvoicing")

public class CaptchaOTPKeys implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="captcha_otp_id")
	private Integer captchaOTPId;
	
	@Column(name="captcha_otp_key")
	private Integer captchaOTPKey;
	
	@Column(name="module_name")
	private String moduleName;
	
	
	@Column(name="status")
	private String status;
	

	@Column(name="created_date")
	private Timestamp createdDate;
	
	@Column(name="updated_date")
	private Timestamp updatedDate;
	
	@Column(name="key_type")
	private String keyType;
	
	@Column(name="key_value")
	private String keyValue;

	public Integer getCaptchaOTPId() {
		return captchaOTPId;
	}

	public void setCaptchaOTPId(Integer captchaOTPId) {
		this.captchaOTPId = captchaOTPId;
	}

	public Integer getCaptchaOTPKey() {
		return captchaOTPKey;
	}

	public void setCaptchaOTPKey(Integer captchaOTPKey) {
		this.captchaOTPKey = captchaOTPKey;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}	

}
