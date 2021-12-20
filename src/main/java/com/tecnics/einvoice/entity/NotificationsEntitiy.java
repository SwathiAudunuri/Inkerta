package com.tecnics.einvoice.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.util.List;

/**
 * The persistent class for the enquiries database table.
 * 
 */
@Entity
@Table(name = "notifications", schema = "einvoicing")
public class NotificationsEntitiy implements Serializable {

	public NotificationsEntitiy() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String ref_id;
	private String entity;
	private String type;
	private String sub_type;
	private String details;
	private String created_by;
	private String status;
	@CreationTimestamp
	@Column(name = "created_on",nullable = false, updatable = false, insertable = false)
	private Timestamp created_on;
	@CreationTimestamp
	@Column(name = "created_on",nullable = false, updatable = false, insertable = false)
	private Timestamp closed_on;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRef_id() {
		return ref_id;
	}
	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSub_type() {
		return sub_type;
	}
	public void setSub_type(String sub_type) {
		this.sub_type = sub_type;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
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
	public Timestamp getClosed_on() {
		return closed_on;
	}
	public void setClosed_on(Timestamp closed_on) {
		this.closed_on = closed_on;
	}
	@Override
	public String toString() {
		return "Notifications [id=" + id + ", ref_id=" + ref_id + ", entity=" + entity + ", type=" + type
				+ ", sub_type=" + sub_type + ", details=" + details + ", created_by=" + created_by + ", status="
				+ status + ", created_on=" + created_on + ", closed_on=" + closed_on + "]";
	}
	public NotificationsEntitiy(Integer id, String ref_id, String entity, String type, String sub_type, String details,
			String created_by, String status, Timestamp created_on, Timestamp closed_on) {
		super();
		this.id = id;
		this.ref_id = ref_id;
		this.entity = entity;
		this.type = type;
		this.sub_type = sub_type;
		this.details = details;
		this.created_by = created_by;
		this.status = status;
		this.created_on = created_on;
		this.closed_on = closed_on;
	}

	
	




}