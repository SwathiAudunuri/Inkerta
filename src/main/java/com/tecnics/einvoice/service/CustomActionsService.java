package com.tecnics.einvoice.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.CustomActionsRepo.CustomActionsResults;
import com.tecnics.einvoice.Repo.CustomActionsRepo;
import com.tecnics.einvoice.Repo.CustomActionsByInvoiceStatusRepo;
import com.tecnics.einvoice.Repo.CustomActionsByPartnerRoleRepo;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.PartnerRoles;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.CustomActionsModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.entity.CustomActions;
import com.tecnics.einvoice.entity.CustomActionsByInvoiceStatus;
import com.tecnics.einvoice.entity.CustomActionsByPartnerRole;

import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.util.APIError;
@Component

public class CustomActionsService extends BaseService {
	
	@Autowired
	CustomActionsRepo customActionsRepo;
	
	@Autowired
	CustomActionsByInvoiceStatusRepo customActionsByInvoiceStatusRepo;
	
	@Autowired
	CustomActionsByPartnerRoleRepo customActionsByPartnerRoleRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	public List<CustomActions> findByPartnerId(String partnerId) {
		return (List<CustomActions>) customActionsRepo.findByPartnerId(partnerId);
	}
	
	public CustomActionsModel findByPartnerIdAndActionId(String partnerId,Integer actionId)
	{
		
		CustomActions customActions=null;
		CustomActionsModel customActionsModel=null;
		Optional<CustomActions>  optionalCustomActions=customActionsRepo.findByPartnerIdAndActionId(partnerId, actionId);
		if (optionalCustomActions!=null && optionalCustomActions.isPresent())
		{
			customActions=optionalCustomActions.get();
			customActionsModel=new CustomActionsModel();
			customActionsModel.setCustomActions(customActions);
			customActionsModel.setCustomActionsByInvoiceStatus(customActionsByInvoiceStatusRepo.findByActionId(customActions.getActionId()));
			customActionsModel.setCustomActionsByPartnerRole(customActionsByPartnerRoleRepo.findByActionId(customActions.getActionId()));			
		}
		
		return customActionsModel;
		
	}
	
	public List<CustomActionsResults> fetchCustomActionsByPartnerId(String partnerId)
	{
		return customActionsRepo.fetchCustomActionsByPartnerId(partnerId);
	}
	
	
	public List<CustomActionsResults> getActionsByInvoiceStatusAndPartnerRoles(UserLoginDetails userObj, String invoice_status, List<String> partnerRoles)
	{
		return customActionsRepo.fetchActionsByInvoicestatusAndPartnerRoles(userObj.getPartnerId(), invoice_status, partnerRoles);
	
	}

	public ResponseEntity<ResponseMessage> fetchPartnerRolesAndInvoicetatuses(UserLoginDetails userObj)
	{
		HashMap<String,List<String>> rolesAndInvoiceStatuses=null;
		try
		{
		List<String> partnerRoles=customActionsRepo.fetchPartnerRoles(userObj.getPartnerId());
		List<String> invoiceStatuses=customActionsRepo.fetchInvoiceStatuses();
		rolesAndInvoiceStatuses=new HashMap<String,List<String>>();
		rolesAndInvoiceStatuses.put("partnerRoles", partnerRoles);
		rolesAndInvoiceStatuses.put("invoiceStatuses", invoiceStatuses);
		}
		catch(Exception ex){
					
					ErrorLog error = new ErrorLog();
					error.setComponentName("CustomActionsService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("Save");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Roles and Invoice Status Fetch Error", "Error while fetch Partner Roles and Invoice Statuses :","fetchPartnerRolesAndInvoicetatuses" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(rolesAndInvoiceStatuses));
		
	
	}
	
	
	public CustomActions findByPartnerIdAndActionName(String partnerId,String actionName) {	
		
		CustomActions customActions=null;
		Optional<CustomActions>  optionalCustomActions=customActionsRepo.findByPartnerIdAndActionName(partnerId, actionName);
		if (optionalCustomActions!=null && optionalCustomActions.isPresent())			
			customActions=(CustomActions) optionalCustomActions.get();		
		return customActions;
		
	}
	


	public ResponseEntity<ResponseMessage> save(CustomActionsModel obj, UserLoginDetails userObj) {
		int failCount =0,successCount=0;
		System.out.println("Inside save of CustomActionsService");
		CustomActionsModel customActionsModel =null;
		try {
			CustomActions customActions=obj.getCustomActions();
			List<CustomActionsByInvoiceStatus> customActionsByInvoiceStatus=obj.getCustomActionsByInvoiceStatus();
			List<CustomActionsByPartnerRole> customActionsByPartnerRole=obj.getCustomActionsByPartnerRole();
			String partnerId=userObj.getPartnerId();
			customActions.setCreatedBy(userObj.getUserId());
			customActions.setPartnerId(userObj.getPartnerId());
			customActions.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			System.out.println("PartnerId from invoice comments save " +partnerId );
			
			customActions = customActionsRepo.save(customActions);
			customActionsModel=new CustomActionsModel();
			customActionsModel.setCustomActions(customActions);
			
			//start
			System.out.println(" Actions by Invoice Status obj " + customActionsByInvoiceStatus);
			System.out.println("Size of Actions by Invoice Status = " + customActionsByInvoiceStatus );
			//Processing and Saving Invoice Status keys
			if(customActionsByInvoiceStatus!=null)
			{
			for (CustomActionsByInvoiceStatus customActionsByInvoiceStatus_val : customActionsByInvoiceStatus) {	            
				customActionsByInvoiceStatus_val.setActionId(customActions.getActionId());          
	        }
			if(customActionsByInvoiceStatus.size()>0)
				customActionsByInvoiceStatus=(List<CustomActionsByInvoiceStatus>)customActionsByInvoiceStatusRepo.saveAll(customActionsByInvoiceStatus);
			customActionsModel.setCustomActionsByInvoiceStatus(customActionsByInvoiceStatus);
			}
			
			//Processing and Saving PartnerRole keys
			if(customActionsByPartnerRole!=null)
			{
			for (CustomActionsByPartnerRole customActionsByPartnerRole_val : customActionsByPartnerRole) {	            
				customActionsByPartnerRole_val.setActionId(customActions.getActionId());          
	        }
			if(customActionsByPartnerRole.size()>0)
				customActionsByPartnerRole=(List<CustomActionsByPartnerRole>)customActionsByPartnerRoleRepo.saveAll(customActionsByPartnerRole);
				customActionsModel.setCustomActionsByPartnerRole(customActionsByPartnerRole);
			}
			
			//end
			
			
		}
		
		catch(Exception ex){
			
			ErrorLog error = new ErrorLog();
			error.setComponentName("CustomActionsService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
	return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.CUSTOM_ACTIONS_SAVE_ERROR.getKey(),Ex.formatMessage(Ex.CUSTOM_ACTIONS_SAVE_ERROR.getKeyMessage(),userObj.getPartnerId()),Ex.CUSTOM_ACTIONS_SAVE_ERROR.getKeyMessage())));
			
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(customActionsModel));
	}
	

}
