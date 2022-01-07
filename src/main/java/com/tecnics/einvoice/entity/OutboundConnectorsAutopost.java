
package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * The persistent class for the outbound_connectors_autopost database table.
 * 
 */
@Entity
@Table(name = "outbound_connectors_autopost", schema = "einvoicing")
public class OutboundConnectorsAutopost {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "autopost_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String autopostId;
	

	@Column(name = "connector_id")
	private Integer connectorId;
	

	@Column(name = "connector_type")
	private String connectorType;
	

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_password")
	private String userPassword;

	@Column(name = "url")
	private String url;

	@Column(name = "input_request")
	private String inputRequest;

	@Column(name = "resp_doc_number_onsuccessful")
	private String respDocNumberOnsuccessful;
	

	public String getAutopostId() {
		return autopostId;
	}

	public void setAutopostId(String autopostId) {
		this.autopostId = autopostId;
	}

	public Integer getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(Integer connectorId) {
		this.connectorId = connectorId;
	}

	public String getConnectorType() {
		return connectorType;
	}

	public void setConnectorType(String connectorType) {
		this.connectorType = connectorType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInputRequest() {
		return inputRequest;
	}

	public void setInputRequest(String inputRequest) {
		this.inputRequest = inputRequest;
	}

	public String getRespDocNumberOnsuccessful() {
		return respDocNumberOnsuccessful;
	}

	public void setRespDocNumberOnsuccessful(String respDocNumberOnsuccessful) {
		this.respDocNumberOnsuccessful = respDocNumberOnsuccessful;
	}

	public String getRespMessage() {
		return respMessage;
	}

	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}

	public String getOperationAction() {
		return operationAction;
	}

	public void setOperationAction(String operationAction) {
		this.operationAction = operationAction;
	}

	@Column(name = "resp_message")
	private String respMessage;
	
	@Column(name = "operation_action")
	private String operationAction;
}
