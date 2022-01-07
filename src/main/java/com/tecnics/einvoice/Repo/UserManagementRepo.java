package com.tecnics.einvoice.Repo;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.tecnics.einvoice.constants.SQLQueries;

import com.tecnics.einvoice.entity.UserManagement;


public interface UserManagementRepo extends CrudRepository<UserManagement, String>{

	UserManagement findByUserId(String user_id);
	
	List<UserManagement> findByPartnerId(String partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.GET_USER_INFO)
	List<UserDetails> getUserDetails(String userId);
	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_PARTNER_ASSIGNED_ROLES_BY_USERID)
	List<RoleMappingResults> fetchPartnerAssignedRolesByUserId(String userId);

	public interface UserDetails {
		String getUser_id();
		String getUser_alias();
		String getEmail();
		String getLocation();
		String getRoles();
		String getFirst_name();
		String getLast_name();
		String getCompany_name();
		String getPartner_id();
		String getPartner_type();

	}
	
	public interface RoleMappingResults {
		Integer getId();
		String getUser_id();
		String getRole_id();
		String getRole_name();
		String getRole_description();
		Timestamp getCreated_date();
		String getCreated_by();
	}
	
	// pr.role_id,pr.role_name,pr.role_description
	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_PARTNER_UNASSIGNED_ROLES_FOR_USERID)
	List<UnassginedRoleMappingResults> fetchPartnerUnassignedRolesByUserId(String partnerId,String userId);
	
	public interface UnassginedRoleMappingResults {
		
		String getRole_id();
		String getRole_name();
		String getRole_description();
	
	}
	
	@Query(nativeQuery = true, value=SQLQueries.GET_USERS_FOR_FORWARD)
	List<UserListForForward> getUserListForForward(String partnerId,String userId);
	
	
public interface UserListForForward {
		
		String getUser_id();
		String getDisplayname();
		String getRoles();
		String getPartner_id();
	
	}

@Query(nativeQuery = true, value=SQLQueries.GET_USERS_BY_PARTNER)
List<UserList> getUserListByPartner(String partnerId);

public interface UserList {
	
	String getUserid();
	String getDisplayname();
	String getUseralias();
	Boolean getIs_locked();
	String getRoles();
	String getPartner_id();
	String getEmail();

}


}
