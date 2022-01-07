package com.tecnics.einvoice.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.PartnerGSTINDetails;

public interface PartnerGSTINDetailsRepo extends CrudRepository<PartnerGSTINDetails, String> {

	Optional<PartnerGSTINDetails> findById(String gstin);
	List<PartnerGSTINDetails> findByPartnerId(String partnerId);
	Optional<PartnerGSTINDetails> findByGstinAndPartnerId(String gstin,String partnerId);
	
	
}

