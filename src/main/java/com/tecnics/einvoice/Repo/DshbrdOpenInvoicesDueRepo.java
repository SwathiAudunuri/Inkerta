package com.tecnics.einvoice.Repo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.entity.DshbrdRoot;

public interface DshbrdOpenInvoicesDueRepo extends CrudRepository<DshbrdRoot, Integer> {
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_CUSTOMER_OPEN_UNPAIDTOTALS)
	List<OpenDueInvoicesResults> fetchCustomerUnpaidTotalsByPartnerId(String inInv_partnerId, String exInv_partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_CUSTOMER_OPEN_OVERDUETOTALS)
	List<OpenDueInvoicesResults> fetchCustomerOverdueTotalsByPartnerId(String partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_CUSTOMER_OPEN_01TO30DAYSOVERDUETOTALS)
	List<OpenDueInvoicesResults> fetchCustomer01To30DaysOverdueTotalsByPartnerId(String partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_CUSTOMER_OPEN_31TO90DAYSOVERDUETOTALS)
	List<OpenDueInvoicesResults> fetchCustomer31To90DaysOverdueTotalsByPartnerId(String partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_CUSTOMER_OPEN_OVER90DAYSOVERDUETOTALS)
	List<OpenDueInvoicesResults> fetchCustomerOver90DaysOverdueTotalsByPartnerId(String partnerId);
	
	
	
	public interface OpenDueInvoicesResults {
		Integer getNoofinvoices();
		Integer getTotals();
		String getInvoice_currency_code();

	}
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_VENDOR_OPEN_UNPAIDTOTALS)
	List<OpenDueInvoicesResults> fetchVendorUnpaidTotalsByPartnerId(String inInv_partnerId, String exInv_partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_VENDOR_OPEN_OVERDUETOTALS)
	List<OpenDueInvoicesResults> fetchVendorOverdueTotalsByPartnerId(String inInv_partnerId, String exInv_partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_VENDOR_OPEN_01TO30DAYSOVERDUETOTALS)
	List<OpenDueInvoicesResults> fetchVendor01To30DaysOverdueTotalsByPartnerId(String inInv_partnerId, String exInv_partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_VENDOR_OPEN_31TO90DAYSOVERDUETOTALS)
	List<OpenDueInvoicesResults> fetchVendor31To90DaysOverdueTotalsByPartnerId(String inInv_partnerId, String exInv_partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_CUSTOMER_OPEN_OVER90DAYSOVERDUETOTALS)
	List<OpenDueInvoicesResults> fetchVendorOver90DaysOverdueTotalsByPartnerId(String partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_CUSTOMER_TOP10_PAYABLES_BY_VENDOR)
	Top10PayablesORReceivablesResults fetchCustomerTop10PayablesByVendor(String inInv_partnerId, String exInv_partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_VENDOR_TOP10_PAYABLES_BY_CUSTOMER)
	Top10PayablesORReceivablesResults fetchVendorTop10PayablesByCustomer(String inInv_partnerId, String exInv_partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_CURRENT_RECEIVABLES_BY_COMPANIES)
	List<AllReceivableOrPayableResultsByCompany> fetchAllCurrentReceivablesByCompanies(String partnerId, String invoice_currency_code);
	
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_CURRENT_PAYABLES_BY_COMPANIES)
	List<AllReceivableOrPayableResultsByCompany> fetchAllCurrentPayablesByCompanies(String partnerId, String invoice_currency_code);
	
	
	public interface Top10PayablesORReceivablesResults {
		String[] getNoofinvoices();
		String[] getTotals();
		String[] getInvoice_currency_code();
		String[] getCompany_name();

	}
	
	
	public interface AllReceivableOrPayableResultsByCompany {
		String getRating();
		String getInvoice_currency_code();
		String getVendor_company_name();
		String getCustomer_company_name();
		String getVendor_partner_id();
		String getCustomer_partner_id();
		Integer getNoofinvoices();
		BigDecimal getCurrent();
		BigDecimal getOneto30days();
		BigDecimal getThirtyoneto60days();
		BigDecimal getSixtyoneto90days();
		BigDecimal getOver90days();
		BigDecimal getBalancedue();
		String getTransaction_type();
		Boolean getIsexternal();
	}	
	
	
	//DSHBRD_ALL_RECEIVABLES_SUMMARY
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_RECEIVABLES_SUMMARY)
	List<AllReceivableOrPayableSummaryResults> fetchAllReceivablesSummary(String partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_PAYABLES_SUMMARY)
	List<AllReceivableOrPayableSummaryResults> fetchAllPayablesSummary(String partnerId);
	
	
	public interface AllReceivableOrPayableSummaryResults {
		String getInvoice_currency_code();
		Integer getNoofinvoices();
		BigDecimal getCurrent();
		BigDecimal getOneto30days();
		BigDecimal getThirtyoneto60days();
		BigDecimal getSixtyoneto90days();
		BigDecimal getOver90days();
		BigDecimal getBalancedue();		
	}	
	
	//DSHBRD_ALL_CLOSED_RECEIVABLES_SUMMARY
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_CLOSED_RECEIVABLES_SUMMARY)
	List<AllClosedReceivableOrPayableSummaryResults> fetchAllClosedReceivablesSummary(String partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_CLOSED_PAYABLES_SUMMARY)
	List<AllClosedReceivableOrPayableSummaryResults> fetchAllClosedPayablesSummary(String partnerId);
	
	public interface AllClosedReceivableOrPayableSummaryResults {
		String getInvoice_currency_code();
		Integer getNoofinvoices();
		BigDecimal getCurrent();
		BigDecimal getOneto30days();
		BigDecimal getThirtyoneto60days();
		BigDecimal getSixtyoneto90days();
		BigDecimal getOver90days();
		BigDecimal getBalancedue();
		BigDecimal getPaidamount();
	}	
	
	
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_CLOSED_RECEIVABLES_BY_COMPANIES)
	List<AllClosedReceivableOrPayableResultsByCompany> fetchAllClosedReceivablesByCompanies(String partnerId, String invoice_currency_code);
	
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_CLOSED_PAYABLES_BY_COMPANIES)
	List<AllClosedReceivableOrPayableResultsByCompany> fetchAllClosedPayablesByCompanies(String partnerId, String invoice_currency_code);
	
	public interface AllClosedReceivableOrPayableResultsByCompany {
		String getRating();
		String getInvoice_currency_code();
		String getVendor_company_name();
		String getCustomer_company_name();
		String getVendor_partner_id();
		String getCustomer_partner_id();
		Integer getNoofinvoices();
		BigDecimal getCurrent();
		BigDecimal getOneto30days();
		BigDecimal getThirtyoneto60days();
		BigDecimal getSixtyoneto90days();
		BigDecimal getOver90days();
		BigDecimal getBalancedue();
		BigDecimal getPaid_amount();
		String getTransaction_type();
		Boolean getIsexternal();
	}	
	
	
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_CURRENT_RECEIVABLES_BY_COMPANY)
	List<AllReceivableOrPayableForCompany> fetchAllCurrentReceivablesFromCompany(String partnerId, String cusomer_partnerId, String invoice_currency_code);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_CURRENT_PAYABLES_BY_COMPANY)
	List<AllReceivableOrPayableForCompany> fetchAllCurrentPayablesFromCompany(String partnerId, String cusomer_partnerId, String invoice_currency_code);
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_CLOSED_RECEIVABLES_BY_COMPANY)
	List<AllReceivableOrPayableForCompany> fetchAllClosedReceivablesFromCompany(String partnerId, String cusomer_partnerId, String invoice_currency_code);
	
	
	@Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_CLOSED_PAYABLES_BY_COMPANY)
	List<AllReceivableOrPayableForCompany> fetchAllClosedPayablesFromCompany(String partnerId, String vendor_partnerId, String invoice_currency_code);
	
	
	
		public interface AllReceivableOrPayableForCompany {
		 String getDocument_ref_id();
		 String getVendor_partner_id();
		 String getCustomer_partner_id();
		 String getCompany_name();
		 String getInvoicenum();
		 Date getInvoicedate();
		 String getInvoice_currency_code();
		 String getInvoice_status();
	     BigDecimal getTotal_invoice_value();
			BigDecimal getPaid_amount();
			BigDecimal getBalance();
			Date getInvoice_duedate();
			Integer getAge();
	
		}
		
		
		@Query(nativeQuery = true, value=SQLQueries.DSHBRD_RECEIVABLES_SUMMARY_BY_ONBOARDED_COMPANY)
		List<AllReceivableOrPayableSummaryForOnboardedOrExternalCompany> fetchAllReceivablesSummaryFromOnboardedCompany(String vendor_partnerId, String customer_partnerId);
		
		
		@Query(nativeQuery = true, value=SQLQueries.DSHBRD_RECEIVABLES_SUMMARY_BY_EXTERNAL_COMPANY)
		List<AllReceivableOrPayableSummaryForOnboardedOrExternalCompany> fetchAllReceivablesSummaryFromExternalCompany(String vendor_partnerId, String customer_partnerId);
		
		public interface AllReceivableOrPayableSummaryForOnboardedOrExternalCompany {
			String getVendor_company_name();
			String getCustomer_company_name();
			 String getVendor_partner_id();
			 String getCustomer_partner_id();
			 BigDecimal getCurrentdue();
			 BigDecimal getOverdue();
			 BigDecimal getPromisetopay();
			 BigDecimal getPending();
			 BigDecimal getPaid_amount();
		
			}
		
		@Query(nativeQuery = true, value=SQLQueries.DSHBRD_CONTACTDETAILS_ONBOARDED_COMPANY)
		List<ContactDetailsForOnboardedOrExternalCompany> fetchContactDetailsOfOnboardedCompany(String partnerId);
	
		@Query(nativeQuery = true, value=SQLQueries.DSHBRD_CONTACTDETAILS_EXTERNAL_COMPANY)
		List<ContactDetailsForOnboardedOrExternalCompany> fetchContactDetailsOfExternalCompany(String partnerId);
	
		
		public interface ContactDetailsForOnboardedOrExternalCompany {
			String getPartner_id();
			String getCompany_name();
			String getRating();
			 String getPartner_address();
			 String getContactname();
			 String getPrimary_phone_number();
			 String getEmail();		
			}
		
		
		
		
		@Query(nativeQuery = true, value=SQLQueries.DSHBRD_INTERNAL_INVOICE_RECEIVABLE_ACTIVITIES)
		List<InvoiceActivities> fetchInvoiceActivitiesForInternal(String notes_vendor_partner_id,String notes_customer_partner_id, String sttracker_vendor_partner_id,String sttracker_customer_partner_id,String query_vendor_partner_id,String query_customer_partner_id);
		
		@Query(nativeQuery = true, value=SQLQueries.DSHBRD_EXTERNAL_INVOICE_RECEIVABLE_ACTIVITIES)
		List<InvoiceActivities> fetchInvoiceActivitiesForExternal(String notes_partner_id, String notes_recipient_partner_id,String sttracker_partner_id, String sttracker_recipient_partner_id);
	//activity,activityby,activity_date,invoicenum,activity_subtype,activity_type
		
		public interface InvoiceActivities {
			String getActivity();
			String getActivityby();
			Timestamp getActivity_date();
			 String getInvoicenum();
			 String getActivity_subtype();
			 String getActivity_type();	
		}
			 
			 
			 // cash flow dashboards
			 
			 
			 
			 @Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_RECEIVABLES_FOR_CASHFLOW)
				List<CashFlowResults> fetchReceivablesForCashFlow(String partner_id, Date start_date,Date end_date);
			
			 @Query(nativeQuery = true, value=SQLQueries.DSHBRD_ALL_PAYABLES_FOR_CASHFLOW)
				List<CashFlowResults> fetchPayablesForCashFlow(String partner_id, Date start_date,Date end_date);
			
			   
				public interface CashFlowResults {
					String getDocument_ref_id();
					String getCompany_name();
					String getInvoicenum();
					String getType();
					String getInvoice_currency_code();
					BigDecimal getBalance();
					Date getInvoice_duedate();
					Date getExpected_paymentdate();
				
				}

}

