package com.tecnics.einvoice.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;

import com.tecnics.einvoice.Repo.PartnerRolesRepo;

import com.tecnics.einvoice.entity.ErrorLog;

import com.tecnics.einvoice.entity.PartnerRoles;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;

import com.tecnics.einvoice.util.APIError;


@Component
public class PartnerRolesService  extends BaseService {
	
	@Autowired
	PartnerRolesRepo partnerRolesRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	public ResponseEntity<ResponseMessage> findByPartnerId(String partnerId) {		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage((List<PartnerRoles>) partnerRolesRepo.findByPartnerId(partnerId)));
		
	}
	
	public ResponseEntity<ResponseMessage> findByRoleId(Integer roleId) {	
		
		PartnerRoles partnerRole=null;
		Optional<PartnerRoles>  optionalPartnerRoles=partnerRolesRepo.findByRoleId(roleId);
		if (optionalPartnerRoles!=null && optionalPartnerRoles.isPresent())			
			partnerRole=(PartnerRoles) optionalPartnerRoles.get();		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(partnerRole));
		
	}
	
	public PartnerRoles findByPartnerIdAndRoleName(String partnerId,String roleName) {	
		
		PartnerRoles partnerRole=null;
		Optional<PartnerRoles>  optionalPartnerRoles=partnerRolesRepo.findByPartnerIdAndRoleName(partnerId, roleName);
		if (optionalPartnerRoles!=null && optionalPartnerRoles.isPresent())			
			partnerRole=(PartnerRoles) optionalPartnerRoles.get();		
		return partnerRole;
		
	}
	
	

	
	public ResponseEntity<ResponseMessage> save(PartnerRoles obj,UserLoginDetails userObj) {
		
		System.out.println("Inside save of PartnerRolesService");
		PartnerRoles response = null;	
		try {
			obj.setCreatedBy(userObj.getUserId());
			obj.setPartnerId(userObj.getPartnerId());
			obj.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			response = partnerRolesRepo.save(obj);			
		}
		
		catch(Exception ex){
					
					ErrorLog error = new ErrorLog();
					error.setComponentName("PartnerRolesService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("Save");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
					return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.PARTNER_ROLES_SAVE_ERROR.getKey(),Ex.formatMessage(Ex.PARTNER_ROLES_SAVE_ERROR.getKeyMessage(),userObj.getPartnerId()),Ex.PARTNER_ROLES_SAVE_ERROR.getKeyMessage())));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

}
