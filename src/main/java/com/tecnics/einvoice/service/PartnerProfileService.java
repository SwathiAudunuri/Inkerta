package com.tecnics.einvoice.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.PartnerContactDetailsRepo;
import com.tecnics.einvoice.Repo.PartnerDetailsRepo;
import com.tecnics.einvoice.Repo.PartnerGSTINDetailsRepo;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.PartnerContactDetails;
import com.tecnics.einvoice.entity.PartnerDetails;
import com.tecnics.einvoice.entity.PartnerGSTINDetails;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.PartnerProfileModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;

@Component
public class PartnerProfileService extends BaseService {
	
	@Autowired
	PartnerGSTINDetailsRepo partnerGSTINDetailsRepo;
	
	@Autowired
	PartnerContactDetailsRepo partnerContactDetailsRepo;
	
	
	@Autowired
	PartnerDetailsService partnerDetailsService;
	
	@Autowired
	PartnerGSTINDetailsService partnerGSTINDetailsService;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	@Autowired
	PartnerDetailsRepo partnerDetailRepo;
	
	public List<PartnerDetails> findPartnerDetailsByPartnerId(String partnerId) {
		return (List<PartnerDetails>) partnerDetailRepo.findByPartnerId(partnerId);
	}
	
	public List<PartnerContactDetails> findContactDetailsByPartnerId(String partnerId) {
		return (List<PartnerContactDetails>) partnerContactDetailsRepo.findByPartnerId(partnerId);
	}
	
	public List<PartnerContactDetails> saveContactDetails(List<PartnerContactDetails> obj, UserLoginDetails userObj) {
		
		System.out.println("Inside save of PartnerContactDetailsService");	
		List<PartnerContactDetails> savedPartnerContactDetails=new ArrayList<PartnerContactDetails>();
		try {
			
			for(PartnerContactDetails partnerContactDetails : obj) {
				partnerContactDetails.setPartnerId(userObj.getPartnerId());
				partnerContactDetails=partnerContactDetailsRepo.save(partnerContactDetails);
				savedPartnerContactDetails.add(partnerContactDetails);
				}
				
			return savedPartnerContactDetails;
	
			
		}
		
		catch(Exception ex){
			
			ErrorLog error = new ErrorLog();
			error.setComponentName("PartnerProfileService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);

		}
		return savedPartnerContactDetails;
	
	}
	
public PartnerDetails savePartnerDetails(PartnerDetails obj, UserLoginDetails userObj) {
		
	PartnerDetails partnerDetails=null;
		System.out.println("Inside save of PartnerContactDetailsService");	
		try {
			
			
			partnerDetails = partnerDetailRepo.save(obj);
			
			if(partnerDetails!=null)
			{
				return partnerDetails;
			}		
				
			
		}
		
		catch(Exception ex){
			
			ErrorLog error = new ErrorLog();
			error.setComponentName("PartnerProfileService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
	
			}
		return partnerDetails;
	
	}
	
public List<PartnerGSTINDetails> findGSTINDetailsByPartnerId(String partnerId) {
		
		List<PartnerGSTINDetails>  partnerGSTINDetails=partnerGSTINDetailsRepo.findByPartnerId(partnerId);
		return partnerGSTINDetails;
	}
	
	
	
public ResponseEntity<ResponseMessage> savePartnerGSTINDetails(PartnerGSTINDetails obj, UserLoginDetails userObj) {
		
		System.out.println("Inside save of PartnerContactDetailsService");		
		try {
			
			List<PartnerDetails> partnerDetails=findPartnerDetailsByPartnerId(userObj.getPartnerId());
			if(partnerDetails.get(0).getPanNo().equals(obj.getPanno()))	
			obj = partnerGSTINDetailsService.save(obj);
			else
			{
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Error Validating PAN NO", "PAN number mismatch with the onboarded PAN. The PAN number of the GSTIN " + obj.getGstin() + " has to match with the onboarded PAN " +partnerDetails.get(0).getPanNo() )));
			}
			
			if(obj!=null)
			{
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(obj));
			}
			else
				return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.PARTNER_GSTIN_DETAILS_SAVE_ERROR.getKey(),Ex.formatMessage(Ex.PARTNER_GSTIN_DETAILS_SAVE_ERROR.getKeyMessage(),userObj.getPartnerId()),Ex.PARTNER_GSTIN_DETAILS_SAVE_ERROR.getKeyMessage())));	
			
			//end
			
			
		}
		
		catch(Exception ex){
			
			ErrorLog error = new ErrorLog();
			error.setComponentName("PartnerProfileService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
	return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.PARTNER_CONTACT_DETAILS_SAVE_ERROR.getKey(),Ex.formatMessage(Ex.PARTNER_CONTACT_DETAILS_SAVE_ERROR.getKeyMessage(),userObj.getPartnerId()),Ex.PARTNER_CONTACT_DETAILS_SAVE_ERROR.getKeyMessage())));
			
		}
	
	}

public PartnerProfileModel getProfileDetails(UserLoginDetails uld)
{
	PartnerProfileModel response=null;
	PartnerDetails partnerDetails=null;
	List<PartnerContactDetails> partnerContactDetails=null;
	List<PartnerGSTINDetails> partnerGSTINDetails=null;
	System.out.println("Inside getProfileDetails of PartnerProfileService");
		
	try {
		System.out.println("Partner ID = " + uld.getPartnerId());
		
		partnerDetails=findPartnerDetailsByPartnerId(uld.getPartnerId()).get(0);
		System.out.println("company name  = " + partnerDetails.getCompanyName());
		partnerContactDetails=findContactDetailsByPartnerId(uld.getPartnerId());
		partnerGSTINDetails=findGSTINDetailsByPartnerId(uld.getPartnerId());
		response=new PartnerProfileModel();
		response.setPartnerDetails(partnerDetails);
		response.setPartnerContactDetails(partnerContactDetails);
		response.setPartnerGSTINDetails(partnerGSTINDetails);		
	}
	
	catch(Exception ex){
		ex.printStackTrace();
		ErrorLog error = new ErrorLog();
		error.setComponentName("PartnerProfileService");
		error.setError(ex.getMessage());
		error.setErrorMessage(getStackTrace(ex));
		error.setModule("Save");
		error.setSource("Java");
		//error.setUserId(userObj.getUserId());
		errorLogRepo.save(error);
	}
	
	return response;
	
}
	

	public PartnerProfileModel savePartnerProfileDetails(PartnerProfileModel obj, UserLoginDetails userObj) {
		
		PartnerProfileModel response=null;
		PartnerDetails partnerDetails=null;
		List<PartnerContactDetails> partnerContactDetails=null;
		System.out.println("Inside save of PartnerGSTINDetailsService");
			
		try {
			partnerDetails=obj.getPartnerDetails();
			partnerContactDetails=obj.getPartnerContactDetails();
			
			partnerDetails=savePartnerDetails(partnerDetails, userObj);
			partnerContactDetails=saveContactDetails(partnerContactDetails, userObj);
			response=new PartnerProfileModel();
			response.setPartnerDetails(partnerDetails);
			response.setPartnerContactDetails(partnerContactDetails);
		}
		
		catch(Exception ex){
			ex.printStackTrace();
			ErrorLog error = new ErrorLog();
			error.setComponentName("PartnerProfileService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			//error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
		}
		
		return response;
	}

}
