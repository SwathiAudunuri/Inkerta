package com.tecnics.einvoice.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.PartnerProfileActivity;

public interface PartnerProfileActivityRepo 
extends CrudRepository<PartnerProfileActivity, Integer> {


	Optional<PartnerProfileActivity> findByPartnerId(String partnerid);

	Optional<PartnerProfileActivity> findByPartnerId(int partnerId);

}
