package com.tecnics.einvoice.Repo;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.Enquiry;
import com.tecnics.einvoice.entity.ErrorLog;


public interface ErrorLogRepo extends CrudRepository<ErrorLog, Integer>{



}
