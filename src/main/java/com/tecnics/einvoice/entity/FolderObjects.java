package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * The persistent class for the invoice_attachment_details database table.
 * 
 */
@Entity
@Table(name = "folder_objects", schema = "einvoicing")
public class FolderObjects {
	
	private static final long serialVersionUID = 1L;

	@CreationTimestamp
	@Column(name = "created_on", nullable = false, updatable = false, insertable = false)
	private Timestamp created_on;
	
	@Id
	@Column(name = "folder_id")
	private String folder_id;
	

	@Column(name = "folder_path")
	private String folderPath;
	

	@Column(name = "folder_type")
	private String folder_type;
	
	public Timestamp getCreated_on() {
		return created_on;
	}


	public void setCreated_on(Timestamp created_on) {
		this.created_on = created_on;
	}
	

	public String getFolder_id() {
		return folder_id;
	}


	public void setFolder_id(String folder_id) {
		this.folder_id = folder_id;
	}
	
	
	public String getFolderPath() {
		return folderPath;
	}


	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}


	public String getFolder_type() {
		return folder_type;
	}


	public void setFolder_type(String folder_type) {
		this.folder_type = folder_type;
	}

}
