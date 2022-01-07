package com.tecnics.einvoice.controller;

import java.sql.Timestamp;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.client.RestTemplate;




import com.tecnics.einvoice.entity.PartnerGSTINDetails;

import com.tecnics.einvoice.entity.ExternalPartnerDetails;
import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.ExternalPartnerDetailsService;
import com.tecnics.einvoice.service.PartnerGSTINFetchService;

import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class ExternalPartnerDetailsController extends BaseController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
	ExternalPartnerDetailsService externalPartnerDetailsService;
	

	@Autowired
	PartnerGSTINFetchService fetchService;
	
	
	@GetMapping("/externalpartner/createpartner/{gstin}")
	public ResponseEntity<ResponseMessage> createPartner(@PathVariable String gstin,@RequestHeader("authorization") String token) {
		
		
		PartnerGSTINDetails partnerGSTINDetails=null;
		ExternalPartnerDetails externalPartnerDetails=null;
		UserLoginDetails userObj=getUserObjFromToken(token);
		String uri = env.getProperty("gstn.api.uri") + gstin+env.getProperty("gstn.api.key");
	
		try {
		int count = externalPartnerDetailsService.validategstn(gstin,userObj.getPartnerId());
		if (count > 0) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("Duplicate GSTIN found", "Company is already a member of Partners list")));
		}

	} catch (Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseMessage(new APIError("Validate GSTN Error", e.getMessage())));
	}
		
		
		try {
		
			ResponseEntity<String> resp = restTemplate.getForEntity(uri, String.class);
			System.out.println("Response from appyflow =" + resp);

			
			JSONObject json_gstin_response_body = new JSONObject(new String(resp.getBody()));
			
			if(json_gstin_response_body.has("error"))
			{
			boolean isError=json_gstin_response_body.getBoolean("error");			
			if(isError)
			{
			String errorMessage = json_gstin_response_body.getString("message");		; 
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("GSTIN Verification Error", errorMessage)));
			}
			}
			
			
			
			partnerGSTINDetails = fetchService.handleJsonJoltToPartnerGSTINDetails(new String(resp.getBody()));
			externalPartnerDetails=new ExternalPartnerDetails();
			externalPartnerDetails.setCompanyName(partnerGSTINDetails.getLgnm());
			
			externalPartnerDetails.setFirmType(partnerGSTINDetails.getCtb());
			externalPartnerDetails.setGstin(partnerGSTINDetails.getGstin());
			externalPartnerDetails.setNatureOfBusiness(partnerGSTINDetails.getNba());
			externalPartnerDetails.setPanNo(partnerGSTINDetails.getPanno());
			externalPartnerDetails.setPartnerAddress(partnerGSTINDetails.getBnm() + ", " + partnerGSTINDetails.getBno() + ", " + partnerGSTINDetails.getCity() + ", " +partnerGSTINDetails.getSt() + ", " +partnerGSTINDetails.getLoc() + ", " +partnerGSTINDetails.getPncd());
			externalPartnerDetails.setCountry("India");
			
		  if (partnerGSTINDetails == null) {			  
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("Error Validating", "GSTN information not found")));
		}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("GSTIN Verification Error", e.getMessage())));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(externalPartnerDetails));

	}
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/externalpartner/getpartnerdetails/{partnerId}")
	public ResponseEntity<ResponseMessage> getPartnerDetails(@PathVariable String partnerId,@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			ResponseEntity<ResponseMessage> response;
			System.out.println("Inside externalpartner details Controller getpartnerdetails ");
			
			response = externalPartnerDetailsService.getPartnerDetails(userObj, partnerId);
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("Fetch External Partner Details getpartnerdetails Error", e.getMessage())));
		}
	}
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/externalpartner/getpartners")
	public ResponseEntity<ResponseMessage> getPartners(@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			ResponseEntity<ResponseMessage> response;
			System.out.println("Inside externalpartner details Controller fetchpartners ");
			
			response = externalPartnerDetailsService.getPartners(userObj);
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("Fetch External Partner Details fetchpartners Error", e.getMessage())));
		}
	}
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@PostMapping("/externalpartner/savepartner")
	public ResponseEntity<ResponseMessage> saveProfileDetails(@RequestBody ExternalPartnerDetails externalPartnerDetails, @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			 ResponseEntity<ResponseMessage> response;
			System.out.println("Inside External Partner  savepartner");
			
			response = externalPartnerDetailsService.save(externalPartnerDetails, userObj);
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("savepartner  Error", e.getMessage())));
		}
	}

}

