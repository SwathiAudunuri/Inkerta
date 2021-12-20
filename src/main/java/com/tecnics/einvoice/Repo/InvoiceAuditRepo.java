/**
 * 
 */
package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.InvoiceAudit;



public interface InvoiceAuditRepo extends CrudRepository<InvoiceAudit, Integer> {
	
	List<InvoiceAudit> findByRefId(String refId);

}
