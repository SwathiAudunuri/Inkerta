package com.tecnics.einvoice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.alfresco.AlfrescoService;
import com.tecnics.einvoice.alfresco.FolderRequest;
import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.entity.PartnerInvitationDetail;
import com.tecnics.einvoice.entity.PartnerProfileActivity;
import com.tecnics.einvoice.entity.PartnerProfileTransaction;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.log.BaseLogger;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.service.PartnerInvitationDetailService;
import com.tecnics.einvoice.service.PartnerProfileActivityService;
import com.tecnics.einvoice.util.APIError;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PartnerInvitationController extends BaseController {

	 BaseLogger log = BaseLogger.getBaseLogger(BaseController.class);


	private PartnerInvitationDetailService partnerInvitationService;

	@Autowired
	AlfrescoService alfrescoService;
	
	@Autowired
	PartnerProfileActivityService partnerProfileActivityService;


	@Autowired
	public PartnerInvitationController(PartnerInvitationDetailService theCompanyService) {
		partnerInvitationService = theCompanyService;
	}

	@GetMapping("/partnerInvitation")
	public ResponseEntity<ResponseMessage> findAll() {
		List<PartnerInvitationDetail> response = partnerInvitationService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@PostMapping("/partnerInvitation")
	public  ResponseEntity<ResponseMessage>  postAndGenerate(@RequestBody PartnerInvitationDetail details) {
		/***
		 * Calling Storder Procedure that generates Registration URL
		 */
		String response =partnerInvitationService.postAndGenerate(details);
		String segments[] = response.split("=");
		String regID= segments[segments.length - 1];
		if(response!=null) {
			
			
			String folderId = alfrescoService.callcreateFolder(new FolderRequest(details.getCompanyName()));
		
			if(folderId!=null) {
				PartnerProfileActivity obj = new PartnerProfileActivity();
				String partnerId= partnerInvitationService.findPartnerIdByRegId(regID);
				details.setPartnerId(partnerId);
				details.setFolderID(folderId);
				JSONObject myreqjson = new JSONObject(details);
				String myreq = myreqjson.toString();
				
				obj.setPartnerId(partnerId);
				obj.setActivityStatus(Constants.PROCESS_INITITATED);
				obj.setProfileJsonDetails(myreq);
				obj.setActivityType(Constants.PARTNER_FOLDER_CREATION);
				
				PartnerProfileTransaction log = new PartnerProfileTransaction();
				log.setActionBy(Constants.TESTUSER);
				log.setActionTaken(Constants.PROCESS_INITITATED);
				log.setActionComments(Constants.PARTNER_FOLDER_CREATION);
				
				List <PartnerProfileTransaction> transactions = new ArrayList<PartnerProfileTransaction>();
				transactions.add(log);
				
				obj.setPartnerProfileTransactions(transactions);

				partnerProfileActivityService.save(obj);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@GetMapping("/partnerInvitation/{status}")
	public ResponseEntity<ResponseMessage> findByStatus(@PathVariable String status) {
		List<PartnerInvitationDetail> response = partnerInvitationService.findByStatus(status);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@GetMapping("/registrationDetails/{regId}")
	public ResponseEntity<ResponseMessage> findByReg_id(@PathVariable String regId) {
		try {
			Optional<PartnerProfileActivity> response = partnerInvitationService.findDetailsByReg_id(regId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (EmptyResultDataAccessException ex) {
			return ResponseEntity.ok().body(
					new ResponseMessage(new APIError(Ex.GET_REGISTRATION_DETAILS_FAILED.getKey(),
							Ex.formatMessage(Ex.GET_REGISTRATION_DETAILS_FAILED.getKeyMessage()), getStackTrace(ex))));

		}
	}

}
