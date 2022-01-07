package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.PartnerUserRoles;

public interface PartnerUserRolesRepo  extends CrudRepository<PartnerUserRoles, String> {

	List<PartnerUserRoles> findByUserId(String userId);
	
	long deleteByUserIdAndRoleId(@Param("userId") String userId, @Param("roleId") Integer roleId);
}


