package com.tecnics.einvoice.Repo;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.CompanyBankDetail;
import com.tecnics.einvoice.entity.UserManagement;


public interface UserManagementRepo extends CrudRepository<UserManagement, String>{

	UserManagement findByUserId(String user_id);

}
