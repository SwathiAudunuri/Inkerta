package com.tecnics.einvoice.Repo;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.EnquiryActivity;
import com.tecnics.einvoice.entity.RecipientActivity;
import com.tecnics.einvoice.entity.RecipientEmailMapping;
import com.tecnics.einvoice.entity.RecipientWebserviceMapping;


public interface RecipientWebServiceRepo extends CrudRepository<RecipientWebserviceMapping, Integer>{



}
