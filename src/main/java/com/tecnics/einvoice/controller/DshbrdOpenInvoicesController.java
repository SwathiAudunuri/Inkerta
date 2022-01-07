package com.tecnics.einvoice.controller;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.CashFlowFilters;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.DshbrdOpenInvoicesDueService;
import com.tecnics.einvoice.service.ExternalPartnerDetailsService;
import com.tecnics.einvoice.service.PartnerDetailsService;
import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class DshbrdOpenInvoicesController extends BaseController {

	@Autowired
	DshbrdOpenInvoicesDueService dshbrdCustomerDefaultService;
	
	@Autowired
	ExternalPartnerDetailsService externalPartnerDetailsService;
	@Autowired
	PartnerDetailsService partnerDetailService;
	
	@RequestMapping("/verifyDshbrdCustomerDefaultController")  
    public String verify()  
    {  
		System.out.println("inside DshbrdCustomerDefaultController");
        return "The DshbrdCustomerDefaultController is up and running";  
    } 
	
	@GetMapping("/dashboard/customer/getInvoiceOverdueTotals")
	public ResponseEntity<ResponseMessage> getCustomerInvoiceOverdueTotals( @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside customer getInvoiceOverdueTotals ");
		
		return dshbrdCustomerDefaultService.fetchCustomerOpenDueInvoicesByPartnerId(userObj);

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKeyMessage(), "getInvoiceOverdueTotals"), getStackTrace(e))));
		}
	} 
	
	
	@GetMapping("/dashboard/vendor/getInvoiceOverdueTotals")
	public ResponseEntity<ResponseMessage> getVendorInvoiceOverdueTotals( @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside vendor getInvoiceOverdueTotals ");
		
		return dshbrdCustomerDefaultService.fetchVendorOpenDueInvoicesByPartnerId(userObj);

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKeyMessage(), "getInvoiceOverdueTotals"), getStackTrace(e))));
		}
	} 
	
	
	
	@GetMapping("/dashboard/vendor/getCustomerTop10PayablesByVendor")
	public ResponseEntity<ResponseMessage> getCustomerTop10PayablesByVendor( @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside vendor getCustomerTop10PayablesByVendor ");
		
		return dshbrdCustomerDefaultService.fetchCustomerTop10PayablesByVendor(userObj);

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKeyMessage(), "getCustomerTop10PayablesByVendor"), getStackTrace(e))));
		}
	} 
	
	@GetMapping("/dashboard/vendor/getVendorTop10PayablesByCustomer")
	public ResponseEntity<ResponseMessage> getVendorTop10PayablesByCustomer( @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside vendor getVendorTop10PayablesByCustomer ");
		
		return dshbrdCustomerDefaultService.fetchVendorTop10PayablesByCustomer(userObj);

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKeyMessage(), "getCustomerTop10PayablesByVendor"), getStackTrace(e))));
		}
	} 
	
	@GetMapping("/dashboard/receivables/getCurrentReceivablesByAllCompanies/{invoiceCurrencyCode}")
	public ResponseEntity<ResponseMessage> getReceivablesByAllCompanies(@PathVariable String invoiceCurrencyCode, @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside getReceivablesByAllCompanies ");
		
		return dshbrdCustomerDefaultService.fetchAllReceivablesOrPayablesByCompanies(userObj, "receivables",invoiceCurrencyCode);

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKeyMessage(), "getReceivablesByAllCompanies"), getStackTrace(e))));
		}
	} 
	
	//fetchAllReceivablesOrPayablesSummaryResults
	
	@GetMapping("/dashboard/receivables/getCurrentReceivablesSummary")
	public ResponseEntity<ResponseMessage> getReceivablesSummary( @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside getReceivablesSummary ");
		
		return dshbrdCustomerDefaultService.fetchAllReceivablesOrPayablesSummaryResults(userObj, "receivables");

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("getReceivablesSummary Error",
					"getReceivablesSummary Error", "getReceivablesSummary")));
		}
	} 	
	
	@GetMapping("/dashboard/payables/getCurrentPayablesSummary")
	public ResponseEntity<ResponseMessage> getPayablesSummary( @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside getReceivablesSummary ");
		
		return dshbrdCustomerDefaultService.fetchAllReceivablesOrPayablesSummaryResults(userObj, "payables");

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("getPayablesSummary Error",
					"getPayablesSummary Error", "getPayablesSummary")));
		}
	}
	
	@GetMapping("/dashboard/receivables/getCurrentReceivablesFromCompany/{customer_partner_id}/{invoiceCurrencyCode}")
	public ResponseEntity<ResponseMessage> getReceivablesFromCompany(@PathVariable String customer_partner_id,@PathVariable String invoiceCurrencyCode, @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside getReceivablesFromCompany ");
		
		return dshbrdCustomerDefaultService.fetchAllReceivablesFromCompany(userObj, "receivables",customer_partner_id,invoiceCurrencyCode );

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("getReceivablesFromCompany Error",
					"getReceivablesFromCompany Error", "getReceivablesFromCompany")));
		}
	}
	
	@GetMapping("/dashboard/receivables/getReceivablesSummaryFromCompany/{customer_partner_id}")
	public ResponseEntity<ResponseMessage> getReceivablesSummaryFromCompany(@PathVariable String customer_partner_id, @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside getReceivablesSummaryFromCompany ");
		
		return dshbrdCustomerDefaultService.fetchAllReceivablesSummaryFromCompany(userObj, "receivables",customer_partner_id );

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("getReceivablesSummaryFromCompany Error",
					"getReceivablesSummaryFromCompany Error", "getReceivablesSummaryFromCompany")));
		}
	}
	
	
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/dashboard/getpartnercontactdetails/{partnerId}")
	public ResponseEntity<ResponseMessage> fetchContactDetailsOfCompany(@PathVariable String partnerId,@RequestHeader("authorization") String token) {
		ResponseEntity<ResponseMessage> response=null;
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			
			System.out.println("Inside DshbrdOpenInvoicesController fetchContactDetailsOfCompany   ");
					
			response = dshbrdCustomerDefaultService.fetchContactDetailsOfCompany(userObj, partnerId);
			
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("Fetch Partner Details fetchContactDetailsOfCompany Error", e.getMessage())));
		}
	}
	

