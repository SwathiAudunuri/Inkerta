package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * The persistent class for the document_objects database table.
 * 
 */
@Entity
@Table(name = "document_objects", schema = "einvoicing")
public class AttachmentDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@CreationTimestamp
	@Column(name = "created_on", nullable = false, updatable = false, insertable = false)
	private Timestamp created_on;

	@Column(name = "doc_type")
	@JsonProperty("doc_type")
	private String docType;
	
	@Column(name = "doc_path")
	private String docPath;
	
	@Column(name = "created_by")
	private String createdBy;

	
	@Id
	@Column(name = "doc_id")
	private String docId;
	
	
	@Column(name = "doc_size")
	private Integer doc_size;
	
	@Column(name = "mime_type")
	private String mime_type;

	@Transient
	private String base64;
	
	@Column(name = "folder_id")
	private String folder_id;
	
	@Column(name = "doc_name")
	private String doc_name;
	
	@Column(name = "ref_id")
	private String refId;
	
	@Column(name = "doc_category")
	private String docCategory;



	public String getDocCategory() {
		return docCategory;
	}


	public void setDocCategory(String docCategory) {
		this.docCategory = docCategory;
	}


	public Timestamp getCreated_on() {
		return created_on;
	}


	public void setCreated_on(Timestamp created_on) {
		this.created_on = created_on;
	}


	public String getDocType() {
		return docType;
	}


	public void setDocType(String docType) {
		this.docType = docType;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	

	public String getDocPath() {
		return docPath;
	}


	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}


	public String getDocId() {
		return docId;
	}


	public void setDocId(String docId) {
		this.docId = docId;
	}


	public Integer getDoc_size() {
		return doc_size;
	}


	public void setDoc_size(Integer doc_size) {
		this.doc_size = doc_size;
	}


	public String getMime_type() {
		return mime_type;
	}


	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}


	public String getBase64() {
		return base64;
	}


	public void setBase64(String base64) {
		this.base64 = base64;
	}


	public String getFolder_id() {
		return folder_id;
	}


	public void setFolder_id(String folder_id) {
		this.folder_id = folder_id;
	}


	public String getDoc_name() {
		return doc_name;
	}


	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}




	@Override
	public String toString() {
		return "InvoiceAttachmentDetail [created_on=" + created_on + ", doc_type=" + docType + ", doc_id=" + docId
				+ ", doc_size=" + doc_size + ", mime_type=" + mime_type + ", base64=" + base64 + ", folder_id="
				+ folder_id + ", doc_name=" + doc_name + ", ref_id=" + refId + "]";
	}


	public String getRefId() {
		return refId;
	}


	public void setRefId(String refId) {
		this.refId = refId;
	}


	public AttachmentDetails(Timestamp created_on, String docType, String docId, Integer doc_size,
			String mime_type, String base64, String folder_id, String doc_name, String docPath, String refId) {
		super();
		this.created_on = created_on;
		this.docType = docType;
		this.docId = docId;
		this.doc_size = doc_size;
		this.mime_type = mime_type;
		this.base64 = base64;
		this.folder_id = folder_id;
		this.doc_name = doc_name;
		this.docPath=docPath;
		this.refId = refId;
	}


	public AttachmentDetails() {
		super();
	}
	
	


}