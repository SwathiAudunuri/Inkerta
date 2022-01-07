/*
 * Licensed Materials - Property of Tecnics Integration Technologies Pvt Ltd.
 *   (C) Copyright Tecnics Corp. 2021
 */
package com.tecnics.einvoice.util;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.config.ConfigFactory;
import com.tecnics.einvoice.dao.UserDAO;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.Roles;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.User;
import com.tecnics.einvoice.model.UserLoginDetails;

@Component
public class APIUtil {
	private static final Logger logger = LoggerFactory.getLogger(APIUtil.class);

	@Autowired
	JWTUtil jwtutil;

	@Autowired
	UserDAO userdao;
	
	@Autowired
	ErrorLogRepo errorLogRepo;

	public ResponseEntity<ResponseMessage> handleLogon(String loginid, String encryptKey) {
		
		
		String userId = getLoggedinUserId(loginid);
		
		if(userId==null)
			return ResponseEntity.ok().body(
					new ResponseMessage(new APIError(Ex.LOGIN_FAIL.getKey(),							
							Ex.formatMessage(Ex.LOGIN_FAIL.getKeyMessage(), loginid))));
			
		
		UserLoginDetails userDetails = new UserLoginDetails();

		boolean isAuthorized = authenticateUser(userId, encryptKey);
		if (!isAuthorized) {
			logger.error(Ex.formatMessage(Ex.LOGIN_FAIL.getKeyMessage(), userId));
			errorLogRepo.save( new ErrorLog(Ex.LOGIN_FAIL.getKey(),"",Ex.LOGIN_FAIL.getKeyMessage(),"LOGIN","","Backend",userId));
			return ResponseEntity.ok().body(
					new ResponseMessage(new APIError(Ex.LOGIN_FAIL.getKey(),							
							Ex.formatMessage(Ex.LOGIN_FAIL.getKeyMessage(), loginid))));
			}
		
		User user = userdao.getUser(userId);
		System.out.println("is Partner Active" + user.isPartnerActive() + " : " + user.getCompanyName() + " " + user.getPartnerId());
		if (!user.isPartnerActive()) {
			logger.error(Ex.formatMessage(Ex.PARTNER_INACTIVE.getKeyMessage(), user.getCompanyName()));
			errorLogRepo.save( new ErrorLog(Ex.PARTNER_INACTIVE.getKey(),"",Ex.PARTNER_INACTIVE.getKeyMessage(),"LOGIN","","Backend",userId));
			return ResponseEntity.ok().body(
					new ResponseMessage(new APIError(Ex.PARTNER_INACTIVE.getKey(),							
							Ex.formatMessage(Ex.PARTNER_INACTIVE.getKeyMessage(), user.getCompanyName()))));
			}
		
	
		userDetails.setUserId(userId);
		userDetails.setUserAlias(user.getUserAlias());
		userDetails.setFirstName(user.getFirstName());
		userDetails.setLastName(user.getLastName());
		userDetails.setPartnerName(user.getPartnerName());
		userDetails.setRoles(user.getRoles());
		userDetails.setPartnerRoles(user.getPartnerRoles());
		userDetails.setEmail(user.getEmail());
		userDetails.setPartnerId(user.getPartnerId());
		userDetails.setPartnerType(user.getPartnerType());
	
		System.out.println("Partner Type ===" + user.getPartnerType());
		System.out.println("email ===" + userDetails.getEmail() + " " + userDetails.getFirstName());
		
		String jwtToken = jwtutil.getJWTToken(userId,userDetails);
		String refreshToken = jwtutil.getRefreshJWTToken(userId,userDetails);
		
		user.setSecurityToken(jwtToken);
		user.setRefreshToken(refreshToken);

		userDetails.setSecurityToken(jwtToken);
		
		userdao.logUserSession(user);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(userDetails));

	}

	/**
	 * authenticateUser
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	private boolean authenticateUser(String userId, String encryptKey) {
		boolean authenticated = false;
		try {
			authenticated = userdao.authenticateUser(userId, encryptKey);
		} catch (Exception e) {
			throw e;
		}
		return authenticated;
	}
	
	
	/**
	 * getLoggedinUserId returns the user id with user info in username
	 * @param username - can be alias/primary phone/ email /userid
	 * @return userid from usermanagement table
	 */
	
	private String getLoggedinUserId(String username) {

		String loggeduserid = null;

		try {
			loggeduserid = userdao.getUserIdDetails(username);
		} catch (Exception e) {
			throw e;
		}
		return loggeduserid;

	}

	/**
	 * refreshToken
	 * 
	 * @param securityToken
	 * @return
	 */
	public String refreshToken(String securityToken) {
            // check the security token existence in db
           String refreshToken =  userdao.findToken(securityToken);
           String _securityToken = null;
           // validity refresh token
        
           String userName = jwtutil.getUserIdFromToken(refreshToken);
           boolean isAuthorized = jwtutil.verifyJWTToken(refreshToken,userName);
           if(isAuthorized) {
              
               
               //new code for userDetails
               UserLoginDetails userDetails = new UserLoginDetails();
              String userId = userdao.getUserIdDetails(userName);  
          	User user = userdao.getUser(userId);
           	userDetails.setUserId(userId);
    		userDetails.setPartnerName(user.getPartnerName());
    		userDetails.setRoles(user.getRoles());
    		userDetails.setEmail(user.getEmail());
    		userDetails.setPartnerId(user.getPartnerId());
               
               //end of userDetails
    		 // create new token
    			refreshToken = jwtutil.getRefreshJWTToken(userName,userDetails);
               _securityToken = jwtutil.getJWTToken(userName,userDetails); // update refresh token of user
               userdao.updateToken(_securityToken,refreshToken);
    
           	}
           
           return _securityToken;
	}

	   /**
     * This method fetch all the navigation role items
     * @param roles - list of string role items
     * @return
     */
    public JSONArray getNavigationItems( Roles roles) {
        String methodName = "getNavigationItems";
        JSONArray items = null;
        try {
            items = new JSONArray();   
            for (int i = 0; i < roles.getRoles().size(); i++) {
                JSONArray navItem =  new ConfigFactory().getNavigationObject(roles.getRoles().get(i));
                if(navItem  != null)
                    items.putAll(navItem);
			}
            
        } catch (Exception e) {
            throw e;
        }
        return items;
    }
	
}
