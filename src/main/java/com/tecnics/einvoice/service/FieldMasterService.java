package com.tecnics.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.FieldMasterRepo;
import com.tecnics.einvoice.entity.FieldMaster;

@Component
public class FieldMasterService {

	@Autowired
	FieldMasterRepo fieldMasterRepo;

	public List<FieldMaster> findAll() {
		return (List<FieldMaster>) fieldMasterRepo.findAll();
	}


	public FieldMaster update(int id, FieldMaster obj) {
		obj.setId(id);
		return fieldMasterRepo.save(obj);
	}

	public FieldMaster save(FieldMaster obj) {
		obj.setId(0);
		return fieldMasterRepo.save(obj);
	}

	public void delete(int id) {
		 fieldMasterRepo.deleteById(id);
		
	}

	public List<FieldMaster> findByFieldName(String fieldName) {
		return fieldMasterRepo.findByFieldName(fieldName);
	}


	public List<FieldMaster> findByFieldNameAndModuleName(String fieldName, String moduleName) {
		return fieldMasterRepo.findByFieldNameAndModuleName(fieldName, moduleName);
	}


	
}
