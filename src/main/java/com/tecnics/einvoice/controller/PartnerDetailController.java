package com.tecnics.einvoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.PartnerDetail;
import com.tecnics.einvoice.entity.SignupRequestModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.SignupResponse;
import com.tecnics.einvoice.service.PartnerDetailService;
import com.tecnics.einvoice.util.APIError;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PartnerDetailController {

	private PartnerDetailService partnerDetailService;

	@Autowired
	public PartnerDetailController(PartnerDetailService thePartnerDetailService) {
		partnerDetailService = thePartnerDetailService;
	}

	@GetMapping("/partnerDetail")
	public ResponseEntity<ResponseMessage> findAll() {
		List<PartnerDetail> response = partnerDetailService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}


	
	@PostMapping("/signup")
	public ResponseEntity<ResponseMessage> onboarding(@RequestBody SignupRequestModel obj) {
		SignupResponse response;
		try {
			response = partnerDetailService.save(obj);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(new APIError("500", e.getMessage())));
		}
	}
	
	
	@GetMapping("/partnerDetail/{partnerId}")
	public ResponseEntity<ResponseMessage> findAll(@PathVariable String partnerId) {
		List<PartnerDetail> response = null;
		try {
			response = partnerDetailService.findByPartnerId(partnerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

	}

	@GetMapping("/isDuplicategstn/{gstn}")
	public boolean validategstn(@PathVariable String gstn) {
		boolean response = false;
		try {

			int count = partnerDetailService.validategstn(gstn);
			if (count > 0) {
				response = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/isDuplicatephone/{primaryPhone}")
	public ResponseEntity<ResponseMessage> validatePrimaryPhone(@PathVariable String primaryPhone) {
		boolean response = false;
		try {

			int count = partnerDetailService.validatePrimaryPhone(primaryPhone);
			if (count > 0) {
				response = true;
			}

		} catch (Exception e) {
			throw e;
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@GetMapping("/isDuplicateemail/{email}")
	public ResponseEntity<ResponseMessage> validateEmail(@PathVariable String email) {
		boolean response = false;
		int count = partnerDetailService.validateEmail(email);
		if (count > 0) {
			response = true;
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

}
