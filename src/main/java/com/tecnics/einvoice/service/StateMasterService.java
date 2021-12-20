/**
 * 
 */
package com.tecnics.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.RecipientMappingRepo;
import com.tecnics.einvoice.Repo.StateMasterRepo;
import com.tecnics.einvoice.entity.StateMaster;
@Component
public class StateMasterService {
	
	
	@Autowired
	StateMasterRepo stateMasterRepo;
	
	
	public List<StateMaster> findAll() {
		return (List<StateMaster>) stateMasterRepo.findAll();
	}

}
