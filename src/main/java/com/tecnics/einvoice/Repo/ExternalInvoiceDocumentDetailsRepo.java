package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tecnics.einvoice.entity.ExternalInvoiceDocumentDetails;

public interface ExternalInvoiceDocumentDetailsRepo extends CrudRepository<ExternalInvoiceDocumentDetails, String> {
	
	
	@Modifying
	 @Transactional
	@Query("update ExternalInvoiceDocumentDetails eidd set eidd.invoice_status = :invoice_status where eidd.document_ref_id = :document_ref_id")
	int setInvoice_statusForInvoiceDocumentDetail(@Param("invoice_status") String invoice_status, @Param("document_ref_id") String document_ref_id);

	@Modifying
	 @Transactional
	@Query("update ExternalInvoiceDocumentDetails eidd set eidd.assigned_to = :assigned_to where eidd.document_ref_id = :document_ref_id")
	int setAssigned_To_ForInvoiceDocumentDetail(@Param("assigned_to") String assigned_to, @Param("document_ref_id") String document_ref_id);

	@Modifying
	 @Transactional
	@Query("update ExternalInvoiceDocumentDetails eidd set eidd.assigned_to = NULL where eidd.document_ref_id = :document_ref_id")
	int setAssigned_To_Null_ForInvoiceDocumentDetail(@Param("document_ref_id") String document_ref_id);

}
