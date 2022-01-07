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
import com.tecnics.einvoice.model.SendReminderModel;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.InvoicePaidDetailsService;
import com.tecnics.einvoice.service.InvoiceReminderService;
import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class InvoiceReminderController extends BaseController {
	
	@Autowired
	InvoiceReminderService invoiceReminderService;
	
	
	
	
	@RequestMapping("/invoicereminder/verifyInvoiceReminderController")  
    public String verify()  
    {  
        return "The InvoiceReminderTemplatesController is up and running";  
    } 
	
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("invoicereminder/gettemplatenames")
	public ResponseEntity<ResponseMessage> getTemplateNames(@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			ResponseEntity<ResponseMessage>response = invoiceReminderService.fetchInvoiceReminderTemplateNames(userObj);
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Error in getTemplateNames of InvoiceReminderTemplatesController", "Error in getTemplateNames of InvoiceReminderTemplatesController:","getTemplateNames" )));
			
		}
	}
	
	
	@GetMapping("invoicereminder/gettemplate/{templateId}/{documentRefId}/{type_of_invoice}")
	public ResponseEntity<ResponseMessage> getTemplate(@PathVariable Integer templateId,@PathVariable String documentRefId,@PathVariable String type_of_invoice , @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			ResponseEntity<ResponseMessage>response = invoiceReminderService.fetchInvoiceReminderTemplate(userObj, templateId, documentRefId, type_of_invoice);
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Error in getTemplateNames of InvoiceReminderTemplatesController", "Error in getTemplateNames of InvoiceReminderTemplatesController:","getTemplateNames" )));
			
		}
	}
	
	@PostMapping("invoicereminder/sendreminder")
	public ResponseEntity<ResponseMessage> sendReminder(@RequestBody SendReminderModel request, @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			ResponseEntity<ResponseMessage>response = invoiceReminderService.sendReminder(request, userObj);
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Error in getTemplateNames of InvoiceReminderTemplatesController", "Error in getTemplateNames of InvoiceReminderTemplatesController:","getTemplateNames" )));
			
		}
	}
	
	
	
	

}




