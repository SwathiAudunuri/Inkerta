package com.tecnics.einvoice.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.PartnerContactDetailsRepo;

import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.PartnerContactDetails;
import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;

@Component
public class PartnerContactDetailsService  extends BaseService {
	
	@Autowired
	PartnerContactDetailsRepo partnerContactDetailsRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	public List<PartnerContactDetails> findByPartnerId(String partnerId) {
		return (List<PartnerContactDetails>) partnerContactDetailsRepo.findByPartnerId(partnerId);
	}
	
	public ResponseEntity<ResponseMessage> save(PartnerContactDetails obj, UserLoginDetails userObj) {
		
		System.out.println("Inside save of PartnerContactDetailsService");		
		try {
			
			
			obj = partnerContactDetailsRepo.save(obj);
			
			if(obj!=null)
			{
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(obj));
			}
			else
				return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.PARTNER_CONTACT_DETAILS_SAVE_ERROR.getKey(),Ex.formatMessage(Ex.PARTNER_CONTACT_DETAILS_SAVE_ERROR.getKeyMessage(),userObj.getPartnerId()),Ex.PARTNER_CONTACT_DETAILS_SAVE_ERROR.getKeyMessage())));	
			
			//end
			
			
		}
		
		catch(Exception ex){
			
			ErrorLog error = new ErrorLog();
			error.setComponentName("PartnerContactDetailsService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
	return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.PARTNER_CONTACT_DETAILS_SAVE_ERROR.getKey(),Ex.formatMessage(Ex.PARTNER_CONTACT_DETAILS_SAVE_ERROR.getKeyMessage(),userObj.getPartnerId()),Ex.PARTNER_CONTACT_DETAILS_SAVE_ERROR.getKeyMessage())));
			
		}
		
		
	}

}
