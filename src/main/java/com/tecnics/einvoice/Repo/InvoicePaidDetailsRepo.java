package com.tecnics.einvoice.Repo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.InvoicePaidDetails;


public interface InvoicePaidDetailsRepo extends CrudRepository<InvoicePaidDetails, Integer> {
	
	List<InvoicePaidDetails> findByPaidByPartnerIdAndDocumentRefId(String paidby_partner_id,String document_ref_id);
	List<InvoicePaidDetails> findByPaidToPartnerIdAndDocumentRefId(String paidto_partner_id,String document_ref_id);
	List<InvoicePaidDetails> findByDocumentRefId(String document_ref_id);
}

