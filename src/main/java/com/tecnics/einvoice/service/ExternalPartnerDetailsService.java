
package com.tecnics.einvoice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import com.tecnics.einvoice.Repo.ErrorLogRepo;

import com.tecnics.einvoice.Repo.ExternalPartnerDetailsRepo;


import com.tecnics.einvoice.Repo.ExternalPartnerDetailsRepo.ExternalPartnersListResults;
import com.tecnics.einvoice.constants.Constants;

import com.tecnics.einvoice.entity.ErrorLog;


import com.tecnics.einvoice.entity.ExternalPartnerDetails;

import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.HashingUtil;

@Component
public class ExternalPartnerDetailsService extends BaseService {
	@Autowired
	private Environment env;
	

	@Autowired
	ExternalPartnerDetailsRepo externalPartnerDetailsRepo;
	

	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Autowired
	ErrorLogRepo errorLogRepo;

	public List<ExternalPartnerDetails> findAll() {
		return (List<ExternalPartnerDetails>) externalPartnerDetailsRepo.findAll();
	}

	
	public ResponseEntity<ResponseMessage> getPartners(UserLoginDetails userObj)
	{
		List<ExternalPartnersListResults> externalPartnersListResults=null;
		try
		{
			externalPartnersListResults=externalPartnerDetailsRepo.fetchPartnersList(userObj.getPartnerId());
		}
		
		catch(Exception ex){			
			ErrorLog error = new ErrorLog();
			error.setComponentName("ExternalPartnerDetailsService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("fetchAllPartners");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
	return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch All External Partners List Error", "Error while fetching External Partners List :","fetchAllPartners" )));
			
		}
return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(externalPartnersListResults));
	}

	/**
	 * to save external partner details 
	 * 
	 * @param request
	 * @return
	 */
public ResponseEntity<ResponseMessage> save(ExternalPartnerDetails request,UserLoginDetails userObj) {
	ExternalPartnerDetails response = null  ;
		String partnerId = "";
		try {
			String partnerType = request.getPartnerType();	
			if(request!=null && request.getPartnerId()==null)
			{
			partnerId = generatePartnerId("externalpartner");
			request.setPartnerId(partnerId);
			request.setPartnerTo(userObj.getPartnerId());
			}
			else
			partnerId=request.getPartnerId();
			System.out.println("External partnerID"+partnerId);
			response = externalPartnerDetailsRepo.save(request);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} 	
		catch(Exception ex){			
			ErrorLog error = new ErrorLog();
			error.setComponentName("ExternalPartnerDetailsService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("save");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
	return ResponseEntity.ok().body(new ResponseMessage(new APIError("Save External Partners Details Error", "Error while Saving External Partners Details  :","save" )));
			
		}

		
	}
	

	public String generatePartnerId(String type) {
		String sql = "select einvoicing.generate_partner_id(?) ";
		return jdbcTemplate.queryForObject(sql, new Object[] { type }, String.class);
	}


	
	public ResponseEntity<ResponseMessage> getPartnerDetails(UserLoginDetails uld, String partner_id)
	{
		ExternalPartnerDetails response=null;
	
		System.out.println("Inside getPartnerDetails of ExternalPartnerDetailsService");
			
		try {
			System.out.println("Partner ID to be fetched = " + partner_id);
			
			response=externalPartnerDetailsRepo.findByPartnerId(partner_id);
			System.out.println("company name from external getPartnerDetails = " + response.getCompanyName());
		
		}
		
		catch(Exception ex){
			ex.printStackTrace();
			ErrorLog error = new ErrorLog();
			error.setComponentName("ExternalPartnerDetailsService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("getPartnerDetails");
			error.setSource("Java");
			//error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
		
	}
	
	/**
	 * validategstn - returns true if the gstn as parameter is already exists in the
	 * contacts
	 * 
	 * @param email
	 * @return
	 */
	public int validategstn(String gstin, String partner_to) {
		try {
			String theQuery = "select count(0) from einvoicing.external_partner_details epd where epd.gstin =? and epd.partner_to=?";
			return jdbcTemplate.queryForObject(theQuery, new Object[] { gstin, partner_to }, Integer.class);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
		
	}
	

}

