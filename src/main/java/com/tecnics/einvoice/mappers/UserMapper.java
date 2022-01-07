package com.tecnics.einvoice.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.tecnics.einvoice.model.User;



	public class UserMapper implements RowMapper {

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
	        User user = new User();
	        user.setUserId(resultSet.getString(1));
	        user.setUserAlias(resultSet.getString(2));
	        user.setEmail(resultSet.getString(3));
	        user.setLocation(resultSet.getString(4));
	        List<String> roles= Arrays.asList(resultSet.getString(5).split(","));
	        user.setRoles(roles);
	        user.setFirstName(resultSet.getString(6));
	        user.setLastName(resultSet.getString(7));
	        user.setPartnerName(resultSet.getString(8));
	        user.setCompanyName(resultSet.getString(8));
	        user.setPartnerId(resultSet.getString(9));
	        user.setPartnerType(resultSet.getString(10)); 
	        user.setPartnerActive(resultSet.getBoolean(11));
	        return user;
	    }
	}

