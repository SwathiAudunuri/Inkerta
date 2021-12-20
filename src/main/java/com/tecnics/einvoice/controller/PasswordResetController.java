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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.PasswordReset;
import com.tecnics.einvoice.entity.PasswordResetRequest;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.service.PasswordResetService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PasswordResetController {

	@Autowired
	PasswordResetService passwordResetService = new PasswordResetService();

	public PasswordResetController(PasswordResetService passwordResetService) {
		super();
		this.passwordResetService = passwordResetService;
	}

	@GetMapping("/pwdreseturl/{user_id}")
	public PasswordReset generateuserId(@PathVariable String user_id) {
		return passwordResetService.generatePasswordResetid(user_id);
	}

	@PostMapping("/resetPwd/{user_id}")
	public PasswordResetRequest resetPassword(@RequestBody PasswordResetRequest obj) {
		return passwordResetService.resetPassword(obj);
	}

	@PutMapping("/pwdreseturl")
	public ResponseEntity<ResponseMessage> update(@RequestBody PasswordResetRequest request) {
		String response = passwordResetService.updatePassword(request);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

}
