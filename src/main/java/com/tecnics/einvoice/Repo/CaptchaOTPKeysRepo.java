package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.CaptchaOTPKeys;


public interface CaptchaOTPKeysRepo extends CrudRepository<CaptchaOTPKeys, Integer> {
	
	List<CaptchaOTPKeys> findByCaptchaOTPKey(Integer captcha_otp_key);
	List<CaptchaOTPKeys> findByCaptchaOTPKeyAndKeyTypeAndStatus(Integer captcha_otp_key,String key_type,String status);


}
