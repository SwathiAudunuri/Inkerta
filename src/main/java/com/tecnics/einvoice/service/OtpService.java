package com.tecnics.einvoice.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.LoginOtpRepo;
import com.tecnics.einvoice.controller.OtpRequest;
import com.tecnics.einvoice.entity.LoginOtpEntity;

@Component
public class OtpService {

	@Autowired
	LoginOtpRepo otpRepo;

	@Autowired
	private JavaMailSender emailSender;

	
	/**
	 * save
	 * 
	 * @param request
	 * @param otpKey
	 * @return
	 * @throws MessagingException 
	 */
	public String generate(OtpRequest request) throws MessagingException {
		try {
			//generating random number as OTP 
			 String otpKey = generateOTP();
			 //Email the otpKey to the user
			 sendHtmlMail(request, otpKey);
			 //SMS the otp to user - pending
			 
			LoginOtpEntity loginOtp = new LoginOtpEntity();
			loginOtp.setEmail(request.getEmail());
			loginOtp.setOtp_key(otpKey);
			loginOtp.setPrimaryPhone(request.getPrimaryPhoneNumber()+"");
			loginOtp.setSecondary_phone(request.getSecondaryPhoneNumber());
			otpRepo.save(loginOtp);
			return "Otp generated";
		}
		catch(Exception e) {
			return "fail";
		}
		
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public boolean validate(OtpRequest request) {
		boolean flag= false;
		System.out.println("validate method");
		if (otpRepo.findByPrimaryPhone(request.getPrimaryPhoneNumber())!=null) {
			LoginOtpEntity myobj = otpRepo.findByPrimaryPhone(request.getPrimaryPhoneNumber());
			System.err.println("myobj"+myobj);
			if(myobj.getOtp_key()!=null) {
				String generatedOtp = myobj.getOtp_key();
				String receivedOtp = request.getOtpKey();
				if(receivedOtp.equals(generatedOtp)) {
					flag= true;
				}
			}
		}
		return flag;
	}

	
	/**
	 * generateOTP
	 * 
	 * @return
	 */
	public static String generateOTP() {
		int randomPin = (int) (Math.random() * 9000) + 1000;
		String otp = String.valueOf(randomPin);
		return otp;
	}

	/**
	 * sendHtmlMail
	 * 
	 * @param req
	 * @param otpKey
	 * @return
	 * @throws MessagingException
	 */
	@Async
	public String sendHtmlMail(OtpRequest req, String otpKey) throws MessagingException {

		MimeMessage mail = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		String subject = "Validate Inkreta Signup";
		String body = "<p>Dear User,</p><p> We have recieved your intrest to sign up with Inkreta.Please find your OTP here <b>" + otpKey
				+ " </b>. <br/>Do not share your otp with Others</p>";

		helper.setFrom("vendorportalsuppor@gmail.com");
		helper.setTo(req.getEmail());
		helper.setSubject(subject);
		helper.setText(body, true);
		emailSender.send(mail);
		return "email send Successful to " + req.getEmail();
	}


	


}
