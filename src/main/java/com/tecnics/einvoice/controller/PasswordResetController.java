package com.tecnics.einvoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.PasswordReset;
import com.tecnics.einvoice.entity.PasswordResetRequest;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.PasswordResetService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PasswordResetController extends BaseController {

	@Autowired
	PasswordResetService passwordResetService = new PasswordResetService();

	public PasswordResetController(PasswordResetService passwordResetService) {
		super();
		this.passwordResetService = passwordResetService;
	}
	
	@PostMapping("/changePassword")
	public ResponseEntity<ResponseMessage> changePassword(@RequestBody PasswordResetRequest obj,@RequestHeader("authorization") String token) {
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + userObj);
		String partnerId= userObj.getPartnerId();	
		System.out.println("chagePassword in Password Reset controller = " + userObj.getUserId());
		
		return passwordResetService.changePassword(userObj,obj);
	}
	
	@PostMapping("/resetPassword")
	public ResponseEntity<ResponseMessage> resetPassword(@RequestBody PasswordResetRequest obj,@RequestHeader("authorization") String token) {
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + userObj);
		String partnerId= userObj.getPartnerId();	
		System.out.println("resetPassword in Password Reset controller = " + userObj.getUserId());
		
		return passwordResetService.resetPassword(userObj,obj);
	}


	

}
