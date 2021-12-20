package com.tecnics.einvoice.model;

public class InvoiceAction {
	
	
	private String action_code;
	
	private String action_name;
	
	private String source;

	@Override
	public String toString() {
		return "InvoiceAction [action_code=" + action_code + ", action_name=" + action_name + ", source=" + source
				+ "]";
	}

	public String getAction_code() {
		return action_code;
	}

	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}

	public String getAction_name() {
		return action_name;
	}

	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	
}
