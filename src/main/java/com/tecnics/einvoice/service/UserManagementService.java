package com.tecnics.einvoice.service;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.PartnerUserRolesRepo;
import com.tecnics.einvoice.Repo.UserAuthorizationKeyRepo;
import com.tecnics.einvoice.Repo.UserManagementRepo;
import com.tecnics.einvoice.Repo.UserRolesRepo;
import com.tecnics.einvoice.Repo.UserManagementRepo.RoleMappingResults;
import com.tecnics.einvoice.Repo.UserManagementRepo.UnassginedRoleMappingResults;
import com.tecnics.einvoice.Repo.UserManagementRepo.UserDetails;
import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.entity.OnboardingRegistrationDetails;
import com.tecnics.einvoice.entity.PartnerUserRoles;
import com.tecnics.einvoice.entity.UserAuthorizationKey;
import com.tecnics.einvoice.entity.UserManagement;
import com.tecnics.einvoice.entity.UserRoles;
import com.tecnics.einvoice.mappers.UserMapper;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.SignupResponse;
import com.tecnics.einvoice.model.User;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.HashingUtil;

@Component
public class UserManagementService {

	@Autowired
	UserManagementRepo userManagementRepo;
	
	@Autowired
	PartnerUserRolesRepo partnerUserRolesRepo;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private HashingUtil hashingUtil;
	
	@Autowired
	UserAuthorizationKeyRepo userAuthorizationKeyRepo;

	@Autowired
	UserRolesRepo userRoleRepo;

	public List<UserManagement> findAll() {
		return (List<UserManagement>) userManagementRepo.findAll();
	}
	
	public List<UserManagement> findByPartnerId(String partnerId)
	{
		return (List<UserManagement>) userManagementRepo.findByPartnerId(partnerId);
	}
	
