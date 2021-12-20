package com.tecnics.einvoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.service.ErrorLogService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ErrorLogController {

	private ErrorLogService errorLogService;
	

	@Autowired
	public ErrorLogController(ErrorLogService theerrorLogService) {
		errorLogService = theerrorLogService;
	}

	@PostMapping("/logerror")
	public ResponseEntity<ResponseMessage>  save(@RequestBody ErrorLog obj) {
		ErrorLog response=  errorLogService.save(obj);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}


	@GetMapping("/logerror")
	public ResponseEntity<ResponseMessage>  findAll() {
		List<ErrorLog> response=  errorLogService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	


}
