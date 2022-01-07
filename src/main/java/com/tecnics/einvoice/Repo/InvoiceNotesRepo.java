package com.tecnics.einvoice.Repo;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.entity.InvoiceNotes;

public interface InvoiceNotesRepo extends CrudRepository<InvoiceNotes, Integer> {

	List<InvoiceNotes> findByPartnerId(String partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_INTERNAL_INV_NOTES)
	List<InvoiceNotesResults> fetchNotesByPartnerIdAndDocumenrRefId(String partnerId, String documentRefId);

	public interface InvoiceNotesResults {
		Integer getNotes_id();
		String getPartner_id();
		String getDocument_ref_id();
		String getNotes();
		String getNotes_type();
		String getCreated_by();
		String getCreatedbydisplayname();
		Timestamp getCreated_on();
		

	}
}


