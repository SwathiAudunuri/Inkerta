package com.tecnics.einvoice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.StateMaster;
import com.tecnics.einvoice.entity.TodoTask;
import com.tecnics.einvoice.service.StateMasterService;
import com.tecnics.einvoice.service.TodoTaskService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class StateMasterController {

	private StateMasterService stateMasterService;

	@Autowired
	public StateMasterController(StateMasterService thestateMasterService) {
		stateMasterService = thestateMasterService;
	}

	@GetMapping("/states")
	public List<StateMaster> findAll() {
		return stateMasterService.findAll();
	}


}
