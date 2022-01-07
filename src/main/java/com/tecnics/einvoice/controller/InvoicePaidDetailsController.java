
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


import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.entity.InvoicePaidDetails;

import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.InvoicePaidDetailsService;
import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class InvoicePaidDetailsController extends BaseController {
	
	@Autowired
	InvoicePaidDetailsService invoicePaidDetailsService;
	
	
	
	
	@RequestMapping("/invoicepaid/verifyInvoicePaidDetailsController")  
    public String verify()  
    {  
        return "The InvoicePaidDetailsController is up and running";  
    } 
	
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("invoicepaid/getpaiddetails/{documentRefId}")
	public ResponseEntity<ResponseMessage> getInvoicePaidDetails(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		try {
			ResponseEntity<ResponseMessage>response = invoicePaidDetailsService.fetchPaidDetailsByDocumentRefId(documentRefId);
			return response;

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
	@PostMapping("invoicepaid/save")

	public ResponseEntity<ResponseMessage> save(@RequestBody InvoicePaidDetails invoicePaidDetails,@RequestHeader("authorization") String token) 
		{
		ResponseEntity<ResponseMessage> response=null;
		try
		{
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("Inside save of InvoicePaidDetailsController");
		response=invoicePaidDetailsService.save(invoicePaidDetails, userObj);
		return response;

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("InvoicePaidDetails save error",
					Ex.formatMessage("InvoicePaidDetails save error", getStackTrace(e)))));
		}
		
				
	}

}



