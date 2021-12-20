package com.tecnics.einvoice.Repo;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.InvoiceStatusTracker;

public interface InvoiceStatusTrackerRepo extends CrudRepository<InvoiceStatusTracker, Integer> {

	List<InvoiceStatusTracker> findByDocumentRefId(String documentRefId);
}



