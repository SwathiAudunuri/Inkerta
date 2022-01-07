package com.tecnics.einvoice.controller;



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
import org.springframework.web.bind.annotation.RestController;


import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.InvoiceForwardModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;

import com.tecnics.einvoice.service.InvoiceForwardService;
import com.tecnics.einvoice.service.UserManagementService;
import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")

public class InvoiceForwardController extends BaseController {
	
	@Autowired
	InvoiceForwardService invoiceForwardService;	
	
	@Autowired
	UserManagementService userManagementService;
	
	
	@RequestMapping("/invoiceworkflow/verifyInvoiceForwardController")  
    public String verify()  
    {  
        return "The InvoiceForwardController is up and running";  
    } 
	
	
	@GetMapping("/invoiceworkflow/getusersforforward")
	
	public ResponseEntity<ResponseMessage> getUserListForForward(@RequestHeader("authorization") String token) {
		System.out.println("inside getUserListForForward of InvoiceForwardController");		
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + userObj);
		String partnerId= userObj.getPartnerId();	
		System.out.println("Partner Type from Obj = " + userObj.getPartnerType());			
		
		return userManagementService.getUserListForForward(partnerId, userObj.getUserId());
	}
	/***
	 * 
	 * @param invoicetatustrackercontroller
	 * @param token
	
	 * @return
	 */
	@PostMapping("/invoiceworkflow/forward")

	public ResponseEntity<ResponseMessage> save(@RequestBody InvoiceForwardModel invoiceForwardModel,@RequestHeader("authorization") String token) 
		{

		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("Inside invoiceworkflow forward");
		
		String partnerId=userObj.getPartnerId();
		System.out.println("PartnerId from invoiceworkflow forward " +partnerId);
		
		return invoiceForwardService.save(invoiceForwardModel, userObj);
	}
	
	/***
	 * 
	 * @param invoicetatustrackercontroller
	 * @param token
	
	 * @return
	 */
	@PostMapping("/invoiceworkflow/release")

	public ResponseEntity<ResponseMessage> release(@RequestBody InvoiceForwardModel invoiceForwardModel,@RequestHeader("authorization") String token) 
		{

		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("Inside invoiceworkflow release");
		
		String partnerId=userObj.getPartnerId();
		System.out.println("PartnerId from invoiceworkflow release " +partnerId);
		
		return invoiceForwardService.release(invoiceForwardModel, userObj);
	}
	
	/***
	 * 
	 * @param invoicetatustrackercontroller
	 * @param token
	
	 * @return
	 */
	@PostMapping("/externalinvoice/invoiceworkflow/forward")

	public ResponseEntity<ResponseMessage> externalinvoiceAssign(@RequestBody InvoiceForwardModel invoiceForwardModel,@RequestHeader("authorization") String token) 
		{

		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("Inside invoiceworkflow forward");
		
		String partnerId=userObj.getPartnerId();
		System.out.println("PartnerId from invoiceworkflow forward " +partnerId);
		
		return invoiceForwardService.externalinvoiceAssign(invoiceForwardModel, userObj);
	}
	
	/***
	 * 
	 * @param invoicetatustrackercontroller
	 * @param token
	
	 * @return
	 */
	@PostMapping("/externalinvoice/invoiceworkflow/release")

	public ResponseEntity<ResponseMessage> externalInvoiceRelease(@RequestBody InvoiceForwardModel invoiceForwardModel,@RequestHeader("authorization") String token) 
		{

		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("Inside invoiceworkflow release");
		
		String partnerId=userObj.getPartnerId();
		System.out.println("PartnerId from invoiceworkflow release " +partnerId);
		
		return invoiceForwardService.externalInvoiceRelease(invoiceForwardModel, userObj);
	}


}
