package com.tecnics.einvoice.Repo;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.entity.ExternalPartnerDetails;;

public interface ExternalPartnerDetailsRepo extends CrudRepository<ExternalPartnerDetails, String> {

	ExternalPartnerDetails findByPartnerId(String partnerId);
	
	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_EXTERNAL_PARTNERS_LIST)
	List<ExternalPartnersListResults> fetchPartnersList(String partnerId);

	public interface ExternalPartnersListResults {
		String getPartner_id();
		String getCompany_name();
		String getPartner_type();
		String getContactName();
		String getPrimary_phone_number();

	}


}

