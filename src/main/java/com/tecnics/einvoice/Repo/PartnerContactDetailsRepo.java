package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.PartnerContactDetails;
public interface PartnerContactDetailsRepo extends CrudRepository<PartnerContactDetails, String> {
	
	List<PartnerContactDetails> findByPartnerId(String partnerId);

}
