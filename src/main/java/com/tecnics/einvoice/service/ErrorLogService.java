package com.tecnics.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.entity.ErrorLog;

@Component
public class ErrorLogService {

	@Autowired
	ErrorLogRepo errorLogRepo;
	
	public ErrorLog save(ErrorLog obj) {
		return errorLogRepo.save(obj);
	}

	public List<ErrorLog> findAll() {
		return (List<ErrorLog>) errorLogRepo.findAll();
	}

	
	
}
