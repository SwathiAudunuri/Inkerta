/**
 * 
 */
package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


/**
 * The persistent class for the error_log database table.
 * 
 */
@Entity
@Table(name="error_log", schema = "einvoicing")
public class ErrorLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="component_name", length=25)
	private String componentName;

	@Column(length=25)
	private String error;

	@Column(name="error_message", length=2147483647)
	private String errorMessage;
	
	@CreationTimestamp
	@Column(name = "log_date",nullable = false, updatable = false, insertable = false)
	private Timestamp logDate;

	@Column(length=25)
	private String module;

	@Column(name="ref_id", length=11)
	private String refId;

	@Column(length=25)
	private String source;

	@Column(name="user_id", length=6)
	private String userId;

	public ErrorLog() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComponentName() {
		return this.componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Timestamp getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getRefId() {
		return this.refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param componentName
	 * @param error
	 * @param errorMessage
	 * @param module
	 * @param refId
	 * @param source
	 * @param userId
	 */
	public ErrorLog(String componentName, String error, String errorMessage, String module, String refId, String source,
			String userId) {
		super();
		this.componentName = componentName;
		this.error = error;
		this.errorMessage = errorMessage;
		this.module = module;
		this.refId = refId;
		this.source = source;
		this.userId = userId;
	}



}
