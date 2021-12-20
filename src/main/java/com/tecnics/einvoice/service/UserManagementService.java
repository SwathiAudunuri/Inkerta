package com.tecnics.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.UserManagementRepo;
import com.tecnics.einvoice.entity.UserManagement;

@Component
public class UserManagementService {

	@Autowired
	UserManagementRepo userManagementRepo;

	public List<UserManagement> findAll() {
		return (List<UserManagement>) userManagementRepo.findAll();
	}



	public UserManagement save(UserManagement obj) {
		return userManagementRepo.save(obj);
	}

	public void delete(String id) {
		 userManagementRepo.deleteById(id);
		
	}

	


	
}
