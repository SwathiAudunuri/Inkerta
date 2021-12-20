package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.PartnerInvitationRepo;
import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.controller.BaseController;
import com.tecnics.einvoice.entity.PartnerInvitationDetail;
import com.tecnics.einvoice.entity.PartnerProfileActivity;
import com.tecnics.einvoice.log.BaseLogger;
import com.tecnics.einvoice.serviceImpl.PartnerInvitationDetailServiceImpl;
@Component
public class PartnerInvitationDetailService {

	 BaseLogger log = BaseLogger.getBaseLogger(BaseController.class);

	@Autowired
	PartnerInvitationRepo partnerInvitationDetailRepo;

	@Autowired
	PartnerInvitationDetailServiceImpl partnerInvitationDetailService;

	@Autowired
	PartnerProfileActivityService partnerProfileActivityService;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<PartnerInvitationDetail> findAll() {
		return (List<PartnerInvitationDetail>) partnerInvitationDetailRepo.findAll();
	}

	public String postAndGenerate(PartnerInvitationDetail details) {
		return partnerInvitationDetailService.generateURL(details.getPartnerType(), Constants.TESTUSER,
				details.getCompanyName(), details.getPersonName(),
				details.getMobileNumber(), details.getEmail(), details.getFirmType(),
				details.getInvDescription(), Constants.TESTUSER, details.getStatus());
	}

	public List<PartnerInvitationDetail> findByStatus(String status) {
		return partnerInvitationDetailRepo.findByStatus(status);

	}

	public Optional<PartnerProfileActivity> findDetailsByReg_id(String regId) {
		String partnerid = findPartnerIdByRegId(regId);
		Optional<PartnerProfileActivity> obj = partnerProfileActivityService.findBypartnerid(partnerid);
		if (!obj.isPresent())
			return Optional.of(new PartnerProfileActivity());
		else
			return partnerProfileActivityService.findBypartnerid(partnerid);
	}

	public String findPartnerIdByRegId(String regId) {
		String methodname ="findPartnerIdByRegId";
		String partnerId ="";
		try {
			partnerId = jdbcTemplate.queryForObject(SQLQueries.GET_PARTNERID_BY_REGID, new Object[] { regId },String.class);
		}
		catch(Exception e) {log.logErrorMessage(e.getMessage(), e);}
		return	partnerId;
	}
	
	public int findTransactionIdByPartnerId(String partner_id) {
		try{
			return	 jdbcTemplate.queryForObject(SQLQueries.GET_PARTNERID_BY_PID, new Object[] { partner_id },Integer.class);
		}
		
		catch(EmptyResultDataAccessException ex) {
			return 0;
		}
	}

	
	
	public int updateInvStatus(String regId, String status) {
		try {
			return jdbcTemplate.queryForObject(SQLQueries.UPDATE_INVITATION_DTLS, new Object[] { status,regId },Integer.class);
		}
		catch (Exception e) {
			return 0;
		}
		
	}

}
