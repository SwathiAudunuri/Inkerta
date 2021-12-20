package com.tecnics.einvoice.Repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.LoginOtpEntity;

public interface LoginOtpRepo extends CrudRepository<LoginOtpEntity,Integer> {

	//LoginOtpEntity findByPrimaryPhone(Long long1);
	
	@Query("SELECT t FROM LoginOtpEntity t where t.primaryPhone=:primaryPhone")
	LoginOtpEntity findByPrimaryPhone( @Param ("primaryPhone") String primaryPhone);

}
