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
	        user.setEmail(resultSet.getString(2));
	        user.setLocation(resultSet.getString(3));
	        List<String> roles= Arrays.asList(resultSet.getString(4).split(","));
	        user.setRoles(roles);
	        user.setFirstName(resultSet.getString(5));
	        user.setLastName(resultSet.getString(6));
	        user.setPartnerName(resultSet.getString(7));
	        user.setPartnerId(resultSet.getString(8));
	        user.setPartnerType(resultSet.getString(9));        
	        return user;
	    }
	}

