package com.tecnics.einvoice.entity;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the custom_actions database table.
 * 
 */


@Entity
@Table(name="custom_actions", schema = "einvoicing")

public class CustomActions implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="action_id")
	private Integer actionId;
	
	@Column(name="action_name")
	private String actionName;
	
	@Column(name="partner_id")
	private String partnerId;
	
	
	@Column(name="created_by")
	private String createdBy;
	

	@Column(name="created_date")
	private Timestamp createdDate;
	
	@Column(name="action_connector_type")
	private String actionConnectorType;
	
	@Column(name="action_system")
	private String actionSystem;
	
	@Column(name="routing_component")
	private String routingComponent;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="user_password")
	private String userPassword;
	
	@Column(name="url")
	private String url;
	
	public String getTransferStylesheet() {
		return transferStylesheet;
	}

	public void setTransferStylesheet(String transferStylesheet) {
		this.transferStylesheet = transferStylesheet;
	}

	@Column(name="input_request")
	private String inputRequest;
	
	@Column(name="transfer_stylesheet")
	private String transferStylesheet;
	
	
	@Column(name="resp_attribute_onsuccessful")
	private String respAttributeOnsuccessful;
	
	@Column(name="resp_message")
	private String respMessage;
	
	@Column(name="action_operation")
	private String actionOperation;

	
	

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getActionConnectorType() {
		return actionConnectorType;
	}

	public void setActionConnectorType(String actionConnectorType) {
		this.actionConnectorType = actionConnectorType;
	}

	public String getActionSystem() {
		return actionSystem;
	}

	public void setActionSystem(String actionSystem) {
		this.actionSystem = actionSystem;
	}

	public String getRoutingComponent() {
		return routingComponent;
	}

	public void setRoutingComponent(String routingComponent) {
		this.routingComponent = routingComponent;
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

	public String getRespAttributeOnsuccessful() {
		return respAttributeOnsuccessful;
	}

	public void setRespAttributeOnsuccessful(String respAttributeOnsuccessful) {
		this.respAttributeOnsuccessful = respAttributeOnsuccessful;
	}

	public String getRespMessage() {
		return respMessage;
	}

	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}

	public String getActionOperation() {
		return actionOperation;
	}

	public void setActionOperation(String actionOperation) {
		this.actionOperation = actionOperation;
	}

	

}
