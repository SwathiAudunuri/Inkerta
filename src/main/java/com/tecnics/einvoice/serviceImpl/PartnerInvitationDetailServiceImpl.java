package com.tecnics.einvoice.serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class PartnerInvitationDetailServiceImpl {

	@Autowired
	JdbcTemplate jdbcTemplate;
/**
 * Consuming SP - sp_generate_url -  einvoicing schema
 * @param partnerType
 * @param invSentOn
 * @param invSentBy
 * @param partnerCompanyName
 * @param partnerContactPersonName
 * @param partnerContactMobileNo
 * @param partnerContactEmail
 * @param partnerFirmType
 * @param invDescription
 * @param invReqRaisedBy
 * @param invReqRaisedOn
 * @return
 */
	public String generateURL( String partnerType, String invSentBy, String partnerCompanyName,
			String partnerContactPersonName, Long partnerContactMobileNo, String partnerContactEmail,
			String partnerFirmType, String invDescription, String invReqRaisedBy,String status) {

		String caseID = null;
		try {
			Connection connection = null;
			connection = jdbcTemplate.getDataSource().getConnection();
			java.sql.CallableStatement cstmt;
			cstmt = connection.prepareCall("CALL einvoicing.sp_generate_url(?,?,?,?,?,?,?,?,?,?,'','')");
			cstmt.setString(1, partnerType);
			//cstmt.setTimestamp(2, invSentOn);
			cstmt.setString(2, invSentBy);
			cstmt.setString(3, partnerCompanyName);
			cstmt.setString(4, partnerContactPersonName);
			cstmt.setLong(5,partnerContactMobileNo);
			cstmt.setString(6, partnerContactEmail);
			cstmt.setString(7, partnerFirmType);
			cstmt.setString(8, invDescription);
			cstmt.setString(9, invReqRaisedBy);
			cstmt.setString(10, status);
			//cstmt.setTimestamp(11, invReqRaisedOn);
			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.registerOutParameter(2, Types.VARCHAR);

			cstmt.executeUpdate();
			
			caseID = cstmt.getString(1);

			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.err.println(caseID);
		return caseID;
	}
	

	
	
/**
 * test method consuming a sample Stored Procedure 
 * @return
 */
	public String test() {
		String test = null;
		Connection connection = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			java.sql.CallableStatement cstmt;
			cstmt = connection.prepareCall("CALL einvoicing.RND_TEST(?,'')");
			cstmt.setString(1, "Hi");
			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.executeUpdate();

			cstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return test;
	}

	
}
