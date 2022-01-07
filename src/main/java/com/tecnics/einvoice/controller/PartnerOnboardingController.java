package com.tecnics.einvoice.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.CaptchaUtil;
import com.tecnics.einvoice.model.PartnerGSTINResponseModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.SignupResponse;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.CaptchaOTPKeysService;
import com.tecnics.einvoice.service.OnboardingRegistrationDetailsService;
import com.tecnics.einvoice.service.PartnerDetailsService;
import com.tecnics.einvoice.service.PartnerGSTINFetchService;
import com.tecnics.einvoice.entity.CaptchaOTPKeys;
import com.tecnics.einvoice.entity.OnboardingRegistrationDetails;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PartnerOnboardingController extends BaseController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	PartnerDetailsService partnerDetailService;
	
	@Autowired
	OnboardingRegistrationDetailsService onboardingRegistrationDetailsService;

	@Autowired
	private Environment env;

	@Autowired
	PartnerGSTINFetchService fetchService;
	
	@Autowired
	CaptchaOTPKeysService captchaOTPKeysService;

	@GetMapping("onboarding/verifycaptcha/{captchakey}")
	public ResponseEntity<ResponseMessage> verifyCaptcha(@PathVariable Integer captchakey) {
		
		CaptchaOTPKeys captchaKeyObj=null;
		
		
		try {
			List<CaptchaOTPKeys> captchaKeys=captchaOTPKeysService.findByCaptchaKey(captchakey);
			Integer captchaKey=CaptchaUtil.generateCaptchaKey();
			if(captchaKeys.size()>0)
			{
				captchaKeyObj=(CaptchaOTPKeys)captchaKeys.get(0);
				
				captchaKeyObj.setStatus("Verified");							
				captchaKeyObj.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
				captchaKeyObj=captchaOTPKeysService.update(captchaKeyObj);		
			}
			else
			{
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Invalid CaptchaKey", "Please re-enter the Captcha Key")));
			}
		}
		
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("CaptchaKey Generation Error", e.getMessage())));
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(captchaKeyObj));
			

	}	
	
	
	@GetMapping("onboarding/generatecaptchaforgstin")
	public ResponseEntity<ResponseMessage> generateCaptcha() {
		
		CaptchaOTPKeys captchaKeys=null;
		
		
		try {
			Integer captchaKey=CaptchaUtil.generateCaptchaKey();
			if(captchaKey!=null)
			{
				captchaKeys=new CaptchaOTPKeys();
				captchaKeys.setCaptchaOTPKey(captchaKey);
				captchaKeys.setStatus("Generated");
				captchaKeys.setKeyType("GSTIN");
				captchaKeys.setModuleName("Onboarding");				
				captchaKeys.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				captchaKeys=captchaOTPKeysService.save(captchaKeys);				
			}
		}
		
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("CaptchaKey Generation Error", e.getMessage())));
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(captchaKeys));
			

	}
	
	@GetMapping("onboarding/generateotpforemail/{emailid}")
	public ResponseEntity<ResponseMessage> generateOTPForEmail(@PathVariable String emailid) {
		
		CaptchaOTPKeys OTPKeyObj=null;		
		
		try {
			Integer OTPKey=CaptchaUtil.generateCaptchaKey();
			if(OTPKey!=null)
			{
				OTPKeyObj=new CaptchaOTPKeys();
				OTPKeyObj.setCaptchaOTPKey(OTPKey);
				OTPKeyObj.setStatus("Generated");
				OTPKeyObj.setKeyType("Email");
				OTPKeyObj.setKeyValue(emailid);
				OTPKeyObj.setModuleName("Onboarding");				
				OTPKeyObj.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				OTPKeyObj=captchaOTPKeysService.save(OTPKeyObj);
				onboardingRegistrationDetailsService.sendOTPKeyHtmlMail(emailid,OTPKey);
			}
		}
		
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("OTPKey Generation for EMail Error", e.getMessage())));
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(OTPKeyObj));
			

	}
	
	@GetMapping("onboarding/generateotpformobile/{mobilenumber}/{gstin}")
	public ResponseEntity<ResponseMessage> generateOTPForMobile(@PathVariable String mobilenumber,@PathVariable String gstin) {
		
		CaptchaOTPKeys OTPKeyObj=null;	
		
		//check if Inprogress GSTIN record exists in the system
				OnboardingRegistrationDetails inProgressOnboardingRegistrationDetails=null;
					
					try {
						inProgressOnboardingRegistrationDetails=onboardingRegistrationDetailsService.findByGstinAndOnboardingtatus(gstin, "In Progress");
						
						if (inProgressOnboardingRegistrationDetails==null) 
							return ResponseEntity.status(HttpStatus.OK)
								.body(new ResponseMessage(new APIError("Validate GSTN Error", "No record exists in the system with GSTIN:" +gstin )));

					} catch (Exception e) {
						e.printStackTrace();
						return ResponseEntity.status(HttpStatus.OK)
								.body(new ResponseMessage(new APIError("Verify OTP For Email Error", e.getMessage())));
					}
		
		try {
			Integer OTPKey=CaptchaUtil.generateCaptchaKey();
			if(OTPKey!=null)
			{
				OTPKeyObj=new CaptchaOTPKeys();
				OTPKeyObj.setCaptchaOTPKey(OTPKey);
				OTPKeyObj.setStatus("Generated");
				OTPKeyObj.setKeyType("Mobile");
				OTPKeyObj.setKeyValue(mobilenumber);
				OTPKeyObj.setModuleName("Onboarding");				
				OTPKeyObj.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				OTPKeyObj=captchaOTPKeysService.save(OTPKeyObj);
				onboardingRegistrationDetailsService.sendOTPKeySMS(inProgressOnboardingRegistrationDetails.getEmail(),OTPKey);
			}
		}
		
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("OTPKey Generation for EMail Error", e.getMessage())));
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(OTPKeyObj));
			

	}

	@GetMapping("onboarding/verifycaptchaforgstin/{captchakey}/{gstin}")
	public ResponseEntity<ResponseMessage> verifyGstin(@PathVariable Integer captchakey,@PathVariable String gstin) {
		String uri = env.getProperty("gstn.api.uri") + gstin+env.getProperty("gstn.api.key");
		CaptchaOTPKeys captchaKeyObj=null;
		
		try {
			List<CaptchaOTPKeys> captchaKeys=captchaOTPKeysService.findByCaptchaOTPKeyAndKeyTypeAndStatus(captchakey,"GSTIN","Generated");
			Integer captchaKey=CaptchaUtil.generateCaptchaKey();
			if(captchaKeys.size()>0)
			{
				captchaKeyObj=(CaptchaOTPKeys)captchaKeys.get(0);
				
				captchaKeyObj.setStatus("Verified");
				captchaKeyObj.setKeyValue(gstin);
				captchaKeyObj.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
				captchaKeyObj=captchaOTPKeysService.update(captchaKeyObj);		
			}
			else
			{
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Invalid CaptchaKey", "Please re-enter the Captcha Key")));
			}
		}
		
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("CaptchaKey Generation Error", e.getMessage())));
				}
		
		
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
		//start
		
		try {
			OnboardingRegistrationDetails onboardingRegistrationDetails=
					onboardingRegistrationDetailsService.findByGstinAndOnboardingtatus(gstin, "In Progress");
			
			if (onboardingRegistrationDetails!=null) {
				System.out.println("In progress onboarding details");
				System.out.println("gstin: " +onboardingRegistrationDetails.getGstin()  + " status :" + onboardingRegistrationDetails.getOnboardingstatus() );
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(onboardingRegistrationDetails));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("Validate GSTN Error", e.getMessage())));
		}
		
		//end
		OnboardingRegistrationDetails response =null;
		
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
			
			response = fetchService.handleJsonJoltToOnboardingRegistrationDetails(new String(resp.getBody()));
			System.out.println("Nature of Business = " + response.getNatureofbusiness());
			response.setInitiateddate(new Timestamp(System.currentTimeMillis()));
			response.setLastupdateon(new Timestamp(System.currentTimeMillis()));
			response=onboardingRegistrationDetailsService.save(response);
			System.out.println("appyflow response saved =" + response);
		
		  if (response == null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("Error Validating", "GSTN information not found")));
		}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage(new APIError("GSTIN Verification Error", e.getMessage())));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

	}
	
	@PostMapping("onboarding/verifyotpforemail/{otpkey}/{emailid}/{gstin}")
	public ResponseEntity<ResponseMessage> verifyOTPForEmail(@PathVariable Integer otpkey,@PathVariable String emailid,@PathVariable String gstin,@RequestBody OnboardingRegistrationDetails onboardingRegistrationDetails) {
		//OTP Verifiction

		//check if Inprogress GSTIN record exists in the system
		OnboardingRegistrationDetails inProgressOnboardingRegistrationDetails=null;
			
			try {
				inProgressOnboardingRegistrationDetails=onboardingRegistrationDetailsService.findByGstinAndOnboardingtatus(gstin, "In Progress");
				
				if (inProgressOnboardingRegistrationDetails==null) 
					return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Validate GSTN Error", "No record exists in the system with GSTIN:" +gstin )));
				
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Verify OTP For Email Error", e.getMessage())));
			}
			

			
			
			// Check if OTP entered is correct
			
			
			CaptchaOTPKeys OTPKeyObj=null;
		
		
		try {
			List<CaptchaOTPKeys> OTPKeysObj=captchaOTPKeysService.findByCaptchaOTPKeyAndKeyTypeAndStatus(otpkey,"Email","Generated");
			Integer captchaKey=CaptchaUtil.generateCaptchaKey();
			if(OTPKeysObj.size()>0)
			{
				OTPKeyObj=(CaptchaOTPKeys)OTPKeysObj.get(0);				
				OTPKeyObj.setStatus("Verified");
				OTPKeyObj.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
				OTPKeyObj=captchaOTPKeysService.update(OTPKeyObj);
				inProgressOnboardingRegistrationDetails.setEmail(emailid);
				inProgressOnboardingRegistrationDetails.setVerifiedmail(true);
				inProgressOnboardingRegistrationDetails.setMobilenumber(onboardingRegistrationDetails.getMobilenumber());
				inProgressOnboardingRegistrationDetails.setVerifiedphone(onboardingRegistrationDetails.getVerifiedphone());
				if(inProgressOnboardingRegistrationDetails.getVerifiedphone()!=null && inProgressOnboardingRegistrationDetails.getVerifiedphone())
					inProgressOnboardingRegistrationDetails.setCanenablecontactnext(true);
				inProgressOnboardingRegistrationDetails.setLastupdateon(new Timestamp(System.currentTimeMillis()));
				inProgressOnboardingRegistrationDetails.setFirstname(onboardingRegistrationDetails.getFirstname());
				inProgressOnboardingRegistrationDetails.setLastname(onboardingRegistrationDetails.getLastname());
				
				inProgressOnboardingRegistrationDetails=onboardingRegistrationDetailsService.update(inProgressOnboardingRegistrationDetails);
				System.out.println("onboardingRegistrationDetails response updated in verifyEmail =" + inProgressOnboardingRegistrationDetails);
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(inProgressOnboardingRegistrationDetails));
			}
			else
			{
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Invalid OTP Key", "Please re-enter the OTP Key")));
			}
			
		}
		
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("CaptchaKey Generation Error", e.getMessage())));
				}
		
		//End of OTP Verification
		
		
	}
	
	@PostMapping("onboarding/verifyotpformobile/{otpkey}/{mobilenumber}/{gstin}")
	public ResponseEntity<ResponseMessage> verifyOTPForMobile(@PathVariable Integer otpkey,@PathVariable String mobilenumber,@PathVariable String gstin,@RequestBody OnboardingRegistrationDetails onboardingRegistrationDetails) {
		//OTP Verifiction

		//check if Inprogress GSTIN record exists in the system
		OnboardingRegistrationDetails inProgressOnboardingRegistrationDetails=null;
			
			try {
				inProgressOnboardingRegistrationDetails=onboardingRegistrationDetailsService.findByGstinAndOnboardingtatus(gstin, "In Progress");
				
				if (inProgressOnboardingRegistrationDetails==null) 
					return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Validate GSTN Error", "No record exists in the system with GSTIN:" +gstin )));

			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Verify OTP For Email Error", e.getMessage())));
			}
			

			
			
			// Check if OTP entered is correct
			
			
			CaptchaOTPKeys OTPKeyObj=null;
		
		
		try {
			List<CaptchaOTPKeys> OTPKeysObj=captchaOTPKeysService.findByCaptchaOTPKeyAndKeyTypeAndStatus(otpkey,"Mobile","Generated");
			Integer captchaKey=CaptchaUtil.generateCaptchaKey();
			if(OTPKeysObj.size()>0)
			{
				OTPKeyObj=(CaptchaOTPKeys)OTPKeysObj.get(0);				
				OTPKeyObj.setStatus("Verified");
				OTPKeyObj.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
				OTPKeyObj=captchaOTPKeysService.update(OTPKeyObj);
				inProgressOnboardingRegistrationDetails.setMobilenumber(mobilenumber);
				inProgressOnboardingRegistrationDetails.setVerifiedphone(true);
				inProgressOnboardingRegistrationDetails.setEmail(onboardingRegistrationDetails.getEmail());
				inProgressOnboardingRegistrationDetails.setVerifiedmail(onboardingRegistrationDetails.getVerifiedmail());
				if(inProgressOnboardingRegistrationDetails.getVerifiedmail()!=null && inProgressOnboardingRegistrationDetails.getVerifiedmail())
					inProgressOnboardingRegistrationDetails.setCanenablecontactnext(true);
				inProgressOnboardingRegistrationDetails.setLastupdateon(new Timestamp(System.currentTimeMillis()));
				inProgressOnboardingRegistrationDetails.setFirstname(onboardingRegistrationDetails.getFirstname());
				inProgressOnboardingRegistrationDetails.setLastname(onboardingRegistrationDetails.getLastname());
				inProgressOnboardingRegistrationDetails=onboardingRegistrationDetailsService.update(inProgressOnboardingRegistrationDetails);
				System.out.println("onboardingRegistrationDetails response updated in verifyMobile =" + inProgressOnboardingRegistrationDetails);
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(inProgressOnboardingRegistrationDetails));
			}
			else
			{
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Invalid OTP Key", "Please re-enter the OTP Key")));
			}
			
		}
		
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("CaptchaKey Generation Error", e.getMessage())));
				}
		
		//End of OTP Verification
		
		
	}
	
	
	@PostMapping("onboarding/savedetails/{gstin}")
	public ResponseEntity<ResponseMessage> saveDetails(@PathVariable String gstin,@RequestBody OnboardingRegistrationDetails onboardingRegistrationDetails) {
		//OTP Verifiction

		//check if Inprogress GSTIN record exists in the system
		OnboardingRegistrationDetails inProgressOnboardingRegistrationDetails=null;
			
			try {
				inProgressOnboardingRegistrationDetails=onboardingRegistrationDetailsService.findByGstinAndOnboardingtatus(gstin, "In Progress");
				
				if (inProgressOnboardingRegistrationDetails==null) 
					return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Validate GSTN Error", "No record exists in the system with GSTIN:" +gstin )));
				else
				{
					onboardingRegistrationDetails.setLastupdateon(new Timestamp(System.currentTimeMillis()));
					onboardingRegistrationDetails=onboardingRegistrationDetailsService.update(onboardingRegistrationDetails);
					System.out.println("onboardingRegistrationDetails response updated in verifyMobile =" + onboardingRegistrationDetails);
					return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(onboardingRegistrationDetails));
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("onboarding saveDetails Error", e.getMessage())));
			}
				
		
	}
	
	
	@PostMapping("onboarding/submit/{gstin}")
	public ResponseEntity<ResponseMessage> submit(@PathVariable String gstin,@RequestBody OnboardingRegistrationDetails onboardingRegistrationDetails) {
		//OTP Verifiction

		//check if Inprogress GSTIN record exists in the system
		
		System.out.println("inside submit of onboarding");
		System.out.println(" attachments size=" + onboardingRegistrationDetails.getAttachmentDetails().size());
		OnboardingRegistrationDetails inProgressOnboardingRegistrationDetails=null;
		OnboardingRegistrationDetails updatedOnboardingRegistrationDetails=null;
		//partnerDetailService.saveSupportingAttachmentsInOS(onboardingRegistrationDetails,"123","b123");
			
			try {
				inProgressOnboardingRegistrationDetails=onboardingRegistrationDetailsService.findByGstinAndOnboardingtatus(gstin, "In Progress");
				
				if (inProgressOnboardingRegistrationDetails==null) 
					return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("Registration Submit", "No record exists in the system with GSTIN:" +gstin )));
				else
				{
					onboardingRegistrationDetails.setLastupdateon(new Timestamp(System.currentTimeMillis()));
					updatedOnboardingRegistrationDetails=onboardingRegistrationDetailsService.update(onboardingRegistrationDetails);
					updatedOnboardingRegistrationDetails.setAttachmentDetails(onboardingRegistrationDetails.getAttachmentDetails());
					System.out.println("onboardingRegistrationDetails response submitted in Submit =" + updatedOnboardingRegistrationDetails);
					
					return partnerDetailService.submitSignUp(updatedOnboardingRegistrationDetails);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage(new APIError("onboarding saveDetails Error", e.getMessage())));
			}
				
		
	}

	

}