package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the field_master database table.
 * 
 */
@Entity
@Table(name="field_master", schema = "einvoicing")
public class FieldMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="field_name")
	private String fieldName;

	@Column(name="field_value")
	private String fieldValue;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="module_name")
	private String moduleName;

	public FieldMaster() {
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return this.fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}