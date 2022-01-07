package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tecnics.einvoice.entity.InvoiceDocumentDetail;

public interface InvoiceDocumentDetailRepo extends CrudRepository<InvoiceDocumentDetail, String> {
	
	
	@Modifying
	 @Transactional
	@Query("update InvoiceDocumentDetail idd set idd.invoice_status = :invoice_status where idd.document_ref_id = :document_ref_id")
	int setInvoice_statusForInvoiceDocumentDetail(@Param("invoice_status") String invoice_status, @Param("document_ref_id") String document_ref_id);

	@Modifying
	 @Transactional
	@Query("update InvoiceDocumentDetail idd set idd.assigned_to = :assigned_to where idd.document_ref_id = :document_ref_id")
	int setAssigned_To_ForInvoiceDocumentDetail(@Param("assigned_to") String assigned_to, @Param("document_ref_id") String document_ref_id);

	@Modifying
	 @Transactional
	@Query("update InvoiceDocumentDetail idd set idd.assigned_to = NULL where idd.document_ref_id = :document_ref_id")
	int setAssigned_To_Null_ForInvoiceDocumentDetail(@Param("document_ref_id") String document_ref_id);

}
