package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the company_bank_details database table.
 * 
 */
@Embeddable
public class CompanyBankDetailPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="payee_bank_account_number")
	private String payeeBankAccountNumber;

	@Column(name="ifsc_code")
	private String ifscCode;

	public CompanyBankDetailPK() {
	}
	public String getPayeeBankAccountNumber() {
		return this.payeeBankAccountNumber;
	}
	public void setPayeeBankAccountNumber(String payeeBankAccountNumber) {
		this.payeeBankAccountNumber = payeeBankAccountNumber;
	}
	public String getIfscCode() {
		return this.ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CompanyBankDetailPK)) {
			return false;
		}
		CompanyBankDetailPK castOther = (CompanyBankDetailPK)other;
		return 
			this.payeeBankAccountNumber.equals(castOther.payeeBankAccountNumber)
			&& this.ifscCode.equals(castOther.ifscCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.payeeBankAccountNumber.hashCode();
		hash = hash * prime + this.ifscCode.hashCode();
		
		return hash;
	}
}