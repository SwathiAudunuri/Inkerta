package com.tecnics.einvoice.Repo;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.EnquiryActivity;
import com.tecnics.einvoice.entity.RecipientActivity;
import com.tecnics.einvoice.entity.RecipientEmailMapping;


public interface RecipientEmailMappingRepo extends CrudRepository<RecipientEmailMapping, Integer>{



}
