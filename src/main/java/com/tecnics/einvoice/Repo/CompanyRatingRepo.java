package com.tecnics.einvoice.Repo;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.entity.CompanyRating;


public interface CompanyRatingRepo extends CrudRepository<CompanyRating, String> {
	
	CompanyRating findByToPartnerIdAndFromPartnerId(String toPartnerId, String fromPartnerId);
	
	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_PAID_INVOICES_FOR_EXTERNAL_COMPANY_RATING)
	List<PaidInvoicesForCompanyRatingResults> fetchPaidInvoicesForExternalCompanyRating(String partnerId, String recipient_partnerId);


	public interface PaidInvoicesForCompanyRatingResults {
		String getDocument_ref_id();
		String getVendor_partner_id();
		String getCustomer_partner_id();
		String getCompany_name();
		String getInvoicenum();
		Timestamp getInvoicedate();
		String getInvoice_status();
		BigDecimal getTotal_invoice_value();
		BigDecimal getPaid_amount();
		BigDecimal getBalance();
		Timestamp getInvoice_duedate();
		Integer getAge_in_days();
	

	}
}
