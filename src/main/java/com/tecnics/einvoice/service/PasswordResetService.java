package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.AuthenticationRepo;
import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.PasswordResetRepo;
import com.tecnics.einvoice.Repo.UserAuthorizationKeyRepo;
import com.tecnics.einvoice.Repo.UserManagementRepo;
import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.AllReceivableOrPayableResultsByCompany;
import com.tecnics.einvoice.entity.Authorizationskeys;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.PasswordReset;
import com.tecnics.einvoice.entity.PasswordResetRequest;
import com.tecnics.einvoice.entity.UserAuthorizationKey;
import com.tecnics.einvoice.entity.UserManagement;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.HashingUtil;

@Component
public class PasswordResetService extends BaseService{

	public PasswordResetService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private PasswordResetRepo passwordResetRepo;

	@Autowired
	private AuthenticationRepo authenticationRepo;

	@Autowired
	private UserAuthorizationKeyRepo userAuthorizationKeyRepo;

	@Autowired
	private UserManagementRepo managementRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;

	@Autowired
	private HashingUtil hashingUtil;

	public PasswordResetService(PasswordResetRepo passwordResetRepo, AuthenticationRepo authenticationRepo,
			UserManagementRepo managementRepo) {
		this.passwordResetRepo = passwordResetRepo;
		this.authenticationRepo = authenticationRepo;
		this.managementRepo = managementRepo;
	}

	public PasswordReset generatePasswordResetid(String user_id) {
		PasswordReset obj = new PasswordReset();
		try {
			obj.setUser_id(user_id);
			return passwordResetRepo.save(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return passwordResetRepo.save(obj);
	}


	public String updatePassword(PasswordResetRequest request) {
		Authorizationskeys authorizationKeys = authenticationRepo.findByUserId(request.getUser_id());
		UserManagement userManagement = managementRepo.findByUserId(request.getUser_id());

		userManagement.setUser_alias(request.getAliasName());

		authorizationKeys.setAuthorizationKey(hashingUtil.encryptKey(request.getNewPassword()));
		authorizationKeys.setUser_id(request.getUser_id());
		try {
			managementRepo.save(userManagement);
			authenticationRepo.save(authorizationKeys);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		return "Successfully Updated..!";
	}
	
	
	public ResponseEntity<ResponseMessage> changePassword(UserLoginDetails userObj,PasswordResetRequest passwordResetRequest)
	{
	
		try
		{
			if(!passwordResetRequest.getUser_id().equals(userObj.getUserId()))
			{
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("You do not have sufficient privileges to reset user password", "You do not have sufficient privileges to reset user password for the user :" + passwordResetRequest.getAliasName(),"changePassword" )));				
			}
			Authorizationskeys authorizationKeys = authenticationRepo.findByUserId(userObj.getUserId());
			if(!authorizationKeys.getAuthorizationKey().equals(hashingUtil.encryptKey(passwordResetRequest.getOldPassword())))
			{
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("The old password you have entered is incorrect", "The old password you have entered is incorrect and cannot continue to reset your password for :" + userObj.getUserAlias(),"changePassword" )));			
					
			}
			
			if(passwordResetRequest.getNewPassword().length()<8)
			{
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("The password should be a minimum of eight characters in length", "The password should be a minimum of eight characters in length :" + passwordResetRequest.getUser_id(),"changePassword" )));			
			}
			
			authorizationKeys.setAuthorizationKey(hashingUtil.encryptKey(passwordResetRequest.getNewPassword()));
			authorizationKeys=authenticationRepo.save(authorizationKeys);
			if(authorizationKeys!=null)
			{
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Your password has been changed successfully"));					
			}
			else
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("The password is not changed due to internal error", "The password is not reset due to internal error for the user :" + passwordResetRequest.getUser_id(),"changePassword" )));			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("PasswordResetService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("changePassword");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
					return ResponseEntity.ok().body(new ResponseMessage(new APIError("Password Reset Error", "Error while changing your password :","changePassword" )));
					
				}
		
	
	}
	
	
	public ResponseEntity<ResponseMessage> resetPassword(UserLoginDetails userObj,PasswordResetRequest passwordResetRequest)
	{
	
		try
		{
			java.util.List <String>roles=userObj.getRoles();
			if(roles.contains("businesspartner_manager") || roles.contains("businesspartner_admin") || roles.contains("customer_manager") || roles.contains("customer_admin") || roles.contains("vendor_manager") || roles.contains("vendor_admin") )			
			{
				UserManagement userManagement = managementRepo.findByUserId(passwordResetRequest.getUser_id());
				if(userManagement==null)
					return ResponseEntity.ok().body(new ResponseMessage(new APIError("There is no user exists in the system", "There is no user exists in the system with user :" + passwordResetRequest.getAliasName(),"resetPassword" )));				
				
				else if(!userManagement.getPartnerId().equals(userObj.getPartnerId()))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("You do not have sufficient privileges to reset user password", "You do not have sufficient privileges to reset user password for the user :" + passwordResetRequest.getAliasName(),"resetPassword" )));				
				Authorizationskeys authorizationKeys = authenticationRepo.findByUserId(passwordResetRequest.getUser_id());
				if(passwordResetRequest.getNewPassword().length()<8)
				{
					return ResponseEntity.ok().body(new ResponseMessage(new APIError("The password should be a minimum of eight characters in length", "The password should be a minimum of eight characters in length :" + passwordResetRequest.getUser_id(),"resetPassword" )));			
				}
				
				authorizationKeys.setAuthorizationKey(hashingUtil.encryptKey(passwordResetRequest.getNewPassword()));
				authorizationKeys=authenticationRepo.save(authorizationKeys);
				if(authorizationKeys!=null)
				{
					return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("The password has been reset successfully"));					
				}
				else
					return ResponseEntity.ok().body(new ResponseMessage(new APIError("The password is not reset due to internal error", "The password is not reset due to internal error for the user :" + passwordResetRequest.getUser_id(),"resetPassword" )));			
				
				
			
			}
			else
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("You do not have sufficient privileges to reset user password", "You do not have sufficient privileges to reset user password for the user :" + passwordResetRequest.getAliasName(),"resetPassword" )));				
					
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("PasswordResetService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("resetPassword");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
					return ResponseEntity.ok().body(new ResponseMessage(new APIError("Password Reset Error", "Error while changing your password :","resetPassword" )));
					
				}
		
	
	}
	
}
