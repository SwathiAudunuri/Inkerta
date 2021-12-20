package com.tecnics.einvoice.Repo;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.PasswordReset;

public interface PasswordResetRepo extends CrudRepository<PasswordReset, String>{

}
