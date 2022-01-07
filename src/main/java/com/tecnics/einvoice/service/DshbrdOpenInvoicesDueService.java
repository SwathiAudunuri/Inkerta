package com.tecnics.einvoice.service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.exceptions.Ex;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo;
import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.AllClosedReceivableOrPayableResultsByCompany;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.AllClosedReceivableOrPayableSummaryResults;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.AllReceivableOrPayableForCompany;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.AllReceivableOrPayableResultsByCompany;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.AllReceivableOrPayableSummaryForOnboardedOrExternalCompany;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.AllReceivableOrPayableSummaryResults;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.CashFlowResults;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.ContactDetailsForOnboardedOrExternalCompany;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.InvoiceActivities;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.OpenDueInvoicesResults;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.Top10PayablesORReceivablesResults;
import com.tecnics.einvoice.model.CashFlowReceivablesPayablesResults;
import com.tecnics.einvoice.model.DshbrdOpenInvoicesDue;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;

import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.util.APIError;
@Component
public class DshbrdOpenInvoicesDueService extends BaseService {
	
	@Autowired
	DshbrdOpenInvoicesDueRepo dshbrdCustomerOpenDueInvRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	public ResponseEntity<ResponseMessage> fetchCustomerOpenDueInvoicesByPartnerId(UserLoginDetails userObj)
	{
		DshbrdOpenInvoicesDue dshbrdCustomerOpenDueInvoices=new DshbrdOpenInvoicesDue();
		try
		{
			List<OpenDueInvoicesResults> unpaidtotals;
			List<OpenDueInvoicesResults> overduetotals;
			List<OpenDueInvoicesResults> overduelate01to30daystotals;
			List<OpenDueInvoicesResults> overduelate31to90daystotals;
			List<OpenDueInvoicesResults> overdueover90daystotals;
			unpaidtotals=dshbrdCustomerOpenDueInvRepo.fetchCustomerUnpaidTotalsByPartnerId(userObj.getPartnerId(),userObj.getPartnerId());
			dshbrdCustomerOpenDueInvoices.setUnpaidtotals(unpaidtotals);
			overduetotals=dshbrdCustomerOpenDueInvRepo.fetchCustomerOverdueTotalsByPartnerId(userObj.getPartnerId());
			dshbrdCustomerOpenDueInvoices.setOverduetotals(overduetotals);
			overduelate01to30daystotals=dshbrdCustomerOpenDueInvRepo.fetchCustomer01To30DaysOverdueTotalsByPartnerId(userObj.getPartnerId());
			dshbrdCustomerOpenDueInvoices.setOverduelate01to30daystotals(overduelate01to30daystotals);
			overduelate31to90daystotals=dshbrdCustomerOpenDueInvRepo.fetchCustomer31To90DaysOverdueTotalsByPartnerId(userObj.getPartnerId());
			dshbrdCustomerOpenDueInvoices.setOverduelate31to90daystotals(overduelate31to90daystotals);
			overdueover90daystotals=dshbrdCustomerOpenDueInvRepo.fetchCustomerOver90DaysOverdueTotalsByPartnerId(userObj.getPartnerId());
			dshbrdCustomerOpenDueInvoices.setOverdueover90daystotals(overdueover90daystotals);
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("Save");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results Invoice Totals Error", "Error while fetching the Dashboard Results :","fetchOpenDueInvoicesByPartnerId" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(dshbrdCustomerOpenDueInvoices));
		
	
	}
	
	
	public ResponseEntity<ResponseMessage> fetchVendorOpenDueInvoicesByPartnerId(UserLoginDetails userObj)
	{
		DshbrdOpenInvoicesDue dshbrdCustomerOpenDueInvoices=new DshbrdOpenInvoicesDue();
		try
		{
			List<OpenDueInvoicesResults> unpaidtotals;
			List<OpenDueInvoicesResults> overduetotals;
			List<OpenDueInvoicesResults> overduelate01to30daystotals;
			List<OpenDueInvoicesResults> overduelate31to90daystotals;
			List<OpenDueInvoicesResults> overdueover90daystotals;
			unpaidtotals=dshbrdCustomerOpenDueInvRepo.fetchVendorUnpaidTotalsByPartnerId(userObj.getPartnerId(),userObj.getPartnerId());
			dshbrdCustomerOpenDueInvoices.setUnpaidtotals(unpaidtotals);
			overduetotals=dshbrdCustomerOpenDueInvRepo.fetchVendorOverdueTotalsByPartnerId(userObj.getPartnerId(),userObj.getPartnerId());
			dshbrdCustomerOpenDueInvoices.setOverduetotals(overduetotals);
			overduelate01to30daystotals=dshbrdCustomerOpenDueInvRepo.fetchVendor01To30DaysOverdueTotalsByPartnerId(userObj.getPartnerId(),userObj.getPartnerId());
			dshbrdCustomerOpenDueInvoices.setOverduelate01to30daystotals(overduelate01to30daystotals);
			overduelate31to90daystotals=dshbrdCustomerOpenDueInvRepo.fetchVendor31To90DaysOverdueTotalsByPartnerId(userObj.getPartnerId(),userObj.getPartnerId());
			dshbrdCustomerOpenDueInvoices.setOverduelate31to90daystotals(overduelate31to90daystotals);
			overdueover90daystotals=dshbrdCustomerOpenDueInvRepo.fetchCustomerOver90DaysOverdueTotalsByPartnerId(userObj.getPartnerId());
			dshbrdCustomerOpenDueInvoices.setOverdueover90daystotals(overdueover90daystotals);
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("Save");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results Invoice Totals Error", "Error while fetching the Dashboard Results :","fetchOpenDueInvoicesByPartnerId" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(dshbrdCustomerOpenDueInvoices));
		
	
	}
	
	public ResponseEntity<ResponseMessage> fetchCustomerTop10PayablesByVendor(UserLoginDetails userObj)
	{
		Top10PayablesORReceivablesResults top10PayablesORReceivablesResults=null;
		HashMap results=new LinkedHashMap();
		try
		{
			top10PayablesORReceivablesResults=dshbrdCustomerOpenDueInvRepo.fetchCustomerTop10PayablesByVendor(userObj.getPartnerId(),userObj.getPartnerId());
			if(top10PayablesORReceivablesResults==null || (top10PayablesORReceivablesResults!=null && top10PayablesORReceivablesResults.getNoofinvoices()==null))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Payables", "There are no Payables :","fetchCustomerTop10PayablesByVendor" )));
			
			System.out.println("Size of the comapny name = " + top10PayablesORReceivablesResults.getCompany_name().length);
			String[] companies=(top10PayablesORReceivablesResults.getCompany_name()[0]).split(",");
			String[] totals=(top10PayablesORReceivablesResults.getTotals()[0]).split(",");
			results.put("companies", companies);
			results.put("totals", totals);
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchCustomerTop10PayablesByVendor");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results Invoice Totals Error", "Error while fetching the Dashboard Results :","fetchCustomerTop10PayablesByVendor" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(results));
		
	
	}
	
	public ResponseEntity<ResponseMessage> fetchVendorTop10PayablesByCustomer(UserLoginDetails userObj)
	{
		Top10PayablesORReceivablesResults top10PayablesORReceivablesResults=null;
		HashMap results=new LinkedHashMap();
		try
		{
			top10PayablesORReceivablesResults=dshbrdCustomerOpenDueInvRepo.fetchVendorTop10PayablesByCustomer(userObj.getPartnerId(),userObj.getPartnerId());
			if(top10PayablesORReceivablesResults==null || (top10PayablesORReceivablesResults!=null && top10PayablesORReceivablesResults.getNoofinvoices()==null))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Receivables", "There are no Receivables :","fetchCustomerTop10PayablesByVendor" )));
			
			
			System.out.println("Size of the comapny name = " + top10PayablesORReceivablesResults.getCompany_name().length);
			String[] companies=(top10PayablesORReceivablesResults.getCompany_name()[0]).split(",");
			String[] totals=(top10PayablesORReceivablesResults.getTotals()[0]).split(",");
			results.put("companies", companies);
			results.put("totals", totals);
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchVendorTop10PayablesByCustomer");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results Invoice Totals Error", "Error while fetching the Dashboard Results :","fetchCustomerTop10PayablesByVendor" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(results));
		
	
	}
	
	
	public ResponseEntity<ResponseMessage> fetchAllReceivablesOrPayablesByCompanies(UserLoginDetails userObj,String transaction_type,String invoice_currency_code)
	{
		List<AllReceivableOrPayableResultsByCompany> allReceivableOrPayableResultsByCompany=null;
		
		try
		{
			if(transaction_type.equalsIgnoreCase("receivables"))
			allReceivableOrPayableResultsByCompany=dshbrdCustomerOpenDueInvRepo.fetchAllCurrentReceivablesByCompanies(userObj.getPartnerId(),invoice_currency_code);
			else if(transaction_type.equalsIgnoreCase("payables"))
				allReceivableOrPayableResultsByCompany=dshbrdCustomerOpenDueInvRepo.fetchAllCurrentPayablesByCompanies(userObj.getPartnerId(),invoice_currency_code);	
			if(allReceivableOrPayableResultsByCompany==null || (allReceivableOrPayableResultsByCompany!=null && allReceivableOrPayableResultsByCompany.size()<=0))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Receivables", "There are no Receivables :","fetchAllReceivablesByCompanies" )));
			
			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchAllReceivablesByCompanies");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results Invoice Totals Error", "Error while fetching the Dashboard Results :","fetchAllReceivablesByCompanies" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(allReceivableOrPayableResultsByCompany));
		
	
	}
	
	
	public ResponseEntity<ResponseMessage> fetchAllReceivablesOrPayablesSummaryResults(UserLoginDetails userObj,String transaction_type)
	{
		List<AllReceivableOrPayableSummaryResults> allReceivableOrPayableSummaryResults=null;
		
		try
		{
			if(transaction_type.equalsIgnoreCase("receivables"))
				allReceivableOrPayableSummaryResults=dshbrdCustomerOpenDueInvRepo.fetchAllReceivablesSummary(userObj.getPartnerId());
			else if(transaction_type.equalsIgnoreCase("payables"))
				allReceivableOrPayableSummaryResults=dshbrdCustomerOpenDueInvRepo.fetchAllPayablesSummary(userObj.getPartnerId());	
			if(allReceivableOrPayableSummaryResults==null || (allReceivableOrPayableSummaryResults!=null && allReceivableOrPayableSummaryResults.size()<=0))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Results", "There are no Results :","fetchAllReceivablesOrPayablesSummaryResults" )));
			
			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchAllReceivablesOrPayablesSummaryResults");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results fetchAllReceivablesOrPayablesSummaryResults Error", "Error while fetching the Dashboard fetchAllReceivablesOrPayablesSummaryResults :","fetchAllReceivablesOrPayablesSummaryResults" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(allReceivableOrPayableSummaryResults));
		
	
	}	
	
	
	
	
	public ResponseEntity<ResponseMessage> fetchAllReceivablesFromCompany(UserLoginDetails userObj,String transaction_type, String company_partner_id, String invoice_currency_code)
	{
		List<AllReceivableOrPayableForCompany> allReceivableOrPayableForCompany=null;
		
		try
		{
			if(transaction_type.equalsIgnoreCase("receivables"))
				allReceivableOrPayableForCompany=dshbrdCustomerOpenDueInvRepo.fetchAllCurrentReceivablesFromCompany(userObj.getPartnerId(),company_partner_id,invoice_currency_code);
			else if(transaction_type.equalsIgnoreCase("payables"))
				allReceivableOrPayableForCompany=dshbrdCustomerOpenDueInvRepo.fetchAllCurrentPayablesFromCompany(userObj.getPartnerId(),company_partner_id,invoice_currency_code);	
			if(allReceivableOrPayableForCompany==null || (allReceivableOrPayableForCompany!=null && allReceivableOrPayableForCompany.size()<=0))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Results", "There are no Results :","fetchAllReceivablesFromCompany" )));
			
			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchAllReceivablesOrPayablesSummaryResults");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results fetchAllReceivablesFromCompany Error", "Error while fetching the Dashboard fetchAllReceivablesFromCompany :","fetchAllReceivablesFromCompany" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(allReceivableOrPayableForCompany));
		
	
	}	
	
	
	//List<AllReceivableOrPayableSummaryForOnboardedOrExternalCompany> fetchAllReceivablesSummaryFromExternalCompany(String vendor_partnerId, String customer_partnerId);
	
	public ResponseEntity<ResponseMessage> fetchAllReceivablesSummaryFromCompany(UserLoginDetails userObj,String transaction_type, String company_partner_id)
	{
		List<AllReceivableOrPayableSummaryForOnboardedOrExternalCompany> allReceivableOrPayableSummaryForOnboardedOrExternalCompany=null;
		
		try
		{
			if(transaction_type.equalsIgnoreCase("receivables") && company_partner_id.startsWith("EX"))
				allReceivableOrPayableSummaryForOnboardedOrExternalCompany=dshbrdCustomerOpenDueInvRepo.fetchAllReceivablesSummaryFromExternalCompany(userObj.getPartnerId(),company_partner_id);
			else if(transaction_type.equalsIgnoreCase("receivables"))
				allReceivableOrPayableSummaryForOnboardedOrExternalCompany=dshbrdCustomerOpenDueInvRepo.fetchAllReceivablesSummaryFromOnboardedCompany(userObj.getPartnerId(),company_partner_id);	
			if(allReceivableOrPayableSummaryForOnboardedOrExternalCompany==null || (allReceivableOrPayableSummaryForOnboardedOrExternalCompany!=null && allReceivableOrPayableSummaryForOnboardedOrExternalCompany.size()<=0))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Results", "There are no Results :","fetchAllReceivablesSummaryFromCompany" )));
			
			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchAllReceivablesSummaryFromCompany");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results fetchAllReceivablesSummaryFromCompany Error", "Error while fetching the Dashboard fetchAllReceivablesSummaryFromCompany :","fetchAllReceivablesSummaryFromCompany" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(allReceivableOrPayableSummaryForOnboardedOrExternalCompany));
		
	
	}	
	

	public ResponseEntity<ResponseMessage> fetchContactDetailsOfCompany(UserLoginDetails userObj,String company_partner_id)
	{
		List<ContactDetailsForOnboardedOrExternalCompany> contactDetailsForOnboardedOrExternalCompany=null;
		
		try
		{
			if(company_partner_id.startsWith("EX"))
				contactDetailsForOnboardedOrExternalCompany=dshbrdCustomerOpenDueInvRepo.fetchContactDetailsOfExternalCompany(company_partner_id);
			else 
				contactDetailsForOnboardedOrExternalCompany=dshbrdCustomerOpenDueInvRepo.fetchContactDetailsOfOnboardedCompany(company_partner_id);	
			if(contactDetailsForOnboardedOrExternalCompany==null || (contactDetailsForOnboardedOrExternalCompany!=null && contactDetailsForOnboardedOrExternalCompany.size()<=0))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Results", "There are no Results :","fetchContactDetailsOfCompany" )));
			
			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchAllReceivablesSummaryFromCompany");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results fetchAllReceivablesSummaryFromCompany Error", "Error while fetching the Dashboard fetchAllReceivablesSummaryFromCompany :","fetchAllReceivablesSummaryFromCompany" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(contactDetailsForOnboardedOrExternalCompany));
		
	
	}	
//fetchInvoiceActivitiesForInternal
	
	public ResponseEntity<ResponseMessage> fetchInvoiceActivities(UserLoginDetails userObj,String company_partner_id)
	{
		List<InvoiceActivities> invoiceActivities=null;
		String partnerId= userObj.getPartnerId();
		
		try
		{
			if(company_partner_id.startsWith("EX"))
				invoiceActivities=dshbrdCustomerOpenDueInvRepo.fetchInvoiceActivitiesForExternal(partnerId,company_partner_id,partnerId,company_partner_id);
			else 
				invoiceActivities=dshbrdCustomerOpenDueInvRepo.fetchInvoiceActivitiesForInternal(partnerId,company_partner_id,partnerId,company_partner_id,partnerId,company_partner_id);	
			if(invoiceActivities==null || (invoiceActivities!=null && invoiceActivities.size()<=0))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Results", "There are no Results :","fetchInvoiceActivities" )));
			
			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchAllReceivablesSummaryFromCompany");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results fetchAllReceivablesSummaryFromCompany Error", "Error while fetching the Dashboard fetchAllReceivablesSummaryFromCompany :","fetchAllReceivablesSummaryFromCompany" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoiceActivities));
		
	
	}	
	
	// Closed invoices Results
	
	
	
	public ResponseEntity<ResponseMessage> fetchAllClosedReceivablesOrPayablesSummaryResults(UserLoginDetails userObj,String transaction_type)
	{
		List<AllClosedReceivableOrPayableSummaryResults> allClosedReceivableOrPayableSummaryResults=null;
		
		try
		{
			if(transaction_type.equalsIgnoreCase("receivables"))
				allClosedReceivableOrPayableSummaryResults=dshbrdCustomerOpenDueInvRepo.fetchAllClosedReceivablesSummary(userObj.getPartnerId());
			else if(transaction_type.equalsIgnoreCase("payables"))
				allClosedReceivableOrPayableSummaryResults=dshbrdCustomerOpenDueInvRepo.fetchAllClosedPayablesSummary(userObj.getPartnerId());	
			if(allClosedReceivableOrPayableSummaryResults==null || (allClosedReceivableOrPayableSummaryResults!=null && allClosedReceivableOrPayableSummaryResults.size()<=0))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Results", "There are no Results :","fetchAllClosedReceivablesOrPayablesSummaryResults" )));
			
			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchAllClosedReceivablesOrPayablesSummaryResults");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results fetchAllClosedReceivablesOrPayablesSummaryResults Error", "Error while fetching the Dashboard fetchAllClosedReceivablesOrPayablesSummaryResults :","fetchAllClosedReceivablesOrPayablesSummaryResults" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(allClosedReceivableOrPayableSummaryResults));
		
	
	}	
	
	public ResponseEntity<ResponseMessage> fetchAllClosedReceivablesOrPayablesByCompanies(UserLoginDetails userObj,String transaction_type, String invoice_currency_code)
	{
		List<AllClosedReceivableOrPayableResultsByCompany> allClosedReceivableOrPayableResultsByCompany=null;
		
		try
		{
			if(transaction_type.equalsIgnoreCase("receivables"))
				allClosedReceivableOrPayableResultsByCompany=dshbrdCustomerOpenDueInvRepo.fetchAllClosedReceivablesByCompanies(userObj.getPartnerId(),invoice_currency_code);
			else if(transaction_type.equalsIgnoreCase("payables"))
				allClosedReceivableOrPayableResultsByCompany=dshbrdCustomerOpenDueInvRepo.fetchAllClosedPayablesByCompanies(userObj.getPartnerId(),invoice_currency_code);	
			if(allClosedReceivableOrPayableResultsByCompany==null || (allClosedReceivableOrPayableResultsByCompany!=null && allClosedReceivableOrPayableResultsByCompany.size()<=0))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Receivables", "There are no Receivables :","fetchAllReceivablesByCompanies" )));
			
			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchAllClosedReceivablesByCompanies");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results Invoice Totals Error", "Error while fetching the Dashboard Results :","fetchAllClosedReceivablesByCompanies" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(allClosedReceivableOrPayableResultsByCompany));
		
	
	}
	
	
	
	public ResponseEntity<ResponseMessage> fetchAllClosedReceivablesOrPayablesFromCompany(UserLoginDetails userObj,String transaction_type, String company_partner_id,String invoice_currency_code)
	{
		List<AllReceivableOrPayableForCompany> allReceivableOrPayableForCompany=null;
		
		try
		{
			if(transaction_type.equalsIgnoreCase("receivables"))
				allReceivableOrPayableForCompany=dshbrdCustomerOpenDueInvRepo.fetchAllClosedReceivablesFromCompany(userObj.getPartnerId(),company_partner_id,invoice_currency_code);
			else if(transaction_type.equalsIgnoreCase("payables"))
				allReceivableOrPayableForCompany=dshbrdCustomerOpenDueInvRepo.fetchAllClosedPayablesFromCompany(userObj.getPartnerId(),company_partner_id,invoice_currency_code);	
			if(allReceivableOrPayableForCompany==null || (allReceivableOrPayableForCompany!=null && allReceivableOrPayableForCompany.size()<=0))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Results", "There are no Results :","fetchAllClosedReceivablesFromCompany" )));
			
			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchAllClosedReceivablesFromCompany");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results fetchAllClosedReceivablesFromCompany Error", "Error while fetching the Dashboard fetchAllClosedReceivablesFromCompany :","fetchAllClosedReceivablesFromCompany" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(allReceivableOrPayableForCompany));
		
	
	}	
	
	
	//Cashflow
	
	
	
	public ResponseEntity<ResponseMessage> fetchResultsForCashFlow(UserLoginDetails userObj,Date start_date, Date end_date)
	{
		List<CashFlowResults> cashFlowReceivablesResults=null;
		List<CashFlowResults> cashFlowPayablesResults=null;
		CashFlowReceivablesPayablesResults cashFlowReceivablesPayablesResults=new CashFlowReceivablesPayablesResults();
		String partnerId= userObj.getPartnerId();
		
		try
		{
			
			cashFlowReceivablesResults=dshbrdCustomerOpenDueInvRepo.fetchReceivablesForCashFlow(partnerId,start_date,end_date);
				cashFlowReceivablesPayablesResults.setReceivables(cashFlowReceivablesResults);
				cashFlowPayablesResults=dshbrdCustomerOpenDueInvRepo.fetchPayablesForCashFlow(partnerId,start_date,end_date);
				cashFlowReceivablesPayablesResults.setPayables(cashFlowPayablesResults);
				
				//if(cashFlowResults==null || (cashFlowResults!=null && cashFlowResults.size()<=0))
				//return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no Receivables Results", "There are no Receivables Results :","fetchReceivablesForCashFlow" )));
			
			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("DshbrdCustomerDefaultService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchReceivablesForCashFlow");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch Dashboard Results fetchReceivablesForCashFlow Error", "Error while fetching the Dashboard fetchReceivablesForCashFlow :","fetchReceivablesForCashFlow" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(cashFlowReceivablesPayablesResults));
		
	
	}
	
	
    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }

    public static Date subtractDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return new Date(c.getTimeInMillis());
    }
	

}
