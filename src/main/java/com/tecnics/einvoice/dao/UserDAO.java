package com.tecnics.einvoice.dao;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.mappers.UserMapper;
import com.tecnics.einvoice.model.User;

@Component
public class UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public String getUserIdDetails(String loginuserId) {
		
		try
		{
		String userID = jdbcTemplate.queryForObject(SQLQueries.GET_USERID, new Object[] { loginuserId, loginuserId },String.class);
		System.err.println("whats user id? "+userID);
		return userID;
		}
		catch(EmptyResultDataAccessException erda)
		{
			return null;
		}
	}

	public boolean authenticateUser(String userId, String encryptkey) {
		
		boolean isvalid = false;

		int count = jdbcTemplate.queryForObject(SQLQueries.LOGIN_QUERY, new Object[] { userId, encryptkey },
				Integer.class);
		if (count > 0) {
			isvalid = true;
		}
		return isvalid;

	}

	public User getUser(String userId) {
		User user = null;
		try {
			//List<User> users = (List<User>) jdbcTemplate.query(SQLQueries.GET_USER_DETAILS, new String[] { userId },
			//		new BeanPropertyRowMapper<User>(User.class));
			
			
			 List<User> users =  jdbcTemplate.query(SQLQueries.GET_USER_DETAILS, new String[]{userId},new UserMapper());
			if (users != null && users.size() > 0)
			{
				user = users.get(0);
				
				// writing below code for Partner_Roles
				String qry_partner_user_roles="select pur.user_id,STRING_AGG(pr.role_name, ',' ORDER BY pr.role_name) as partnerUserRoles "
						+ "from einvoicing.partner_user_roles pur "
						+ "inner join einvoicing.partner_roles pr on pur.role_id = pr.role_id  "
						+ "where pur.user_id =? group by pur.user_id ";
				//List<Object> partner_user_Roles = jdbcTemplate.query(qry_partner_user_roles, new String[]{user.getUserId()},new String[])
				List<String> partnerUserRoles=new ArrayList<String>();
				 List<Map<String, Object>> partner_user_Roles = jdbcTemplate.queryForList(qry_partner_user_roles, new Object[]{user.getUserId()}, new int[]{Types.VARCHAR});
				 if (partner_user_Roles != null && partner_user_Roles.size() > 0)
				 {
					 partnerUserRoles= Arrays.asList(((String)partner_user_Roles.get(0).get("partnerUserRoles")).split(","));
					 
				 }
				 user.setPartnerRoles(partnerUserRoles);
				 
				 //end of Partner_Roles code
					 
				   
				
				
				
			}
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

	public void logUserSession(User user) {
		String methodName = "logUserSession";		
		jdbcTemplate.update(SQLQueries.LOG_USER_SESSION, new Object[] { user.getUserId(), user.getSecurityToken(),
				user.getRefreshToken(), "", new Timestamp(new Date().getTime()) });
	}

	public String findToken(String securityToken) {
		String refreshToken = null;

		List<Object> tokens = jdbcTemplate.query(SQLQueries.GET_REFRESH_TOKEN, new String[] { securityToken },
				new BeanPropertyRowMapper<Object>(Object.class));

		if (tokens != null && tokens.size() > 0) // tokens should not be null && must be at least one token should come
													// out in output
			refreshToken = (String) tokens.get(0);
		return refreshToken;

	}

	public void updateToken(String securityToken, String refreshToken) {
		jdbcTemplate.update(SQLQueries.UPDATE_REFRESH_TOKEN,
				new Object[] { securityToken, refreshToken, new Timestamp(new Date().getTime()) });
	}
}
