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

import com.tecnics.einvoice.Repo.ExternalInvoiceDocumentDetailsRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceStatusTrackerRepo.ExternalInvoiceStatusTrackerResults;
import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.entity.ExternalInvoiceStatusTracker;

import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;

import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.ExternalInvoiceStatusTrackerService;

import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class ExternalInvoiceStatusTrackerController extends BaseController {
	
	@Autowired
	ExternalInvoiceStatusTrackerService externalInvoiceStatusTrackerService;
	
	
	@Autowired
	ExternalInvoiceDocumentDetailsRepo externalInvoiceDocumentDetailsRepo;
	
	
	@RequestMapping("/externalinvoice/verifyExternalInvoiceStatusTrackerController")  
    public String verify()  
    {  
        return "The ExternalInvoiceStatusTrackerController is up and running";  
    } 
	
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("externalinvoice/externalinvoicestatustracker/getinvoicestatusdetails/{documentRefId}")
	public ResponseEntity<ResponseMessage> getInvoiceStatusDetails(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		try {
			List<ExternalInvoiceStatusTracker> response = externalInvoiceStatusTrackerService.findByDocumentRefId(documentRefId);
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
	@GetMapping("externalinvoice/externalinvoicestatustracker/getinvoicestatusdetailsforcustomer/{documentRefId}")
	public ResponseEntity<ResponseMessage> getInvoiceStatusDetailsForCustomer(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		try {
			
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("user Obj = " + userObj);
			System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
			
			List<ExternalInvoiceStatusTrackerResults> response = externalInvoiceStatusTrackerService.fetchByDocumentRefId(documentRefId);
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
	@PostMapping("externalinvoice/externalinvoicestatustracker/statussave")

	public ResponseEntity<ResponseMessage> save(@RequestBody ExternalInvoiceStatusTracker externalInvoiceStatusTracker,@RequestHeader("authorization") String token) 
		{

		externalInvoiceStatusTracker.setActionBy(getUserName(token));
		System.out.println("DocumentRefId =*" + externalInvoiceStatusTracker.getDocumentRefId() +"*");
		System.out.println("action =*" + externalInvoiceStatusTracker.getAction() +"*");
		int cnt=externalInvoiceDocumentDetailsRepo.setInvoice_statusForInvoiceDocumentDetail(externalInvoiceStatusTracker.getAction(), externalInvoiceStatusTracker.getDocumentRefId());
		System.out.println("No of Rows Updated in InvoiceDocumentDetail= " + cnt);
		externalInvoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
			TransactionResponse response = externalInvoiceStatusTrackerService.save(externalInvoiceStatusTracker,getUserName(token));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));		
	}

}


