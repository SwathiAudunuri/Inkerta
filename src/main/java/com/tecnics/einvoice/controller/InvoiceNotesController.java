package com.tecnics.einvoice.controller;


import java.sql.Timestamp;

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

import com.tecnics.einvoice.entity.ExternalInvoiceNotes;
import com.tecnics.einvoice.entity.InvoiceNotes;


import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.ExternalInvoiceNotesService;
import com.tecnics.einvoice.service.InvoiceNotesService;
import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")

public class InvoiceNotesController extends BaseController {
	
	@Autowired
	InvoiceNotesService invoiceNotesService;
	
	@Autowired
	ExternalInvoiceNotesService externalInvoiceNotesService;
	
	@RequestMapping("/verifyInvoiceNotesController")  
    public String verify()  
    {  
        return "The InvoiceNotesController is up and running";  
    } 

	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/invoicenotes/fetchNotes/{documentRefId}")
	public ResponseEntity<ResponseMessage> fetchNotes(@PathVariable String documentRefId,@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside fetchNotes");
			
			String partnerId=userObj.getPartnerId();
			System.out.println("Partner Roles from user Obj" + userObj.getPartnerRoles());
			System.out.println("User Alias from user Obj" + userObj.getUserAlias());
			
			System.out.println("PartnerId from fetchNotes " +partnerId );
			ResponseEntity<ResponseMessage> response = invoiceNotesService.fetchNotesByPartnerIdAndDocumenrRefId(partnerId,documentRefId);
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_LIST_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_LIST_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}

	}
	
	/***
	 * 
	 * @param InvoiceNotes
	 * @param token
	
	 * @return
	 */
	@PostMapping("/invoicenotes/notesSave")

	public ResponseEntity<ResponseMessage> save(@RequestBody InvoiceNotes invoiceNotes,@RequestHeader("authorization") String token) 
		{
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("Inside invoice Notes save");
		
		String partnerId=userObj.getPartnerId();
		invoiceNotes.setCreatedBy(userObj.getUserId());
		invoiceNotes.setPartnerId(userObj.getPartnerId());
		invoiceNotes.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		System.out.println("PartnerId from invoice Notes save " +partnerId );		
			return invoiceNotesService.save(invoiceNotes, userObj);
	}
	
	/***
	 * 
	 * @param InvoiceNotes
	 * @param token
	
	 * @return
	 */
	@PostMapping("/externalinvoice/invoicenotes/notesSave")

	public ResponseEntity<ResponseMessage> externalInvoiceNotesSave(@RequestBody ExternalInvoiceNotes externalInvoiceNotes,@RequestHeader("authorization") String token) 
		{
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("Inside externalinvoice Notes save");
		
		String partnerId=userObj.getPartnerId();
		externalInvoiceNotes.setCreatedBy(userObj.getUserId());
		externalInvoiceNotes.setPartnerId(userObj.getPartnerId());
		externalInvoiceNotes.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		System.out.println("PartnerId from invoice notes save " +partnerId );		
			return externalInvoiceNotesService.save(externalInvoiceNotes, userObj);
	}
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/externalinvoice/invoicenotes/fetchNotes/{documentRefId}")
	public ResponseEntity<ResponseMessage> externalInvoiceFetchNotes(@PathVariable String documentRefId,@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside externalinvoice fetchNotes");
			
			String partnerId=userObj.getPartnerId();
			System.out.println("Partner Roles from user Obj" + userObj.getPartnerRoles());
			System.out.println("User Alias from user Obj" + userObj.getUserAlias());
			
			System.out.println("PartnerId from fetchNotes " +partnerId );
			ResponseEntity<ResponseMessage> response = externalInvoiceNotesService.fetchNotesByPartnerIdAndDocumenrRefId(partnerId, documentRefId);
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_LIST_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_LIST_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}

	}
	
}
