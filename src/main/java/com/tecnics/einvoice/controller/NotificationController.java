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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.NotificationsEntitiy;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.service.NotificationsService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class NotificationController extends BaseController{
	
	private NotificationsService notificationsService;

	@Autowired
	public NotificationController(NotificationsService theNotificationsService) {
		notificationsService = theNotificationsService;
	}

	
	@GetMapping("/notifications")
	public  ResponseEntity<ResponseMessage>  findAll() {
		List<NotificationsEntitiy> response = notificationsService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@GetMapping("/notifications/{id}")
	public ResponseEntity<ResponseMessage> findById(@PathVariable int id) {
		Optional<NotificationsEntitiy> response=  notificationsService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@PostMapping("/notifications/{id}")
	public ResponseEntity<ResponseMessage> save(@PathVariable int id, @RequestBody NotificationsEntitiy obj,@RequestHeader("authorization") String token) {
		NotificationsEntitiy response=  notificationsService.save(obj,getUserName(token));
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@PostMapping("/notifications")
	public ResponseEntity<ResponseMessage>  save(@RequestBody NotificationsEntitiy obj) {
		NotificationsEntitiy response=  notificationsService.save(obj,"anonymous");
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	

	@DeleteMapping("/notifications/{id}")
	public void delete(@PathVariable int id) {
		notificationsService.delete(id);
	}
}
