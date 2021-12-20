package com.tecnics.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.CompanyGstinDetailRepo;
import com.tecnics.einvoice.Repo.ContactDetailRepo;
import com.tecnics.einvoice.Repo.PartnerDetailRepo;
import com.tecnics.einvoice.Repo.PartnerDetailSignupRepo;
import com.tecnics.einvoice.Repo.UserAuthorizationKeyRepo;
import com.tecnics.einvoice.Repo.UserManagementRepo;
import com.tecnics.einvoice.Repo.UserRolesRepo;
import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.entity.CompanyGstinDetail;
import com.tecnics.einvoice.entity.ContactDetail;
import com.tecnics.einvoice.entity.PartnerDetail;
import com.tecnics.einvoice.entity.PartnerDetailSignup;
import com.tecnics.einvoice.entity.PasswordReset;
import com.tecnics.einvoice.entity.SignupRequestModel;
import com.tecnics.einvoice.entity.UserAuthorizationKey;
import com.tecnics.einvoice.entity.UserManagement;
import com.tecnics.einvoice.entity.UserRoles;
import com.tecnics.einvoice.model.SignupResponse;
import com.tecnics.einvoice.util.HashingUtil;

@Component
public class PartnerDetailService {
	@Autowired
	private Environment env;
	

	@Autowired
	PartnerDetailRepo partnerDetailRepo;

	@Autowired
	PartnerDetailSignupRepo signupRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	UserManagementRepo userManagementRepo;

	@Autowired
	UserAuthorizationKeyRepo userAuthorizationKeyRepo;

	@Autowired
	UserRolesRepo userRoleRepo;

	@Autowired
	private HashingUtil hashingUtil;

	@Autowired
	ContactDetailRepo contactDetailRepo;

	@Autowired
	CompanyGstinDetailRepo companyGstinDetailRepo;
	
	
	@Autowired
	PasswordResetService passwordResetService;

	public List<PartnerDetail> findAll() {
		return (List<PartnerDetail>) partnerDetailRepo.findAll();
	}

	/**
	 * to save partner details on onboarding
	 * 
	 * @param request
	 * @return
	 */
	public SignupResponse save(SignupRequestModel request) {
		SignupResponse response = null  ;
		String partnerId = "";
		try {
			String partnerType = request.getPartnerDetails().getPartnerType();
			
			
			partnerId = generatePartnerId(partnerType);
			System.out.println("partnerID"+partnerId);

			PartnerDetailSignup partnerDetail = request.getPartnerDetails();
			partnerDetail.setPartnerId(partnerId);
			PartnerDetailSignup objres = signupRepo.save(partnerDetail);

			ContactDetail contDetailObj = request.getContactDetails();
			contDetailObj.setPartnerId(partnerId);
			contactDetailRepo.save(contDetailObj);

			CompanyGstinDetail gstnDetails = request.getGstnDetails();
			gstnDetails.setPartnerId(partnerId);
			companyGstinDetailRepo.save(gstnDetails);

			 response= createUser(partnerId, request, partnerType);
			 
			 
			 return response;
		} catch (Exception e) {
			System.out.println(e );
			throw e;
		}

		
	}

	/**
	 * createUser
	 * 
	 * @param partnerID
	 * @param obj
	 */
	public SignupResponse createUser(String partnerID, SignupRequestModel obj, String partnerType) {
		String userid= getUserId();
		SignupResponse response = new SignupResponse();

		System.out.println("createUser");
		try {

			UserManagement user = new UserManagement();
			user.setPartnerId(partnerID);
			
			user.setUserId(userid);
			
			String username =obj.getContactDetails().getPersonName();
			
			if (username.contains(" ")) {
				user.setFirstName(username.split(" ")[0]);
				user.setLastName(username.split(" ")[1]);
			}
			
			user.setEmail(obj.getContactDetails().getEmail());
			user.setPrimaryPhoneNumber(obj.getContactDetails().getPrimaryPhone());
			user.setLocation(obj.getContactDetails().getAddress());
			user.setUser_alias(obj.getAlias());
			System.err.println(obj.getAlias());
			user = userManagementRepo.save(user);
			String password = obj.getPassword();
			userAuthorisation(user,password);

			String userRole = UserRoleCreation(user, partnerType);
			
			response.setPartnerId(partnerID);
			response.setUserRole(userRole);
			response.setUserId(userid);
			
		} catch (DataIntegrityViolationException e) {
			String message = e.getMostSpecificCause().getMessage();
			System.err.println(message);
		}

		catch (Exception e) {
			System.err.println(e);
		}
		return response;

	}

	private void userAuthorisation(UserManagement user, String password) {
		UserAuthorizationKey auth = new UserAuthorizationKey();
		auth.setUserManagement(user);
		auth.setAuthorizationKey(hashingUtil.encryptKey(password));
		userAuthorizationKeyRepo.save(auth);
	}

	
	
	private String UserRoleCreation(UserManagement user, String partnerType) {

		UserRoles role = new UserRoles();
		String userRole ="";

		role.setUserId(user.getUserId());
		if (partnerType.equalsIgnoreCase("supplier")) {
			role.setUserRole("vendor_manager");
			userRole = "vendor_manager";
			role.setRoleId(7);
		}

		else if (partnerType.equalsIgnoreCase("customer")) {
			role.setUserRole("customer_manager");
			userRole="customer_manager";
			role.setRoleId(4);
		}

		userRoleRepo.save(role);
		return userRole;
	}
	
	
	
	

	public List<PartnerDetail> findByPartnerId(String partnerId) {
		return partnerDetailRepo.findByPartnerId(partnerId);
	}

	public String generatePartnerId(String type) {
		String sql = "select einvoicing.generate_partner_id(?) ";
		return jdbcTemplate.queryForObject(sql, new Object[] { type }, String.class);
	}

	public String getUserId() {
		String sql = "select einvoicing.generate_user_id()";
		String count = jdbcTemplate.queryForObject(sql, new Object[] {}, String.class);
		return count;
	}

	/**
	 * validategstn - returns true if the gstn as parameter is already exists in the
	 * contacts
	 * 
	 * @param email
	 * @return
	 */
	public int validategstn(String gstn) {
		try {
			String theQuery = "select count(*) from einvoicing.company_gstin_details cgd where cgd.gstin =?";
			return jdbcTemplate.queryForObject(theQuery, new Object[] { gstn }, Integer.class);
		} catch (Exception e) {
			System.err.println(e);
		}
		return 0;
	}

	/**
	 * validatePrimaryPhone - returns true if the primaryPhone as parameter is
	 * already exists in the contacts
	 * 
	 * @param email
	 * @return
	 */
	public int validatePrimaryPhone(String primaryPhone) {
		try {
			String theQuery = "select count(*) from einvoicing.contact_details cgd where cgd.primary_phone =?";
			return jdbcTemplate.queryForObject(theQuery, new Object[] { primaryPhone }, Integer.class);
		} catch (Exception e) {
			System.err.println(e);
		}
		return 0;
	}

	/**
	 * validateEmail - returns true if the email as parameter is already exists in
	 * the contacts
	 * 
	 * @param email
	 * @return
	 */
	public int validateEmail(String email) {
		try {
			String theQuery = "select count(*) from einvoicing.contact_details cgd where cgd.email =?";
			return jdbcTemplate.queryForObject(theQuery, new Object[] { email }, Integer.class);
		} catch (Exception e) {
			System.err.println(e);
		}
		return 0;
	}

}
