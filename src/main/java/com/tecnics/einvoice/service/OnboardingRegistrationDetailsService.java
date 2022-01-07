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

import com.tecnics.einvoice.Repo.CustomActionsByInvoiceStatusRepo;
import com.tecnics.einvoice.Repo.CustomActionsByPartnerRoleRepo;
import com.tecnics.einvoice.Repo.CustomActionsRepo;
import com.tecnics.einvoice.Repo.ErrorLogRepo;

import com.tecnics.einvoice.Repo.OnboardingRegistrationDetailsRepo;
import com.tecnics.einvoice.controller.OtpRequest;
import com.tecnics.einvoice.entity.CaptchaOTPKeys;
import com.tecnics.einvoice.entity.CustomActions;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.FieldMaster;
import com.tecnics.einvoice.entity.OnboardingRegistrationDetails;
import com.tecnics.einvoice.model.PartnerGSTINResponseModel;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.util.SendMailUtil;

@Component
public class OnboardingRegistrationDetailsService extends BaseService {
	
	@Autowired
	OnboardingRegistrationDetailsRepo onboardingRegistrationDetailsRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	@Autowired
	private JavaMailSender emailSender;
	
	public OnboardingRegistrationDetails findByGstinAndOnboardingtatus(String gstin, String onboardingstatus) {
		
		Optional<OnboardingRegistrationDetails>  onboardingRegistrationDetails=onboardingRegistrationDetailsRepo.findByGstinAndOnboardingstatus(gstin,onboardingstatus);
		if (onboardingRegistrationDetails!=null && onboardingRegistrationDetails.isPresent())			
		    return onboardingRegistrationDetails.get();		
		else		
			return null;
		
	}
	
	
	public OnboardingRegistrationDetails update(OnboardingRegistrationDetails obj) {
		return onboardingRegistrationDetailsRepo.save(obj);		
	}
	
	public OnboardingRegistrationDetails save(OnboardingRegistrationDetails obj) {
		
		OnboardingRegistrationDetails response=null;
		System.out.println("Inside save of OnboardingRegistrationDetails");
			
		try {
			response = onboardingRegistrationDetailsRepo.save(obj);							
		}
		
		catch(Exception ex){
			
			ErrorLog error = new ErrorLog();
			error.setComponentName("OnboardingRegistrationDetailsService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			//error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
		}
		
		return response;
	}
	
	/**
	 * sendHtmlMail
	 * 
	 * @param obj
	 * @param otpKey
	 * @return
	 * @throws MessagingException
	 */
	@Async
	public String sendOTPKeyHtmlMail(String emailId,Integer otpKey) throws MessagingException {

		try
		{
		String mailSubject = "Validate Inkreta Signup : OTP for email verification ";
		
		String mailBody = "<p> Dear User, </p>"
				+ "<p> Warm Greetings from Inkreta !!! </p>"
				+ "<p> We request you please complete the OTP verification using auto-generated One Time Password: <strong>"+otpKey+"</strong> ! </p>"
				+ "<p> This One Time Password will auto expires in 5 minutes or in case of an unsuccessful attempt. </p>\r\n"
				+ "<p> <strong> Kindly Note- If you do not recognize this activity, you can safely ignore this email and you can always reach "
				+ "out to us in case you require any assistance at </strong> <a href=\"mailto:support@myinkreta.com\">support@myinkreta.com</a> </p>"
				+ "<p>** This is an auto-generated email. Please do not reply to this email.** </p>"
				+ "<p> Warm Regards</p>"
				+ "<p>Team InKreta </p>";
			
	//	String mailBody = "<p>Dear User,</p><p> We have recieved your intrest to sign up with Inkreta.Please find your OTP here <b>" + otpKey
			//	+ " </b>. <br/>Do not share your otp with Others</p>";

		
		
		SendMailUtil emailUtil = new SendMailUtil();
		
		String attachmentIds[]=null;
		String base64Flags[]= null;
		String attachmentNames[]=null;
		String mimeTypes[]= null;
		
		emailUtil.sendEmail("info@inkreta.com", "Melcome@123", new String[] {emailId, ""}, new String[] {}, new String[] {}, mailSubject, mailBody, base64Flags,attachmentIds,attachmentNames,mimeTypes,null,null,null);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "unable to send eMail";
		}
		return "email send Successful to " + emailId;
	}
	
	/**
	 * sendHtmlMail
	 * 
	 * @param obj
	 * @param otpKey
	 * @return
	 * @throws MessagingException
	 */
	@Async
	public String sendOTPKeySMS(String emailId,Integer otpKey) throws MessagingException {

		try
		{
			String mailSubject = "Validate Inkreta Signup : OTP for mobile verification ";
			
			String mailBody = "<p> Dear User, </p>"
					+ "<p> Warm Greetings from Inkreta !!! </p>"
					+ "<p> We request you please complete the OTP verification using auto-generated One Time Password: <strong>"+otpKey+"</strong> ! </p>"
					+ "<p> This One Time Password will auto expires in 5 minutes or in case of an unsuccessful attempt. </p>\r\n"
					+ "<p> <strong> Kindly Note- If you do not recognize this activity, you can safely ignore this email and you can always reach "
					+ "out to us in case you require any assistance at </strong> <a href=\"mailto:support@myinkreta.com\">support@myinkreta.com</a> </p>"
					+ "<p>** This is an auto-generated email. Please do not reply to this email.** </p>"
					+ "<p> Warm Regards</p>"
					+ "<p>Team InKreta </p>";

		
		
		SendMailUtil emailUtil = new SendMailUtil();
		
		String attachmentIds[]=null;
		String base64Flags[]= null;
		String attachmentNames[]=null;
		String mimeTypes[]= null;
		
		emailUtil.sendEmail("info@inkreta.com", "Melcome@123", new String[] {emailId, ""}, new String[] {}, new String[] {}, mailSubject, mailBody, base64Flags,attachmentIds,attachmentNames,mimeTypes,null,null,null);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "unable to send eMail";
		}
		return "email send Successful to " + emailId;
	}
	

}