//fetchInvoiceActivities	
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/dashboard/getinvoiceactivities/{partnerId}")
	public ResponseEntity<ResponseMessage> fetchInvoiceActivitiesOfCompany(@PathVariable String partnerId,@RequestHeader("authorization") String token) {
		ResponseEntity<ResponseMessage> response=null;
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			
			System.out.println("Inside DshbrdOpenInvoicesController getinvoiceactivities   ");
					
			response = dshbrdCustomerDefaultService.fetchInvoiceActivities(userObj, partnerId);
			
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("Fetch Partner Details fetchContactDetailsOfCompany Error", e.getMessage())));
		}
	}
	
	
	//closed invoices
	
	@GetMapping("/dashboard/receivables/getClosedReceivablesSummary")
	public ResponseEntity<ResponseMessage> getClosedReceivablesSummary( @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside getReceivablesSummary ");
		
		return dshbrdCustomerDefaultService.fetchAllClosedReceivablesOrPayablesSummaryResults(userObj, "receivables");

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("getClosedReceivablesSummary Error",
					"getClosedReceivablesSummary Error", "getClosedReceivablesSummary")));
		}
	} 	
	
	@GetMapping("/dashboard/payables/getClosedPayablesSummary")
	public ResponseEntity<ResponseMessage> getClosedPayablesSummary( @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside getReceivablesSummary ");
		
		return dshbrdCustomerDefaultService.fetchAllClosedReceivablesOrPayablesSummaryResults(userObj, "payables");

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("getClosedPayablesSummary Error",
					"getClosedPayablesSummary Error", "getClosedPayablesSummary")));
		}
	}
	
	
	@GetMapping("/dashboard/receivables/getClosedReceivablesByAllCompanies/{invoiceCurrencyCode}")
	public ResponseEntity<ResponseMessage> getClosedReceivablesByAllCompanies( @PathVariable String invoiceCurrencyCode,@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside getClosedReceivablesByAllCompanies ");
			if(invoiceCurrencyCode!=null && invoiceCurrencyCode.length()>=2)		
		return dshbrdCustomerDefaultService.fetchAllClosedReceivablesOrPayablesByCompanies(userObj, "receivables",invoiceCurrencyCode);
			else
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("Mandatory Currencycode parameter is missing",
						"getClosedReceivablesByAllCompanies Mandatory Currencycode parameter is missing Error", "getClosedPayablesSummary")));
				
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.DSHBRD_CUSTOMER_FETCH_ERROR.getKeyMessage(), "getClosedReceivablesByAllCompanies"), getStackTrace(e))));
		}
	} 
	
	
	@GetMapping("/dashboard/receivables/getClosedReceivablesFromCompany/{customer_partner_id}/{invoiceCurrencyCode}")
	public ResponseEntity<ResponseMessage> getClosedReceivablesFromCompany(@PathVariable String customer_partner_id, @PathVariable String invoiceCurrencyCode, @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);		
			System.out.println("inside getClosedReceivablesFromCompany ");
		
		return dshbrdCustomerDefaultService.fetchAllClosedReceivablesOrPayablesFromCompany(userObj, "receivables",customer_partner_id,invoiceCurrencyCode);

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("getClosedReceivablesFromCompany Error",
					"getClosedReceivablesFromCompany Error", "getClosedReceivablesFromCompany")));
		}
	}
	
	
	
	//cashflow
	
	
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@PostMapping("/dashboard/getcashflowresults")
	public ResponseEntity<ResponseMessage> fetchResultsForCashFlow(@RequestBody CashFlowFilters cashFlowFilters,@RequestHeader("authorization") String token) {
		ResponseEntity<ResponseMessage> response=null;
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			Date start_date=null;
			Date end_date=null;
			System.out.println("Inside DshbrdOpenInvoicesController getinvoiceactivities   ");
			if(cashFlowFilters.getStart_date()==null || cashFlowFilters.getEnd_date()==null)
			{
				 java.sql.Date todaysDate = new java.sql.Date(new java.util.Date().getTime());

			        int startDay =1;
			        int endDay=30;

			        start_date = DshbrdOpenInvoicesDueService.addDays(todaysDate, startDay);
			        end_date = DshbrdOpenInvoicesDueService.subtractDays(todaysDate, endDay);
				
			}
			else
			{
			start_date=java.sql.Date.valueOf(cashFlowFilters.getStart_date());
			end_date=java.sql.Date.valueOf(cashFlowFilters.getEnd_date());
			}
			response = dshbrdCustomerDefaultService.fetchResultsForCashFlow(userObj, start_date, end_date);
			
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("Fetch Partner Details fetchContactDetailsOfCompany Error", e.getMessage())));
		}
	}

}
