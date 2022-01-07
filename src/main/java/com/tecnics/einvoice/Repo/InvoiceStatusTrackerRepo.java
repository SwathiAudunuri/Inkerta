package com.tecnics.einvoice.Repo;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.InvoiceStatusTracker;
import com.tecnics.einvoice.constants.SQLQueries;

public interface InvoiceStatusTrackerRepo extends CrudRepository<InvoiceStatusTracker, String> {

	List<InvoiceStatusTracker> findByDocumentRefId(String documentRefId);
	

	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_INV_STATUSTRACKER)
	List<InvoiceStatusTrackerResults> findByVisibleToPartneridAndDocumentRefId(String partnerId,String documentRefId);	

	public interface InvoiceStatusTrackerResults {
		Integer getInvoice_status_tracker_id();
		String getDocument_ref_id();
		String getAction_type();
		String getAction();
		String getAction_comments();

		String getaction_by();
		Timestamp getAction_date();
		Boolean getIs_dispatched();
		String getDispatch_mode();
		Timestamp getDispatched_on();
		String getSource();
		String getAction_to();
		String getActionByDisplayName();
		
		

	}
}