	public ResponseEntity<ResponseMessage> getUserListForForward(String partnerId,String userId)
	{
		try {
		return ResponseEntity.ok().body(new ResponseMessage(userManagementRepo.getUserListForForward(partnerId, userId)));
		}
		catch (Exception e) {
			System.out.println(e );
			e.printStackTrace();
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("GET USER List for FORWARD ERROR in getUserListForForward","GET USER List for FORWARD ERROR , Please contact support team",e.getMessage())));
			
		}
		
	}
	
	public ResponseEntity<ResponseMessage> getUserListByPartner(UserLoginDetails adminUserObj)
	{
		try {
		return ResponseEntity.ok().body(new ResponseMessage(userManagementRepo.getUserListByPartner(adminUserObj.getPartnerId())));
		}
		catch (Exception e) {
			System.out.println(e );
			e.printStackTrace();
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("GET USER List ERROR in getUserListByPartner","GET USER List  ERROR , Please contact support team",e.getMessage())));
			
		}
		
	}
	
	
	
	public ResponseEntity<ResponseMessage> getUserDetails(String userId) {
		UserDetails user = null;
		try {
			 HashMap<String, Object> results = new HashMap<String, Object>();
			   
			
			 List<UserDetails> users =  userManagementRepo.getUserDetails(userId);
			if (users != null && users.size() > 0)
			{
				user = users.get(0);				
				// fetch Partner_Roles				
				List<RoleMappingResults> roleMappingResults=userManagementRepo.fetchPartnerAssignedRolesByUserId(userId);
				 //end of Partner_Roles code		
				results.put("userdetails", user);
				results.put("partnerroles", roleMappingResults);
				
			}
			return ResponseEntity.ok().body(new ResponseMessage(results));
		}	
		catch (Exception e) {
		System.out.println(e );
		e.printStackTrace();
		return ResponseEntity.ok().body(new ResponseMessage(new APIError("GET USER DETAILS ERROR in getUserDetails","GET USER DETAILS ERROR , Please contact support team",e.getMessage())));
		
	}
		
	}
	
	public ResponseEntity<ResponseMessage> getUnassignedRoles( UserLoginDetails adminUserObj, String userId) {
		List<UnassginedRoleMappingResults> results = null;
		try {
			results =  userManagementRepo.fetchPartnerUnassignedRolesByUserId(adminUserObj.getPartnerId(), userId);

			return ResponseEntity.ok().body(new ResponseMessage(results));
		}	
		catch (Exception e) {
		System.out.println(e );
		e.printStackTrace();
		return ResponseEntity.ok().body(new ResponseMessage(new APIError("GET UN ASSIGNED ROLES ERROR in getUnassignedRoles","GET UNASSGINED ROLES ERROR , Please contact support team",e.getMessage())));
		
	}
		
	}

	public ResponseEntity<ResponseMessage>  mapusertorole(PartnerUserRoles partnerUserRole)
	{
		try
		{		
		return ResponseEntity.ok().body(new ResponseMessage(partnerUserRolesRepo.save(partnerUserRole)));
	}	
	catch (Exception e) {
	System.out.println(e );
	e.printStackTrace();
	return ResponseEntity.ok().body(new ResponseMessage(new APIError("MAP USER ROLE ERROR in mapusertorole","MAP USER ROLE ERROR , Please contact support team",e.getMessage())));
	
}
		
	}
	
	@Transactional
	public ResponseEntity<ResponseMessage>  unmapusertorole(PartnerUserRoles partnerUserRole)
	{
		try
		{
			long noOfRolesUnMapped = partnerUserRolesRepo.deleteByUserIdAndRoleId(partnerUserRole.getUserId(), partnerUserRole.getRoleId());
			return ResponseEntity.ok().body(new ResponseMessage(noOfRolesUnMapped));
	}	
	catch (Exception e) {
	System.out.println(e );
	e.printStackTrace();
	return ResponseEntity.ok().body(new ResponseMessage(new APIError("MAP USER ROLE ERROR in mapusertorole","MAP USER ROLE ERROR , Please contact support team",e.getMessage())));
	
}
		
	}



	public UserManagement save(UserManagement obj) {
		return userManagementRepo.save(obj);
	}
	
	public ResponseEntity<ResponseMessage> createUser(User obj, UserLoginDetails adminUserObj) {
		//return userManagementRepo.save(obj);
		
		try {
			return addUser(adminUserObj.getPartnerId(),obj, adminUserObj.getPartnerType());			
		}	
		catch (Exception e) {
		System.out.println(e );
		e.printStackTrace();
		return ResponseEntity.ok().body(new ResponseMessage(new APIError("USER CREATION ERROR","USER CREATION ERROR , Please contact support team",e.getMessage())));
		
	}

	}

	public void delete(String id) {
		 userManagementRepo.deleteById(id);
		
	}
	
	
	/**
	 * createUser
	 * 
	 * @param partnerID
	 * @param obj
	 */
	public ResponseEntity<ResponseMessage> addUser(String partnerID, User obj, String partnerType) {
		
		System.out.println("createUser in the UserManagement Service");
		try {
			String userid= generateUserId();
			UserManagement user = new UserManagement();
			user.setPartnerId(partnerID);			
			user.setUserId(userid);
			
			String username =obj.getUserAlias();			
			user.setFirstName(obj.getFirstName());
			user.setLastName(obj.getLastName());		
			
			user.setEmail(obj.getEmail());
			user.setPrimaryPhoneNumber(obj.getMobileNumber());
			
			user.setUser_alias(obj.getUserAlias());	
			user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			user = userManagementRepo.save(user);
			String password = "P@ssw0rd";
			userAuthorization(user,password);
			String userRole = UserRoleCreation(user, partnerType, obj.getRoles().get(0));
			
			return ResponseEntity.ok().body(new ResponseMessage(user));
			}	
			catch (Exception e) {
			System.out.println(e );
			e.printStackTrace();
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("USER CREATION ERROR in addUser","USER CREATION ERROR , Please contact support team",e.getMessage())));
			
		}
	

	}
	
	private void userAuthorization(UserManagement user, String password) {
		UserAuthorizationKey auth = new UserAuthorizationKey();
		auth.setUserManagement(user);
		auth.setAuthorizationKey(hashingUtil.encryptKey(password));
		userAuthorizationKeyRepo.save(auth);
	}

	
	private String UserRoleCreation(UserManagement user, String partnerType, String roleName) {

		UserRoles role = new UserRoles();
		String userRole ="";

		role.setUserId(user.getUserId());
		if (partnerType.equalsIgnoreCase("vendor") && (roleName.equalsIgnoreCase("vendor_manager") || roleName.equalsIgnoreCase("vendor_admin") )) {
			role.setUserRole("vendor_manager");
			userRole = "vendor_manager";
			role.setRoleId(7);
		}
		
		else if (partnerType.equalsIgnoreCase("vendor") && (roleName.equalsIgnoreCase("vendor_user"))) {
			role.setUserRole("vendor_user");
			userRole = "vendor_user";
			role.setRoleId(9);
		}
		
		else if (partnerType.equalsIgnoreCase("vendor") && (roleName.equalsIgnoreCase("vendor_admin"))) {
			role.setUserRole("vendor_admin");
			userRole = "vendor_admin";
			role.setRoleId(8);
		}

		else if (partnerType.equalsIgnoreCase("customer") && (roleName.equalsIgnoreCase("customer_manager") || roleName.equalsIgnoreCase("customer_admin") )) {
			role.setUserRole("customer_manager");
			userRole="customer_manager";
			role.setRoleId(6);
		}
		
		else if (partnerType.equalsIgnoreCase("customer") && (roleName.equalsIgnoreCase("customer_user") )) {
			role.setUserRole("customer_user");
			userRole="customer_user";
			role.setRoleId(4);
		}
		
		else if (partnerType.equalsIgnoreCase("customer") && (roleName.equalsIgnoreCase("customer_admin") )) {
			role.setUserRole("customer_admin");
			userRole="customer_admin";
			role.setRoleId(5);
		}
		
		// businesspartner
		
		else if (partnerType.equalsIgnoreCase("businesspartner") && (roleName.equalsIgnoreCase("businesspartner_manager") || roleName.equalsIgnoreCase("businesspartner_admin") )) {
			role.setUserRole("businesspartner_manager");
			userRole="businesspartner_manager";
			role.setRoleId(10);
		}
		
		else if (partnerType.equalsIgnoreCase("businesspartner") && (roleName.equalsIgnoreCase("businesspartner_user") )) {
			role.setUserRole("businesspartner_user");
			userRole="businesspartner_user";
			role.setRoleId(1);
		}
		
		else if (partnerType.equalsIgnoreCase("businesspartner") && (roleName.equalsIgnoreCase("businesspartner_admin") )) {
			role.setUserRole("businesspartner_admin");
			userRole="businesspartner_admin";
			role.setRoleId(2);
		}

		userRoleRepo.save(role);
		return userRole;
	}
	
	public String generateUserId() {
		String sql = "select einvoicing.generate_user_id()";
		String count = jdbcTemplate.queryForObject(sql, new Object[] {}, String.class);
		return count;
	}
	
	/**
	 * validatePrimaryPhone - returns true if the primaryPhone as parameter is
	 * already exists in the contacts
	 * 
	 * @param email
	 * @return
	 */
	public int validateUserAlias(String userAlias) throws Exception {
		try {
			String theQuery = "select count(0) from einvoicing.user_management um where um.user_alias =?";
			return jdbcTemplate.queryForObject(theQuery, new Object[] { userAlias }, Integer.class);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
		
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
