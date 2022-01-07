package com.tecnics.einvoice.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the company_bank_details database table.
 * 
 */
@Entity
@Table(name="company_bank_details", schema = "einvoicing")
public class CompanyBankDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="account_type")
	private String accountType;

	@Column(name="bank_name")
	private String bankName;

	@Column(name="branch_address")
	private String branchAddress;

	@EmbeddedId
	private CompanyBankDetailPK id;

	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="partner_id")
	private PartnerDetails partnerDetail;

	@Column(name="payee_name")
	private String payeeName;

	public CompanyBankDetail() {
	}

	public String getAccountType() {
		return this.accountType;
	}

	public String getBankName() {
		return this.bankName;
	}

	public String getBranchAddress() {
		return this.branchAddress;
	}

	public CompanyBankDetailPK getId() {
		return this.id;
	}

	public PartnerDetails getPartnerDetail() {
		return this.partnerDetail;
	}

	public String getPayeeName() {
		return this.payeeName;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public void setId(CompanyBankDetailPK id) {
		this.id = id;
	}

	public void setPartnerDetail(PartnerDetails partnerDetail) {
		this.partnerDetail = partnerDetail;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	@Override
	public String toString() {
		return "CompanyBankDetail [id=" + id + ", accountType=" + accountType + ", bankName=" + bankName
				+ ", branchAddress=" + branchAddress + ", payeeName=" + payeeName + ", partnerDetail=" + partnerDetail
				+ "]";
	}

}