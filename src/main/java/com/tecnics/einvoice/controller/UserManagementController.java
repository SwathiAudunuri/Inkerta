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

import com.tecnics.einvoice.entity.InvoiceStatus;
import com.tecnics.einvoice.entity.InvoiceStatusTracker;
import com.tecnics.einvoice.entity.PartnerUserRoles;
import com.tecnics.einvoice.entity.UserManagement;
import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.User;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.UserManagementService;

import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserManagementController extends BaseController {
	
	@Autowired
	UserManagementService userManagementService;
	
	@RequestMapping("/usermanagement/verifyUserManagementController")  
    public String verify()  
    {  
        return "The UserManagementController is up and running";  
    } 
	
	
	
	
	@GetMapping("/usermanagement/getuserlist")
	public ResponseEntity<ResponseMessage> getUserList(@RequestHeader("authorization") String token) {
		System.out.println("inside getUsers of UserManagementController");		
		UserLoginDetails userObj=getUserObjFromToken(token);
		java.util.List <String>roles=userObj.getRoles();
		System.out.println("Roles from Obj = " );
		ResponseEntity<ResponseMessage> response =null;
		
		if(roles.contains("businesspartner_manager") || roles.contains("businesspartner_admin") || roles.contains("customer_manager") || roles.contains("customer_admin") || roles.contains("vendor_manager") || roles.contains("vendor_admin") )
		{
		response = 	userManagementService.getUserListByPartner(userObj);
		}
		else
		{
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.UNAUTHORIZED.getKey(),
					Ex.formatMessage(Ex.UNAUTHORIZED.getKeyMessage()), Ex.UNAUTHORIZED.getKeyMessage() )));
		}
		
		
		return response;
	}
	
	
	@GetMapping("/usermanagement/getusers")
	public ResponseEntity<ResponseMessage> getUsers(@RequestHeader("authorization") String token) {
		System.out.println("inside getUsers of UserManagementController");		
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + userObj);
		String partnerId= userObj.getPartnerId();	
		System.out.println("Partner Type from Obj = " + userObj.getPartnerType());
		java.util.List <String>roles=userObj.getRoles();
		System.out.println("Roles from Obj = " );
		List<UserManagement> response =null;
		for (String role : roles) { 
           
            System.out.println(role);
        }
		if(roles.contains("businesspartner_manager") || roles.contains("businesspartner_admin") || roles.contains("customer_manager") || roles.contains("customer_admin") || roles.contains("vendor_manager") || roles.contains("vendor_admin") )
		{
		response = 	userManagementService.findByPartnerId(partnerId);
		}
		else
		{
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.UNAUTHORIZED.getKey(),
					Ex.formatMessage(Ex.UNAUTHORIZED.getKeyMessage()), Ex.UNAUTHORIZED.getKeyMessage() )));
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	@GetMapping("/usermanagement/getuserdetails/{userid}")
	public ResponseEntity<ResponseMessage> getUserDetails(@PathVariable String userid,@RequestHeader("authorization") String token) {
		System.out.println("inside getuserdetails of UserManagementController");		
		UserLoginDetails userObj=getUserObjFromToken(token);	
		String partnerId= userObj.getPartnerId();	
		System.out.println("Partner Type from Obj = " + userObj.getPartnerType());
		java.util.List <String>roles=userObj.getRoles();
		
		if(roles.contains("businesspartner_manager") || roles.contains("businesspartner_admin") || roles.contains("customer_manager") || roles.contains("customer_admin") || roles.contains("vendor_manager") || roles.contains("vendor_admin") )
		{
		return	userManagementService.getUserDetails(userid);
		}
		else
		{
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.UNAUTHORIZED.getKey(),
					Ex.formatMessage(Ex.UNAUTHORIZED.getKeyMessage()), Ex.UNAUTHORIZED.getKeyMessage() )));
		}
		
		
	}
	
	@GetMapping("/usermanagement/getunassignedroles/{userid}")
	public ResponseEntity<ResponseMessage> getUnassignedRoles(@PathVariable String userid,@RequestHeader("authorization") String token) {
		System.out.println("inside getUnassignedRoles of UserManagementController");		
		UserLoginDetails userObj=getUserObjFromToken(token);	
		String partnerId= userObj.getPartnerId();	
		System.out.println("Partner Type from Obj = " + userObj.getPartnerType());
		java.util.List <String>roles=userObj.getRoles();
		
		if(roles.contains("businesspartner_manager") || roles.contains("businesspartner_admin") || roles.contains("customer_manager") || roles.contains("customer_admin") || roles.contains("vendor_manager") || roles.contains("vendor_admin") )
		{
		return	userManagementService.getUnassignedRoles(userObj, userid);
		}
		else
		{
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.UNAUTHORIZED.getKey(),
					Ex.formatMessage(Ex.UNAUTHORIZED.getKeyMessage()), Ex.UNAUTHORIZED.getKeyMessage() )));
		}
		
		
	}
	
	

	
	
	
	/***
	 * 
	 * @param User
	 * @param token
	
	 * @return
	 */
	@PostMapping("/usermanagement/createuser")

	public ResponseEntity<ResponseMessage> createuser(@RequestBody User newUser,@RequestHeader("authorization") String token) 
		{
		
		System.out.println("inside createuser of UserManagementController");		
		UserLoginDetails adminUserObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + adminUserObj);
		String partnerId= adminUserObj.getPartnerId();	
		System.out.println("Partner Type from Obj = " + adminUserObj.getPartnerType());
		java.util.List <String>roles=adminUserObj.getRoles();
		
		if(!(roles.contains("customer_manager") || roles.contains("customer_admin") || roles.contains("vendor_manager") || roles.contains("vendor_admin")
				|| roles.contains("businesspartner_manager") || roles.contains("businesspartner_admin")) )
		{
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.UNAUTHORIZED.getKey(),
					Ex.formatMessage(Ex.UNAUTHORIZED.getKeyMessage()), Ex.UNAUTHORIZED.getKeyMessage() )));
		}
		
		
		System.out.println("userAlias =*" + newUser.getUserAlias());
		
		try
		{
		if (userManagementService.validateUserAlias(newUser.getUserAlias())>0) {
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Username already exists","Someone already has this username : " + newUser.getUserAlias(),"Someone already has this username : " + newUser.getUserAlias())));
		}
		}
		catch(Exception e)
		{
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("USER CREATION ERROR","USER EXISTS ERROR , Please contact support team",e.getMessage())));				
		}
		
		ResponseEntity<ResponseMessage> response=userManagementService.createUser(newUser,adminUserObj);
		
			return response;		
	}
	
	/***
	 * 
	 * @param PartnerUserRoles
	 * @param token
	
	 * @return
	 */
	@PostMapping("/usermanagement/mapusertorole")

	public ResponseEntity<ResponseMessage> mapusertorole(@RequestBody PartnerUserRoles partnerUserRole,@RequestHeader("authorization") String token) 
		{
		
		try
		{
		System.out.println("inside mapusertorole of UserManagementController");		
		UserLoginDetails adminUserObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + adminUserObj);
		String partnerId= adminUserObj.getPartnerId();	
		System.out.println("Partner Type from Obj = " + adminUserObj.getPartnerType());
		java.util.List <String>roles=adminUserObj.getRoles();
		
		if(!(roles.contains("businesspartner_manager") || roles.contains("businesspartner_admin") || roles.contains("customer_manager") || roles.contains("customer_admin") || roles.contains("vendor_manager") || roles.contains("vendor_admin")) )
		{
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.UNAUTHORIZED.getKey(),
					Ex.formatMessage(Ex.UNAUTHORIZED.getKeyMessage()), Ex.UNAUTHORIZED.getKeyMessage() )));
		}
		
		partnerUserRole.setCreatedBy(adminUserObj.getUserId());
		partnerUserRole.setCreatedDate((new Timestamp(System.currentTimeMillis())));
		
		System.out.println("partnerUserRole userId  =*" + partnerUserRole.getUserId());
		
		return userManagementService.mapusertorole(partnerUserRole);
	
		}
	catch(Exception e)
	{
		return ResponseEntity.ok().body(new ResponseMessage(new APIError("USER ROLE MAPPING ERROR in UserManagementController","USER ROLE MAPPING ERROR , Please contact support team",e.getMessage())));				
	}
		
				
	}
	
	/***
	 * 
	 * @param PartnerUserRoles
	 * @param token
	
	 * @return
	 */
	@PostMapping("/usermanagement/unmapusertorole")

	public ResponseEntity<ResponseMessage> unmapusertorole(@RequestBody PartnerUserRoles partnerUserRole,@RequestHeader("authorization") String token) 
		{
		
		try
		{
		System.out.println("inside unmapusertorole of UserManagementController");		
		UserLoginDetails adminUserObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + adminUserObj);
		String partnerId= adminUserObj.getPartnerId();	
		System.out.println("Partner Type from Obj = " + adminUserObj.getPartnerType());
		java.util.List <String>roles=adminUserObj.getRoles();
		
		if(!(roles.contains("customer_manager") || roles.contains("customer_admin") || roles.contains("vendor_manager") || roles.contains("vendor_admin")) )
		{
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.UNAUTHORIZED.getKey(),
					Ex.formatMessage(Ex.UNAUTHORIZED.getKeyMessage()), Ex.UNAUTHORIZED.getKeyMessage() )));
		}
		
				
		System.out.println("partnerUserRole userId  =*" + partnerUserRole.getUserId());
		System.out.println("partnerUserRole roleId  =*" + partnerUserRole.getRoleId());
		
		return userManagementService.unmapusertorole(partnerUserRole);
	
		}
	catch(Exception e)
	{
		return ResponseEntity.ok().body(new ResponseMessage(new APIError("USER UN MAPPING ROLE ERROR in UserManagementController","USER UNMAPPING ERROR , Please contact support team",e.getMessage())));				
	}
		
				
	}

}
