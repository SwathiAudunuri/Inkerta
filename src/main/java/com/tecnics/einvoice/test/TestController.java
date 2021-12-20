package com.tecnics.einvoice.test;

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

import com.tecnics.einvoice.file.manager.CreateFolderRequest;
import com.tecnics.einvoice.file.manager.CustomeResponseEntity;
import com.tecnics.einvoice.file.manager.FileManagerService;
import com.tecnics.einvoice.model.ResponseMessage;

@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestController {

	
	private TestService service;
	
	
	private FileManagerService fileManagerService;

	
	@Autowired
	public TestController(TestService service, FileManagerService fileManagerService) {
		super();
		this.service = service;
		this.fileManagerService = fileManagerService;
	}

	
	@GetMapping("/get")
	public  ResponseEntity<ResponseMessage>  findAll() {
		Iterable<TestEntity> response = service.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	
	@GetMapping("/get/{rollnumber}")
	public  ResponseEntity<ResponseMessage>  findById(@PathVariable int rollnumber) {
		Optional<TestEntity> response = service.findById(rollnumber);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	

	@PostMapping("/post")
	public ResponseEntity<ResponseMessage>  save(@RequestBody TestEntity obj) {
		TestEntity response=  service.save(obj);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	
	
	
}
