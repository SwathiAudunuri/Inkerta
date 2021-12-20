package com.tecnics.einvoice.controller;

import java.util.List;
import java.util.Optional;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.entity.PartnerProfileActivity;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.service.PartnerInvitationDetailService;
import com.tecnics.einvoice.service.PartnerProfileActivityService;
import com.tecnics.einvoice.util.APIError;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PartnerProfileActivityController extends BaseController {

	

	private PartnerProfileActivityService partnerProfileActivityService;

	@Autowired
	PartnerInvitationDetailService partnerInvitationDetailService;

	@Autowired
	public PartnerProfileActivityController(PartnerProfileActivityService thePartnerProfileActivityService) {
		partnerProfileActivityService = thePartnerProfileActivityService;
	}

	@GetMapping("/profileActivity")
	public ResponseEntity<ResponseMessage> findAll() {
		List<PartnerProfileActivity> response = partnerProfileActivityService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@GetMapping("/profileActivity/partnerid/{partnerid}")
	public ResponseEntity<ResponseMessage> findBypartnerid(@PathVariable String partnerid) {
		Optional<PartnerProfileActivity> response = partnerProfileActivityService.findBypartnerid(partnerid);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@PostMapping("/profileActivity/{regId}")
	public ResponseEntity<ResponseMessage> update(@PathVariable("regId") String regId,
		@RequestBody PartnerProfileActivity obj) throws JsonMappingException, JsonProcessingException {
		PartnerProfileActivity response = new PartnerProfileActivity();
		int transactionId = 0;
		String partnerID = "";
		String status = "";
		try {
			status = obj.getPartnerProfileTransactions().get(0).getActionTaken();
			partnerID = partnerInvitationDetailService.findPartnerIdByRegId(regId);
			obj.setPartnerId(partnerID);
			transactionId = partnerInvitationDetailService.findTransactionIdByPartnerId(partnerID);
			if (transactionId != 0) {
				obj.setTransactionId(transactionId);
			}
			switch (status) {
			case Constants.APPROVE: {
				response = partnerProfileActivityService.save(obj);
				partnerProfileActivityService.approve(partnerID,obj.getProfileJsonDetails());
				if(response!=null) {
					partnerInvitationDetailService.updateInvStatus(regId, status);
				}
				break;
			}
			case Constants.DISCARD: {
				
				response = partnerProfileActivityService.save(obj);
				partnerProfileActivityService.discard(obj);
				if(response!=null) {
					partnerInvitationDetailService.updateInvStatus(regId, status);
				}
				break;
			}
			case Constants.NEEDMOREDETAILS: {
				response = partnerProfileActivityService.save(obj);
				partnerProfileActivityService.needMoreDetails(obj);
				if(response!=null) {
					partnerInvitationDetailService.updateInvStatus(regId, status);
				}
				break;
			}
			default: {
				response = partnerProfileActivityService.save(obj);
				if(response!=null) {
					partnerInvitationDetailService.updateInvStatus(regId, status);
				}
				break;
			}
			}

		} catch (Exception ex) {
			return ResponseEntity.ok().body(
					new ResponseMessage(new APIError(Ex.PARTNER_PROFILE_ACTIVITY_UPDATE_FAILED.getKey(),
							Ex.formatMessage(Ex.PARTNER_PROFILE_ACTIVITY_UPDATE_FAILED.getKeyMessage()), getStackTrace(ex))));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
}
