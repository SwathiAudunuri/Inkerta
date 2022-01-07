package com.tecnics.einvoice.Repo;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.entity.PartnerDetails;

public interface PartnerDetailsRepo extends CrudRepository<PartnerDetails, String> {

	List<PartnerDetails> findByPartnerId(String partnerId);
	
	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_PARTNERS_LIST)
	List<PartnersListResults> fetchPartnersList();

	public interface PartnersListResults {
		String getPartner_id();
		String getCompany_name();
		String getPartner_type();
		String getStatus();
		Timestamp getOnboarded_on();
		String getContactName();
		String getPrimary_phone_number();

	}
	
	@Modifying
	 @Transactional
	@Query("update PartnerDetails set status = :status where partner_id = :partner_id")
	int setPartnerStatus(@Param("status") String status, @Param("partner_id") String partner_id);


}
