package com.tecnics.einvoice.model;

import java.util.List;

import com.tecnics.einvoice.entity.CustomActions;
import com.tecnics.einvoice.entity.CustomActionsByInvoiceStatus;
import com.tecnics.einvoice.entity.CustomActionsByPartnerRole;

public class CustomActionsModel {
	
	private CustomActions customActions;
	private List<CustomActionsByInvoiceStatus> customActionsByInvoiceStatus;
	private List<CustomActionsByPartnerRole> customActionsByPartnerRole;
	public CustomActions getCustomActions() {
		return customActions;
	}
	public void setCustomActions(CustomActions customActions) {
		this.customActions = customActions;
	}
	public List<CustomActionsByInvoiceStatus> getCustomActionsByInvoiceStatus() {
		return customActionsByInvoiceStatus;
	}
	public void setCustomActionsByInvoiceStatus(List<CustomActionsByInvoiceStatus> customActionsByInvoiceStatus) {
		this.customActionsByInvoiceStatus = customActionsByInvoiceStatus;
	}
	public List<CustomActionsByPartnerRole> getCustomActionsByPartnerRole() {
		return customActionsByPartnerRole;
	}
	public void setCustomActionsByPartnerRole(List<CustomActionsByPartnerRole> customActionsByPartnerRole) {
		this.customActionsByPartnerRole = customActionsByPartnerRole;
	}
	

}
