/**
 * 
 */
package com.tecnics.einvoice.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tecnics.einvoice.constants.SQLQueries;

public class BaseServiceImpl {

	
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	/**
	 * @param userid
	 * @return
	 */
	protected String getPartnerIdfromUser(String userid) {
		return  jdbcTemplate.queryForObject(SQLQueries.GET_PARTNERID_PARTNERDETAILS, new Object[] { userid },
				String.class);
	}
	
	/**
	 * @param userid
	 * @return
	 */
	protected String getRecipientIdByPartnerId(String partnerID) {
		return  jdbcTemplate.queryForObject(SQLQueries.GET_RECIPIENTID_BY_PARTNERID, new Object[] { partnerID },
				String.class);
	}
	
	
	/**
	 * @param userid
	 * @return
	 */
	protected Map<Object, Object> findAllVendorsList() {
		 List<Map<String, Object>> mapList = jdbcTemplate.queryForList(SQLQueries.GET_ALL_VENDORS);
		    return mapList.stream().collect(Collectors.toMap(k -> (String) k.get("partner_id"), k -> (String) k.get("company_name")));		
		
	}
	

	/**
	 * @return
	 */
	protected Map<Object, Object> findVendorsByNameList(String companyName) {
		 List<Map<String, Object>> mapList = jdbcTemplate.queryForList(SQLQueries.GET_VENDORS_LIKE_NAME,new Object[] {companyName+'%'});
		    return mapList.stream().collect(Collectors.toMap(k -> (String) k.get("partner_id"), k -> (String) k.get("company_name")));		
		
	}
	
}
	
