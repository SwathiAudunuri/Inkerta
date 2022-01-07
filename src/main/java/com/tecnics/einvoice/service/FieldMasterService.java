package com.tecnics.einvoice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.FieldMasterRepo;
import com.tecnics.einvoice.dao.FieldMasterValuesDAO;
import com.tecnics.einvoice.entity.FieldMaster;

@Component
public class FieldMasterService {

	@Autowired
	FieldMasterRepo fieldMasterRepo;
	
	 @Autowired
	FieldMasterValuesDAO fieldMasterValuesDAO; 

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
	
	public HashMap<String, ArrayList<String>> findByModuleNameAndModuleKey(String moduleName,String moduleKey)
	{
		HashMap<String, ArrayList<String>> fieldValues = new HashMap<String, ArrayList<String>>();
		try
		{
		
			LinkedHashMap<String,String> fieldMasterValues=fieldMasterValuesDAO.getFieldMasterValues(moduleName, moduleKey);
		
			Iterator<Map.Entry<String, String>> fieldMasterValuesIterator = fieldMasterValues.entrySet().iterator();
			
            String field_key="";			
	        while (fieldMasterValuesIterator.hasNext()) {
	            Map.Entry<String, String> fieldValue = fieldMasterValuesIterator.next();
	            System.out.println(fieldValue.getValue() + " => " + fieldValue.getKey());
	            field_key=fieldValue.getKey().substring(0, fieldValue.getKey().lastIndexOf("#"));
	            ArrayList<String> fieldValue_list = fieldValues.get(field_key);
				 if(fieldValue_list==null)
					 fieldValue_list = new ArrayList<String>();				
				     
					 fieldValue_list.add(fieldValue.getValue());
				 
				 fieldValues.put(field_key, fieldValue_list);
	            
	            
	        }
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return fieldValues;
	} 
	
	public HashMap<String, ArrayList<String>> findByModuleName(String moduleName)
	{
		HashMap<String, ArrayList<String>> fieldValues = new HashMap<String, ArrayList<String>>();
		try
		{
		
			LinkedHashMap<String,String> fieldMasterValues=fieldMasterValuesDAO.getFieldMasterValuesByModuleName(moduleName);
		
			Iterator<Map.Entry<String, String>> fieldMasterValuesIterator = fieldMasterValues.entrySet().iterator();
			
            String field_key="";			
	        while (fieldMasterValuesIterator.hasNext()) {
	            Map.Entry<String, String> fieldValue = fieldMasterValuesIterator.next();
	            System.out.println(fieldValue.getValue() + " => " + fieldValue.getKey());
	            field_key=fieldValue.getKey().substring(0, fieldValue.getKey().lastIndexOf("#"));
	            ArrayList<String> fieldValue_list = fieldValues.get(field_key);
				 if(fieldValue_list==null)
					 fieldValue_list = new ArrayList<String>();				
				     
					 fieldValue_list.add(fieldValue.getValue());
				 
				 fieldValues.put(field_key, fieldValue_list);
	            
	            
	        }
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return fieldValues;
	} 


	
}
