package com.tecnics.einvoice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.Enquiry;
import com.tecnics.einvoice.exceptions.TokenExpiryException;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.service.EnquiryService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class EnquiryController extends BaseController {

	private EnquiryService enquiryService;

	@Autowired
	public EnquiryController(EnquiryService theEnquiryService) {
		enquiryService = theEnquiryService;
	}

	@GetMapping("/enquiries")
	public  ResponseEntity<ResponseMessage>  findAll() {
		List<Enquiry> response = enquiryService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@GetMapping("/enquiries/{id}")
	public ResponseEntity<ResponseMessage> findById(@PathVariable int id) {
		Optional<Enquiry> response=  enquiryService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@PostMapping("/enquiry/{id}")
	public ResponseEntity<ResponseMessage> save(@PathVariable int id, @RequestBody Enquiry obj,@RequestHeader("authorization") String token) {
		obj.setEnqRefId(id);
		Enquiry response=  enquiryService.save(obj,getUserName(token));
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@PostMapping("/enquiry")
	public ResponseEntity<ResponseMessage>  save(@RequestBody Enquiry obj) {
		Enquiry response=  enquiryService.save(obj,"anonymous");
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	

	@DeleteMapping("/enquiries/{id}")
	public void delete(@PathVariable int id) {
		enquiryService.delete(id);
	}

	@GetMapping("/inkreta")
	public  ResponseEntity<ResponseMessage>  test() {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Welcome to Inkreta"));

	}


}
