package com.tecnics.einvoice.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.Repo.InvoiceDocumentDetailRepo;
import com.tecnics.einvoice.Repo.InvoiceStatusTrackerRepo.InvoiceStatusTrackerResults;
import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.entity.InvoiceStatusTracker;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;

import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.InvoiceStatusTrackerService;

import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class InvoiceStatusTrackerController extends BaseController {
	
	@Autowired
	InvoiceStatusTrackerService invoiceStatusTrackerService;
	
	
	@Autowired
	InvoiceDocumentDetailRepo invoicedetailRepo;
	
	
	@RequestMapping("/verifyInvoiceStatusTrackerController")  
    public String verify()  
    {  
        return "The InvoiceTrackerController is up and running";  
    } 
	
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("invoicestatustracker/getinvoicestatusdetails/{documentRefId}")
	public ResponseEntity<ResponseMessage> getInvoiceStatusDetails(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		try {
			List<InvoiceStatusTracker> response = invoiceStatusTrackerService.findByDocumentRefId(documentRefId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_STATUS_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_STATUS_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("invoicestatustracker/getinvoicestatusdetailsforvendor/{documentRefId}")
	public ResponseEntity<ResponseMessage> getInvoiceStatusDetailsForVendor(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("user Obj = " + userObj);
			System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
			List<InvoiceStatusTrackerResults> response = invoiceStatusTrackerService.fetchAccessibleStatus(userObj.getPartnerId(), documentRefId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_STATUS_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_STATUS_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("invoicestatustracker/getinvoicestatusdetailsforcustomer/{documentRefId}")
	public ResponseEntity<ResponseMessage> getInvoiceStatusDetailsForCustomer(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		try {
			
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("user Obj = " + userObj);
			System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
			
			List<InvoiceStatusTrackerResults> response = invoiceStatusTrackerService.fetchAccessibleStatus(userObj.getPartnerId(), documentRefId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_STATUS_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_STATUS_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}

	
	/***
	 * 
	 * @param invoicetatustrackercontroller
	 * @param token
	
	 * @return
	 */
	@PostMapping("/invoicestatustracker/statussave")

	public ResponseEntity<ResponseMessage> save(@RequestBody InvoiceStatusTracker invoiceStatusTracker,@RequestHeader("authorization") String token) 
		{

		invoiceStatusTracker.setActionBy(getUserName(token));
		System.out.println("DocumentRefId =*" + invoiceStatusTracker.getDocumentRefId() +"*");
		System.out.println("action =*" + invoiceStatusTracker.getAction() +"*");
		int cnt=invoicedetailRepo.setInvoice_statusForInvoiceDocumentDetail(invoiceStatusTracker.getAction(), invoiceStatusTracker.getDocumentRefId());
		System.out.println("No of Rows Updated in InvoiceDocumentDetail= " + cnt);
		invoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
			TransactionResponse response = invoiceStatusTrackerService.save(invoiceStatusTracker,getUserName(token));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));		
	}

}

