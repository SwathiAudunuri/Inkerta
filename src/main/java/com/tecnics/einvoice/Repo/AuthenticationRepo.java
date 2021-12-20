package com.tecnics.einvoice.Repo;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.Authorizationskeys;
import com.tecnics.einvoice.entity.PasswordResetRequest;

public interface AuthenticationRepo extends CrudRepository<Authorizationskeys, Integer> {

	Authorizationskeys findByUserId(String user_id);

}
