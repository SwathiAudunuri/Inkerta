package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.FieldMaster;

public interface FieldMasterRepo extends CrudRepository<FieldMaster, Integer> {

	List<FieldMaster> findByFieldName(String fieldName);

//	@Query("SELECT u FROM FieldMaster u WHERE u.fieldName=:fieldName and u.moduleName=:moduleName")
	List<FieldMaster> findByFieldNameAndModuleName(String fieldName, String moduleName);

}
