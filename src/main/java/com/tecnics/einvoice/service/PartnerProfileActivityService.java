package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnics.einvoice.Repo.CompanyBankDetailRepo;
import com.tecnics.einvoice.Repo.CompanyGstinDetailRepo;
import com.tecnics.einvoice.Repo.CompanyMsmeDetailRepo;
import com.tecnics.einvoice.Repo.ContactDetailRepo;
import com.tecnics.einvoice.Repo.PartnerDetailsRepo;
import com.tecnics.einvoice.Repo.PartnerProfileActivityRepo;
import com.tecnics.einvoice.Repo.PartnerProfileTransactionRepo;
import com.tecnics.einvoice.Repo.UserAuthorizationKeyRepo;
import com.tecnics.einvoice.Repo.UserManagementRepo;
import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.controller.PartnerDetailsController;
import com.tecnics.einvoice.entity.CompanyBankDetail;
import com.tecnics.einvoice.entity.CompanyGstinDetail;
import com.tecnics.einvoice.entity.CompanyMsmeDetail;
import com.tecnics.einvoice.entity.ContactDetail;
import com.tecnics.einvoice.entity.PartnerDetails;
import com.tecnics.einvoice.entity.PartnerProfileActivity;
import com.tecnics.einvoice.entity.PartnerProfileTransaction;
import com.tecnics.einvoice.entity.UserAuthorizationKey;
import com.tecnics.einvoice.entity.UserManagement;
import com.tecnics.einvoice.model.PartnerDetailModel;
import com.tecnics.einvoice.util.HashingUtil;

@Component
public class PartnerProfileActivityService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	PartnerProfileActivityRepo partnerProfileActivityRepo;
	

	@Autowired
	PartnerProfileTransactionRepo partnerProfileTransactionRepo;

	@Autowired
	PartnerDetailsController partnerDetailController;
	@Autowired
	PartnerDetailsRepo partnerDetailRepo;
	
	@Autowired
	ContactDetailRepo contactDetailRepo;
	
	@Autowired
	CompanyMsmeDetailRepo companyMsmeDetailRepo;
	
	
	@Autowired
	CompanyBankDetailRepo companyBankDetailRepo;
	
	@Autowired
	private HashingUtil hashingUtil;

	@Autowired
	UserManagementRepo userManagementRepo;
	
	@Autowired
	UserAuthorizationKeyRepo userAuthorizationKeyRepo;
	
	@Autowired
	CompanyGstinDetailRepo companyGstinDetailRepo;

	public List<PartnerProfileActivity> findAll() {
		return (List<PartnerProfileActivity>) partnerProfileActivityRepo.findAll();
	}

	public Optional<PartnerProfileActivity> findById(int id) {
		return (Optional<PartnerProfileActivity>) partnerProfileActivityRepo.findById(id);
	}


	public PartnerProfileActivity save(PartnerProfileActivity obj) {
		try {
			PartnerProfileActivity response = partnerProfileActivityRepo.save(obj);
			PartnerProfileTransaction log = obj.getPartnerProfileTransactions().get(0);
			log.setPartnerProfileActivity(obj);
			partnerProfileTransactionRepo.save(log);
			return response;
		}
		
		catch(Exception ex) {
			return null;

		}
			
	}

	public void delete(int id) {
		partnerProfileActivityRepo.deleteById(id);
	}

	public Optional<PartnerProfileActivity> findBypartnerid(String partnerid) {
		return  partnerProfileActivityRepo.findByPartnerId(partnerid);
	}

	public void approve(String partnerID, String partnerProfilejsonString)
			throws JsonMappingException, JsonProcessingException {
	
		try {
		PartnerDetailModel parnerDetailModel = new ObjectMapper().readValue(partnerProfilejsonString,
				PartnerDetailModel.class);

		PartnerDetails partnerobj = parnerDetailModel.getParnerDetail();
		partnerobj.setPartnerId(partnerID);
		partnerobj = partnerDetailRepo.save(partnerobj);

		ContactDetail contactobj = parnerDetailModel.getContactDetail();
		contactobj.setPartnerId(partnerobj.getPartnerId());
		contactDetailRepo.save(contactobj);

//		CompanyBankDetail bankobj = parnerDetailModel.getcompanyBankDetail();
//		bankobj.setPartnerDetail(partnerobj);
//		companyBankDetailRepo.save(bankobj);
//		
		
		
		CompanyGstinDetail gstnobj = parnerDetailModel.getcompanyGSTNDetail();
		if(gstnobj!=null &&  gstnobj.getGstin()!=null) {
			gstnobj.setPartnerId(partnerobj.getPartnerId());
			companyGstinDetailRepo.save(gstnobj);
		}
		

		CompanyMsmeDetail msmeobj = parnerDetailModel.getcompanyMSMEDetail();
		if(msmeobj!=null &&  msmeobj.getMsmeRegNo()!=null) {

		msmeobj.setPartnerDetail(partnerobj);
		companyMsmeDetailRepo.save(msmeobj);
		}
		// USer creation here

		UserManagement user = new UserManagement();
		user.setUserId(getUserId());
		//user.setPartnerDetail(partnerobj);
		user.setFirstName(contactobj.getPersonName());
		user.setEmail(contactobj.getEmail());
		user.setEmail(contactobj.getEmail());
		user.setPrimaryPhoneNumber(contactobj.getPrimaryPhone());
		user.setLocation(contactobj.getAddress());
		user = userManagementRepo.save(user);
		UserAuthorizationKey auth = new UserAuthorizationKey();
		auth.setUserManagement(user);
		auth.setAuthorizationKey(hashingUtil.encryptKey(Constants.DEFAULT_PASSWORD));
		userAuthorizationKeyRepo.save(auth);
		}
		catch(DataIntegrityViolationException  e) {
	        String message = e.getMostSpecificCause().getMessage();
		}
		
		catch(Exception e) {
		}
			

	}

	public void discard(PartnerProfileActivity obj) {
		
	}

	public void needMoreDetails(PartnerProfileActivity obj) {
		
	}

	public Optional<PartnerProfileActivity> findByPartnerId(int partnerId) {
		return (Optional<PartnerProfileActivity>) partnerProfileActivityRepo.findByPartnerId(partnerId);
	}

	
	
	public String getUserId() {
		String sql = "select einvoicing.generate_user_id()";
		String count = jdbcTemplate.queryForObject(sql, new Object[] {  }, String.class);
		return count;
	}
}
