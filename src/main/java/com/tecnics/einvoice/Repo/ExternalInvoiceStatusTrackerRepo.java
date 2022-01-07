package com.tecnics.einvoice.Repo;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.ExternalInvoiceStatusTracker;
import com.tecnics.einvoice.constants.SQLQueries;

public interface ExternalInvoiceStatusTrackerRepo extends CrudRepository<ExternalInvoiceStatusTracker, String> {

	List<ExternalInvoiceStatusTracker> findByDocumentRefId(String documentRefId);
	

	@Query(nativeQuery = true, value=SQLQueries.FETCH_EXT_INV_STATUSTRACKER)
	List<ExternalInvoiceStatusTrackerResults> fetchByDocumentRefId(String documentRefId);	

	public interface ExternalInvoiceStatusTrackerResults {
		Integer getInvoice_status_tracker_id();
		String getDocument_ref_id();
		String getAction_type();
		String getAction();
		String getAction_comments();
		String getaction_by();
		Timestamp getAction_date();		
		String getAction_to();
		String getActionByDisplayName();
		
		

	}
}




