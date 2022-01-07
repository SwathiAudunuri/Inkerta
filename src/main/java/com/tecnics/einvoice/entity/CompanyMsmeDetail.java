package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the company_msme_details database table.
 * 
 */
@Entity
@Table(name="company_msme_details", schema = "einvoicing")
public class CompanyMsmeDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="msme_reg_no")
	private String msmeRegNo;

	@Column(name="additional_info")
	private String additionalInfo;

	@Column(name="ecm_doc_id")
	private String msmeDocId;

	@Temporal(TemporalType.DATE)
	@Column(name="msme_reg_date")
	private Date msmeRegDate;

	@Column(name="msme_type")
	private String msmeType;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="partner_id")
	private PartnerDetails partnerDetail;

	public CompanyMsmeDetail() {
	}

	public String getAdditionalInfo() {
		return this.additionalInfo;
	}

	public String getMsmeDocId() {
		return this.msmeDocId;
	}

	public Date getMsmeRegDate() {
		return this.msmeRegDate;
	}

	public String getMsmeRegNo() {
		return this.msmeRegNo;
	}

	public String getMsmeType() {
		return this.msmeType;
	}

	public PartnerDetails getPartnerDetail() {
		return this.partnerDetail;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public void setMsmeDocId(String msmeDocId) {
		this.msmeDocId = msmeDocId;
	}

	public void setMsmeRegDate(Date msmeRegDate) {
		this.msmeRegDate = msmeRegDate;
	}

	public void setMsmeRegNo(String msmeRegNo) {
		this.msmeRegNo = msmeRegNo;
	}

	public void setMsmeType(String msmeType) {
		this.msmeType = msmeType;
	}

	public void setPartnerDetail(PartnerDetails partnerDetail) {
		this.partnerDetail = partnerDetail;
	}

	@Override
	public String toString() {
		return "CompanyMsmeDetail [msmeRegNo=" + msmeRegNo + ", additionalInfo=" + additionalInfo + ", msmeDocId="
				+ msmeDocId + ", msmeRegDate=" + msmeRegDate + ", msmeType=" + msmeType + ", partnerDetail="
				+ partnerDetail + "]";
	}

}