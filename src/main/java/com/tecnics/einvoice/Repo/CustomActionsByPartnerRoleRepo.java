package com.tecnics.einvoice.Repo;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import com.tecnics.einvoice.entity.CustomActionsByPartnerRole;
import com.tecnics.einvoice.constants.SQLQueries;

public interface CustomActionsByPartnerRoleRepo extends CrudRepository<CustomActionsByPartnerRole, Integer> {
	List<CustomActionsByPartnerRole> findByRoleName(String roleName);
	List<CustomActionsByPartnerRole> findByActionId(Integer actionId);

}
