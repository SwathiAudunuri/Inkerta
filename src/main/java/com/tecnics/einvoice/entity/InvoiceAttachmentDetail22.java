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
@Table(name = "invoice_attachment_details", schema = "einvoicing")
public class InvoiceAttachmentDetail22 implements Serializable {
	private static final long serialVersionUID = 1L;

	@CreationTimestamp
	@Column(name = "created_date", nullable = false, updatable = false, insertable = false)
	private Timestamp created_date;

	@Column(name = "doc_type")
	private String doc_type;

	@Column(name = "ecm_doc_id")
	private String ecm_doc_id;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "mime_type")
	private String mime_type;

	@Transient
	private String base64;
	@Transient
	private String folderId;
	
	@Transient
	private String fileName;
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private String document_ref_id;

	/**
	 * @return the base64
	 */
	public String getBase64() {
		return base64;
	}

	/**
	 * @param base64 the base64 to set
	 */
	public void setBase64(String base64) {
		this.base64 = base64;
	}

	/**
	 * @return the folder_id
	 */
	
	public InvoiceAttachmentDetail22() {
	}

	/**
	 * @return the folderId
	 */
	public String getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public String getDoc_type() {
		return doc_type;
	}

	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}

	public String getEcm_doc_id() {
		return ecm_doc_id;
	}

	public void setEcm_doc_id(String ecm_doc_id) {
		this.ecm_doc_id = ecm_doc_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMime_type() {
		return mime_type;
	}

	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}

	public String getDocument_ref_id() {
		return document_ref_id;
	}

	public void setDocument_ref_id(String document_ref_id) {
		this.document_ref_id = document_ref_id;
	}

}