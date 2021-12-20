package com.tecnics.einvoice.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		
		String userID = jdbcTemplate.queryForObject(SQLQueries.GET_USERID, new Object[] { loginuserId, loginuserId,loginuserId,loginuserId },
				String.class);
		System.err.println("whats user id? "+userID);
		return userID;
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
				user = users.get(0);
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
