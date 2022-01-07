package com.tecnics.einvoice.controller;

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


import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.InvoiceDocumentDetailService;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.FetchMailUtil;
import com.tecnics.einvoice.util.SendMailUtil;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class EMailController extends BaseController {
	

	@Autowired
	InvoiceDocumentDetailService detailService;
	
	
	@RequestMapping("/verifyEMailController")  
    public String verify()  
    {  
        return "The EMailController is up and running";  
    } 

	
	@GetMapping("/email/sendSample")
	public ResponseEntity<ResponseMessage> sendSample(@RequestHeader("authorization") String token) {
		System.out.println("inside fetch Email");
		System.out.println("Toke String is : " + token);
		System.out.println("User Name is : " + getUserName(token));
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + userObj);
		System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
		System.out.println("User Name from Obj = " + userObj.getUserId());
		System.out.println("Partner Type from Obj = " + userObj.getPartnerType());
		java.util.List <String>roles=userObj.getRoles();
		System.out.println("Roles from Obj = " );
		for (String role : roles) { 
           
            System.out.println(role);
        }
		String response="success";
		try
		{
			
	
		SendMailUtil emailUtil = new SendMailUtil();
		//String attachmentIds[]= {"VGhpcyBpcyBzYW1wbGUgZW1haWw="};
		String attachmentIds[]={"data:text/plain;base64,VGhpcyBpcyBzYW1wbGUgZW1haWw="};
		String base64Flags[]= {"true"};
		String attachmentNames[]={"Invoice.txt"};
		String mimeTypes[]= {"text/plain"};
		
		emailUtil.sendEmail("inkreta.tecnics@gmail.com", "Welcome@123", new String[] {"noreplyekaarya@gmail.com", ""}, new String[] {}, new String[] {}, "Test email Subject at 15:28 13-09-2021", "Hi, How are you at 13:28 13-09-2021", base64Flags,attachmentIds,attachmentNames,mimeTypes,null,null,null);
		response="sent";
		}catch(Exception e)
		{
			System.out.println("Error while sending e-mail" + e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	@GetMapping("/email/fetchMail")
	public ResponseEntity<ResponseMessage> fetchMail(@RequestHeader("authorization") String token) {
		System.out.println("inside fetch Email");
		System.out.println("Toke String is : " + token);
		System.out.println("User Name is : " + getUserName(token));
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + userObj);
		System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
		System.out.println("User Name from Obj = " + userObj.getUserId());
		System.out.println("Partner Type from Obj = " + userObj.getPartnerType());
		java.util.List <String>roles=userObj.getRoles();
		System.out.println("Roles from Obj = " );
		for (String role : roles) { 
           
            System.out.println(role);
        }
		ResponseEntity<ResponseMessage> response=null;
		try
		{
			FetchMailUtil fetchMailUtil = new FetchMailUtil();
			String userName = "inkreta.tecnics@gmail.com";
			String password = "Welcome@123";
			response=fetchMailUtil.run(userName, password,userObj,detailService);
	
		//response="fetchMail invoked Successfully";
		}catch(Exception e)
		{
			System.out.println("Error while fetch e-mail" + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
}