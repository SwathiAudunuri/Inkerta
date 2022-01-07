package com.tecnics.einvoice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.model.InvoiceQueryTreeRow;


@Component
public class FieldMasterValuesDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
	 public LinkedHashMap<String,String> getFieldMasterValues(String module_name,String module_key){
        return jdbcTemplate.query(SQLQueries.GET_FIELD_MASTER_VALUES, new String[] {module_name,module_key},(ResultSet rs) -> {
        	LinkedHashMap<String,String> results = new LinkedHashMap<String,String>();
            while (rs.next()) {
            	
                results.put(rs.getString("field_name")+"#"+rs.getString("field_value"), rs.getString("field_value"));
            }
                        
            return results;
        });
        
        
	}
	 
	 public LinkedHashMap<String,String> getFieldMasterValuesByModuleName(String module_name){
	        return jdbcTemplate.query(SQLQueries.GET_FIELD_MASTER_VALUES_BY_MODULE, new String[] {module_name},(ResultSet rs) -> {
	        	LinkedHashMap<String,String> results = new LinkedHashMap<String,String>();
	            while (rs.next()) {	            	
	                results.put(rs.getString("field_name")+"#"+rs.getString("field_value"), rs.getString("field_value"));
	            }
	                        
	            return results;
	        });
	        
	        
		} 
}
