package com.tecnics.einvoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.FieldMaster;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.service.FieldMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FieldMasterController {

	private FieldMasterService fieldMasterService;

	@Autowired
	public FieldMasterController(FieldMasterService theFieldMasterService) {
		fieldMasterService = theFieldMasterService;
	}

	@GetMapping("/lookup/{fieldName}")
	public ResponseEntity<ResponseMessage> findById(@PathVariable String fieldName ) {
		List<FieldMaster> response=  fieldMasterService.findByFieldName(fieldName);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	@GetMapping("/lookups/{fieldName}/{moduleName}")
	public ResponseEntity<ResponseMessage> findByModuleNameAndFieldName(@PathVariable String fieldName , @PathVariable String moduleName) {
		List<FieldMaster> response=  fieldMasterService.findByFieldNameAndModuleName(fieldName,moduleName);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	
	@GetMapping("/lookup")
	public ResponseEntity<ResponseMessage> findByFieldNameAndModuleName( @RequestParam("fieldName") String fieldName,@RequestParam("moduleName") String moduleName) {
		List<FieldMaster> response=  fieldMasterService.findByFieldNameAndModuleName(fieldName,moduleName);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}





}
