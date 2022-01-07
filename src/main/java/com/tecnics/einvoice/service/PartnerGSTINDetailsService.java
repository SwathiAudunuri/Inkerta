package com.tecnics.einvoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.PartnerGSTINDetailsRepo;

import com.tecnics.einvoice.Repo.ErrorLogRepo;

import com.tecnics.einvoice.entity.PartnerGSTINDetails;
import com.tecnics.einvoice.entity.ErrorLog;


@Component
public class PartnerGSTINDetailsService extends BaseService {
	
	@Autowired
	PartnerGSTINDetailsRepo partnerGSTINDetailsRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
public PartnerGSTINDetails findById(String gstin) {
		
		Optional<PartnerGSTINDetails>  partnerGSTINDetails=partnerGSTINDetailsRepo.findById(gstin);
		if (partnerGSTINDetails!=null && partnerGSTINDetails.isPresent())			
		    return partnerGSTINDetails.get();		
		else		
			return null;
		
	}

public PartnerGSTINDetails findByGstinAndPartnerId(String gstin,String partnerId) {
	
	Optional<PartnerGSTINDetails>  partnerGSTINDetails=partnerGSTINDetailsRepo.findByGstinAndPartnerId(gstin,partnerId);
	if (partnerGSTINDetails!=null && partnerGSTINDetails.isPresent())			
	    return partnerGSTINDetails.get();		
	else		
		return null;
	
}
	
	
	public PartnerGSTINDetails update(PartnerGSTINDetails obj) {
		return partnerGSTINDetailsRepo.save(obj);		
	}
	
	public PartnerGSTINDetails save(PartnerGSTINDetails obj) {
		
		PartnerGSTINDetails response=null;
		System.out.println("Inside save of PartnerGSTINDetailsService");
			
		try {
			response = partnerGSTINDetailsRepo.save(obj);							
		}
		
		catch(Exception ex){
			
			ErrorLog error = new ErrorLog();
			error.setComponentName("PartnerGSTINDetailsService");
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
