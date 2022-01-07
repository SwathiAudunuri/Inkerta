
package com.tecnics.einvoice.entity;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the custom_actions database table.
 * 
 */


@Entity
@Table(name="invoice_reminder_templates", schema = "einvoicing")

public class InvoiceReminderTemplates implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="template_id")
	private Integer templateId;
	
	@Column(name="template_name")
	private String templateName;
	
	@Column(name="partner_id")
	private String partnerId;
	

	@Column(name="created_by")
	private String createdBy;
	

	@Column(name="created_date")
	private Timestamp createdDate;

	
	@Column(name="email_subject")
	private String emailSubject;
	
	
	@Column(name="template_content")
	private String templateContent;
	
	
	public Integer getTemplateId() {
		return templateId;
	}


	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}


	public String getTemplateName() {
		return templateName;
	}


	public void setTemplateName(String templateName) {
		this.templateName = templateName;
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



	public String getEmailSubject() {
		return emailSubject;
	}


	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}


	public String getTemplateContent() {
		return templateContent;
	}


	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}



	}


