package com.tecnics.einvoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.RecipientMapping;
import com.tecnics.einvoice.entity.UserManagement;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.service.UserManagementService;
import com.tecnics.einvoice.service.VendorMappingService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserManagementController extends BaseController {
	private UserManagementService  userManagementService;
	@Autowired
	public UserManagementController(UserManagementService theUserMappingService) {
		userManagementService = theUserMappingService;
	}
	
	@GetMapping("/getusers")
	public ResponseEntity<ResponseMessage> findAll() {
		List<UserManagement> response = userManagementService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

}
