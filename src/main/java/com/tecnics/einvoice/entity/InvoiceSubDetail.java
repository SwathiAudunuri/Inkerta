package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the invoice_sub_details database table.
 * 
 */
@Entity
@Table(name = "invoice_sub_details")
@NamedQuery(name = "InvoiceSubDetail.findAll", query = "SELECT i FROM InvoiceSubDetail i")
public class InvoiceSubDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "additional_details")
	private String additionalDetails;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "partner_trans_id")
	private String partnerTransId;

	@Column(name = "recipient_note")
	private String recipientNote;

	// bi-directional many-to-one association to InvoiceDetail
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_ref_id")
	private InvoiceDocumentDetail invoiceDocumentDetail;

	public InvoiceSubDetail() {
	}

	public String getAdditionalDetails() {
		return this.additionalDetails;
	}

	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartnerTransId() {
		return this.partnerTransId;
	}

	public void setPartnerTransId(String partnerTransId) {
		this.partnerTransId = partnerTransId;
	}

	public String getRecipientNote() {
		return this.recipientNote;
	}

	public void setRecipientNote(String recipientNote) {
		this.recipientNote = recipientNote;
	}

	public InvoiceDocumentDetail getInvoiceDetail() {
		return this.invoiceDocumentDetail;
	}

	public void setInvoiceDetail(InvoiceDocumentDetail invoiceDocumentDetail) {
		this.invoiceDocumentDetail = invoiceDocumentDetail;
	}

}