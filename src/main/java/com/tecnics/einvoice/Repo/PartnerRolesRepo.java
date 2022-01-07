package com.tecnics.einvoice.Repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.PartnerRoles;

public interface PartnerRolesRepo extends CrudRepository<PartnerRoles, Integer>{
	
List<PartnerRoles> findByPartnerId(String partnerId);
Optional<PartnerRoles> findByRoleId(Integer roleId);
Optional<PartnerRoles> findByPartnerIdAndRoleName(String partnerId,String roleName);
}
