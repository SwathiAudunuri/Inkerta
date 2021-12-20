package com.tecnics.einvoice.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.service.OtpService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OtpController {

	@Autowired
	OtpService service;
	
	@PostMapping("/otp/generate")
	public ResponseEntity<ResponseMessage> generate(@RequestBody OtpRequest request) throws MessagingException {
		String response = service.generate(request);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	@PostMapping("/otp/validate")
	public ResponseEntity<ResponseMessage> validate(@RequestBody OtpRequest request)  {
		boolean response = service.validate(request);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	

	
}
