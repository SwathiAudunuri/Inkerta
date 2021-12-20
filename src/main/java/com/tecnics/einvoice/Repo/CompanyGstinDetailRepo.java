package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.CompanyGstinDetail;
import com.tecnics.einvoice.entity.InvoiceAudit;


public interface CompanyGstinDetailRepo extends CrudRepository<CompanyGstinDetail, Integer>{
	
	CompanyGstinDetail findByGstin(String gstin);
	
}
