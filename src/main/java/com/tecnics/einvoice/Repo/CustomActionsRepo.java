package com.tecnics.einvoice.Repo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.CustomActions;

import com.tecnics.einvoice.constants.SQLQueries;

public interface CustomActionsRepo extends CrudRepository<CustomActions, String> {
	
	List<CustomActions> findByPartnerId(String partnerId);
	Optional<CustomActions> findByPartnerIdAndActionId(String partnerId, Integer actionId);
	Optional<CustomActions> findByPartnerIdAndActionName(String partnerId, String actionName);
	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_CUSTOM_ACTIONS_BY_PARTNERID)
	List<CustomActionsResults> fetchCustomActionsByPartnerId(String partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_CUSTOM_ACTIONS_BY_INVOICESTATUS_PARTNERROLES)
	List<CustomActionsResults> fetchActionsByInvoicestatusAndPartnerRoles(String partnerId, String invoice_status, List<String> partnerRoles);
	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_PARTNER_ROLES)
	List<String> fetchPartnerRoles(String partnerId);
	

	@Query(nativeQuery = true, value=SQLQueries.FETCH_INVOICE_STATUSES)
	List<String> fetchInvoiceStatuses();
	
	

	public interface CustomActionsResults {
		Integer getAction_id();
		String getAction_name();
		String getCreatedbydisplayname();
		String getAction_connector_type();
		Timestamp getCreated_date();
		String getRouting_component();

	}

}
