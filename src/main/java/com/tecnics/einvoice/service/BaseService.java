/**
 * 
 */
package com.tecnics.einvoice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.log.BaseLogger;
import com.tecnics.einvoice.serviceImpl.BaseServiceImpl;
import com.tecnics.einvoice.util.JWTUtil;

public class BaseService extends BaseServiceImpl {

	BaseLogger log = BaseLogger.getBaseLogger(BaseService.class);

	@Autowired
	JWTUtil jWTUtil;

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * Method to extract StackTrace as a String
	 */
	public String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	/**
	 * Method inputs the Bearer Token Id and generates user Id from valid token
	 * 
	 * @param token
	 * @return
	 */
	public String getUserName(String token) {
		String username = null;
		try {
			if (token != null && token.indexOf("Bearer") >= 0) { // header and not ignore
				token = token.substring(7);
				username = jWTUtil.getUserIdFromToken(token);
			}
		} catch (Exception e) {
			log.logErrorMessage("could not fetch user from token", e);
			return username;
		}
		return username;
	}

	/**
	 * @param userid
	 * @return
	 */
	public String getPartnerIdfromUserId(String userid) {
		return getPartnerIdfromUser(userid);
	}

	/**
	 * findAllVendorsService
	 * 
	 * @return
	 */
	public Map<Object, Object> findAllVendorsService() {
		return findAllVendorsList();

	}

	/**
	 * isVendor
	 * @param userID
	 * @return
	 */
	public boolean isVendor(String userID) {
		if (getRole(userID) != null && getRole(userID).startsWith("vendor"))
			return true;
		else
			return false;

	}
	
	/**
	 * isVendor
	 * @param userID
	 * @return
	 */
	public boolean canUpload(String userID) {
		String role=getRole(userID);
		if (role!= null && (role.startsWith("vendor") || role.startsWith("businesspartner")))
			return true;
		else
			return false;

	}

	/**
	 * isCustomer
	 * 
	 * @param userID
	 * @return
	 */
	public boolean isCustomer(String userID) {
		if (getRole(userID) != null && getRole(userID).startsWith("customer"))
			return true;
		else
			return false;

	}

	/**
	 * getRole
	 * 
	 * @param userId
	 * @return
	 */
	public String getRole(String userId) {
		return jdbcTemplate.queryForObject(SQLQueries.CHECK_ROLE, new Object[] { userId }, String.class);
	}

	/**
	 * findVendorsByNameService
	 * 
	 * @param companyName
	 * @return
	 */
	public Map<Object, Object> findVendorsByNameService(String companyName) {
		return findVendorsByNameList(companyName);
	}



}
