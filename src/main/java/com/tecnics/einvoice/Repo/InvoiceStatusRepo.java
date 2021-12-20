package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.InvoiceStatus;

public interface InvoiceStatusRepo extends CrudRepository<InvoiceStatus, Integer>{
	
	List<InvoiceStatus> findByPartnerTypeAndStatusKey(String partnerType, String statusKey);
	

}


