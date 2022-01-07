package com.tecnics.einvoice.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
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


import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.entity.ErrorLog;

import com.tecnics.einvoice.entity.PartnerRoles;
import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.PartnerRolesService;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class PartnerRolesController extends BaseController {
	
	@Autowired
	PartnerRolesService partnerRolesService;
	
	@RequestMapping("/partnerroles/verifyPartnerRolesController")  
    public String verify()  
    {  
        return "The PartnerRolesController is up and running";  
    } 
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/partnerroles/getroles")
	public ResponseEntity<ResponseMessage> getRoles(@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside getRoles");
			
			String partnerId=userObj.getPartnerId();
			System.out.println("PartnerId from getRoles " +partnerId );
			ResponseEntity<ResponseMessage> response = partnerRolesService.findByPartnerId(partnerId);
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.PARTNER_ROLES_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.PARTNER_ROLES_FETCH_ERROR.getKeyMessage(),"getroles()"), getStackTrace(e))));
		}

	}
	
	/***
	 * 
	 * @param actionId
	 * @param token
	 * @return
	 */
	@GetMapping("/partnerroles/roledetails/{roleId}")
	public ResponseEntity<ResponseMessage> getRoleDetails(@PathVariable Integer roleId,
			@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside PartnerRolesController roledetails");
			
			return partnerRolesService.findByRoleId(roleId);

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKeyMessage(),"actiondetails()"), getStackTrace(e))));
		}
	}
	
	
	@PostMapping("/partnerroles/createrole")
	public ResponseEntity<ResponseMessage> createRole(@RequestBody PartnerRoles partnerRoles,@RequestHeader("authorization") String token) 
	{
	UserLoginDetails userObj=getUserObjFromToken(token);
	System.out.println("Inside PartnerRolesController createRole");	

	System.out.println("Partner ID from Obj = " + userObj.getPartnerId());	
	String partnerId= userObj.getPartnerId();			
		
		//check if same role exists with the same role name in the system
		PartnerRoles existingPartnerRole=null;			
			try {
				existingPartnerRole=partnerRolesService.findByPartnerIdAndRoleName(partnerId,partnerRoles.getRoleName());
				
				if (existingPartnerRole!=null) 
					return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Duplicate Role", "The record exists with the same role name :",partnerRoles.getRoleName() )));
				else
				{					
					return partnerRolesService.save(partnerRoles, userObj);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("onboarding saveDetails Error", e.getMessage())));
			}
				
		
	}

}
