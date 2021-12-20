/**
 * 
 */
package com.tecnics.einvoice.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="state_master", schema = "einvoicing")
public class StateMaster {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="state_code", unique=true, nullable=false, length=5)
	private String stateCode;

	@Column(name="state_name", length=50)
	private String stateName;
	
	
	@Column(name="state_gstin_code", length=50)
	private String stateGstinCode;


	@Override
	public String toString() {
		return "StateMaster [stateCode=" + stateCode + ", stateName=" + stateName + ", stateGstinCode=" + stateGstinCode
				+ "]";
	}


	public String getStateCode() {
		return stateCode;
	}


	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}


	public String getStateName() {
		return stateName;
	}


	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	public String getStateGstinCode() {
		return stateGstinCode;
	}


	public void setStateGstinCode(String stateGstinCode) {
		this.stateGstinCode = stateGstinCode;
	}
	
	
	
}
