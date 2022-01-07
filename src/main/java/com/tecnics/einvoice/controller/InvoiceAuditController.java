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
import com.tecnics.einvoice.entity.InvoiceAudit;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.entity.PartnerDetails;
import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.InvoiceAuditService;
import com.tecnics.einvoice.service.PartnerDetailsService;
import com.tecnics.einvoice.serviceImpl.InvoiceDetailsServiceImpl;
import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class InvoiceAuditController extends BaseController {
	
	@Autowired
	InvoiceAuditService invoiceAuditService;
	
	
	@Autowired
	InvoiceDetailsServiceImpl invoiceDetailsServiceImpl;
	
	
	@RequestMapping("/verifyInvoiceAuditController")  
    public String verify()  
    {  
        return "The InvoiceAuditController is up and running";  
    } 

	
	/***
	 * 
	 * @param invoiceaudit
	 * @param token
	
	 * @return
	 */
	@PostMapping("/invoiceaudit/auditSave")

	public ResponseEntity<ResponseMessage> save(@RequestBody InvoiceAudit invoiceAudit,@RequestHeader("authorization") String token) 
		{
			invoiceAudit.setActionBy(getUserName(token));
			invoiceAudit.setActionDate(new Timestamp(System.currentTimeMillis()));
			TransactionResponse response = invoiceAuditService.save(invoiceAudit,getUserName(token));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));		
	}
}