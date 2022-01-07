package com.tecnics.einvoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;

import com.tecnics.einvoice.Repo.CaptchaOTPKeysRepo;

import com.tecnics.einvoice.entity.ErrorLog;

import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.entity.CaptchaOTPKeys;
import com.tecnics.einvoice.entity.CustomActions;
import com.tecnics.einvoice.response.TransactionResponse;
@Component
public class CaptchaOTPKeysService extends BaseService {
	
	@Autowired
	CaptchaOTPKeysRepo captchaOTPKeysRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	public List<CaptchaOTPKeys> findByCaptchaKey(Integer captcha_otp_key) {
		return (List<CaptchaOTPKeys>) captchaOTPKeysRepo.findByCaptchaOTPKey(captcha_otp_key);
	}
	
	public List<CaptchaOTPKeys> findByCaptchaOTPKeyAndKeyTypeAndStatus(Integer captcha_key,String key_type,String status) {
		return (List<CaptchaOTPKeys>) captchaOTPKeysRepo.findByCaptchaOTPKeyAndKeyTypeAndStatus(captcha_key,key_type,status);
	}
	
	public CaptchaOTPKeys update(CaptchaOTPKeys obj) {	
		return captchaOTPKeysRepo.save(obj);
	}


	public CaptchaOTPKeys save(CaptchaOTPKeys obj) {
		
		CaptchaOTPKeys response=null;
		System.out.println("Inside save of CaptchaKeys");
			
		try {
			response = captchaOTPKeysRepo.save(obj);							
		}
		
		catch(Exception ex){
			
			ErrorLog error = new ErrorLog();
			error.setComponentName("CaptchaKeysService");
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
