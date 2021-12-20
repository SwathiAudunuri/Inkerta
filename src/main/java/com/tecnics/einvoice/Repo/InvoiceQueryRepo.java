package com.tecnics.einvoice.Repo;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * 
 */

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.InvoiceQuery;

/**
 * @author Mohammed Farooq
 *
 */
public interface InvoiceQueryRepo extends CrudRepository<InvoiceQuery, Integer> {
	
	List<InvoiceQuery> findByDocumentRefId(String documentRefId);
	List<InvoiceQuery> findById(int id);
	List<InvoiceQuery> findByParentQueryRefId(String parentQueryRefId);
	
	List<InvoiceQuery> findByDocumentRefIdAndQueryRefId(String documentRefId,String queryRefId);
	List<InvoiceQuery> findByDocumentRefIdAndParentQueryRefId(String documentRefId,String parentQueryRefId);
	

	@Query(nativeQuery = true,value = "select count(0) from einvoicing.invoice_queries where document_ref_id=:documentRefId and (parent_query_ref_id='' or parent_query_ref_id=null)")
	public Long count(@Param("documentRefId") String documentRefId);
	
	}
