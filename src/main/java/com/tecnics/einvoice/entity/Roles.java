package com.tecnics.einvoice.entity;

import java.util.List;

public class Roles {

	
	private List<String> roles;

	@Override
	public String toString() {
		return "Roles [roles=" + roles + "]";
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
}
