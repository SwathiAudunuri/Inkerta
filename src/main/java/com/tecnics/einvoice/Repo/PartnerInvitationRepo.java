package com.tecnics.einvoice.Repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.PartnerInvitationDetail;

public interface PartnerInvitationRepo extends CrudRepository<PartnerInvitationDetail, String> {

	String FETCHPARTNERID = "SELECT t.partnerId FROM einvoicing.PartnerInvitationDetail t where t.regId=:regId ";


	@Procedure(procedureName = "sp_generate_url")
	String postAndGenerate(String in_partner_type, Timestamp in_inv_sent_on, String in_inv_sent_by,
			String in_partner_company_name, String in_partner_contact_person_name, Long in_partner_contact_mobile_no,
			String in_partner_contact_email, String in_partner_firm_type, String in_inv_description,
			String in_inv_req_raised_by, Timestamp in_inv_req_raised_on
	);
	

	List<PartnerInvitationDetail> findByStatus(String status);



}
