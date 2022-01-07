package com.tecnics.einvoice.controller;

import java.util.List;
import java.util.Map;
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

import com.tecnics.einvoice.entity.RecipientMapping;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.response.RecipientMappingResponse;
import com.tecnics.einvoice.service.RecipientMappingService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RecipientMappingController extends BaseController {

	private RecipientMappingService recipientMappingService;

	@Autowired
	public RecipientMappingController(RecipientMappingService theRecipientMappingService) {
		recipientMappingService = theRecipientMappingService;
	}

	@GetMapping("/reciepientMapping")
	public ResponseEntity<ResponseMessage> findAll() {
		List<RecipientMapping> response = recipientMappingService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@GetMapping("/reciepientMapping/{id}")
	public ResponseEntity<ResponseMessage> findById(@PathVariable String id) {
		Optional<RecipientMapping> response = recipientMappingService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@PutMapping("/reciepientMapping/{id}")
	public ResponseEntity<ResponseMessage> save(@PathVariable int id, @RequestBody RecipientMapping obj) {
		RecipientMapping response = recipientMappingService.update(id, obj);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	@PutMapping("/inactiveReciepent/{id}")
	public ResponseEntity<ResponseMessage> inActivate(@PathVariable String id) {
		Optional<RecipientMapping> response = recipientMappingService.findById(id);
		response.get().setIsActive(false);
		RecipientMapping response2 = recipientMappingService.update(12, response.get());
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response2));
	}
	@PutMapping("/activeReciepent/{id}")
	public ResponseEntity<ResponseMessage> activate(@PathVariable String id) {
		Optional<RecipientMapping> response = recipientMappingService.findById(id);
		response.get().setIsActive(true);
		RecipientMapping response2 = recipientMappingService.update(12, response.get());
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response2));
	}


	@PostMapping("/reciepientMapping")
	public ResponseEntity<ResponseMessage> save(@RequestBody RecipientMapping obj,
			@RequestHeader(value ="authorization", required=false) String token) {
		RecipientMappingResponse response = recipientMappingService.save(obj, getUserName(token));
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@DeleteMapping("/reciepientMapping/{id}")
	public void delete(@PathVariable String id) {
		recipientMappingService.delete(id);
	}

	@GetMapping("/getReciepientId")
	public ResponseEntity<ResponseMessage> generateReciepientID() {
		String response = "";
		try {
			response = recipientMappingService.generateReciepientID();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		}

	}

	@GetMapping("/getGstnList")
	public ResponseEntity<ResponseMessage> getGstnList(@RequestHeader("authorization") String token) {
		Map<String, Object> response = recipientMappingService.getGSTNList(getUserName(token));
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

}
