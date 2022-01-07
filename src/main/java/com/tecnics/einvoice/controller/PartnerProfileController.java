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



import com.tecnics.einvoice.entity.OnboardingRegistrationDetails;
import com.tecnics.einvoice.entity.PartnerGSTINDetails;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.PartnerProfileModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.PartnerDetailsService;
import com.tecnics.einvoice.service.PartnerGSTINFetchService;
import com.tecnics.einvoice.service.PartnerProfileService;
import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class PartnerProfileController extends BaseController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
	PartnerDetailsService partnerDetailService;
	
	@Autowired
	PartnerProfileService partnerProfileService;
	
	@Autowired
	PartnerGSTINFetchService fetchService;
	
	
	@GetMapping("/partnerprofile/addgstin/{gstin}")
	public ResponseEntity<ResponseMessage> addGSTIN(@PathVariable String gstin,@RequestHeader("authorization") String token) {
		
		ResponseEntity<ResponseMessage> response=null;
		
		PartnerGSTINDetails partnerGSTINDetails=null;
		UserLoginDetails userObj=getUserObjFromToken(token);
		String uri = env.getProperty("gstn.api.uri") + gstin+env.getProperty("gstn.api.key");
	
		try {
		int count = partnerDetailService.validategstn(gstin);
		if (count > 0) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("Duplicate GSTN found", "GSTN already a member of Inkreta")));
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
			partnerGSTINDetails.setPartnerId(userObj.getPartnerId());
			response=partnerProfileService.savePartnerGSTINDetails(partnerGSTINDetails,userObj);
			System.out.println("appyflow response saved =" + partnerGSTINDetails);
			
		  if (response == null) {			  
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("Error Validating", "GSTN information not found")));
		}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("GSTIN Verification Error", e.getMessage())));
		}
		return response;

	}
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/partnerprofile/getprofiledetails")
	public ResponseEntity<ResponseMessage> getProfileDetails(@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside PartnerProfileController getprofiledetails");
			
			PartnerProfileModel response = partnerProfileService.getProfileDetails(userObj);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@PostMapping("/partnerprofile/saveprofiledetails")
	public ResponseEntity<ResponseMessage> saveProfileDetails(@RequestBody PartnerProfileModel partnerProfileModel, @RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside PartnerProfileController saveprofiledetails");
			
			PartnerProfileModel response = partnerProfileService.savePartnerProfileDetails(partnerProfileModel, userObj);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}

}
