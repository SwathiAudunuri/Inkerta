package com.tecnics.einvoice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.model.InvoiceQuery;
import com.tecnics.einvoice.model.InvoiceQueryTreeRow;


@Component
public class InvoiceQueryDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	  //Can use directly a RowMapper implementation class without an object creation
	  public List<InvoiceQuery> findInvoiceQueriesByDocumentRefId(String documentRefId){
	    return jdbcTemplate.query(SQLQueries.GET_INVOICE_QUERIES_BY_DOCUMENTREFID,new String[] {documentRefId} ,new RowMapper<InvoiceQuery>(){  
	      @Override  
	      public InvoiceQuery mapRow(ResultSet rs, int rownumber) throws  SQLException    { 
	    	  InvoiceQuery iq=new InvoiceQuery();  
	    	  iq.setId(rs.getInt(1));
	    	  iq.setQueryRefId(rs.getString(2));
	    	  iq.setDocumentRefId(rs.getString(3));
	    	  iq.setQueryType(rs.getString(4));
	    	  iq.setQueryText(rs.getString(5));
	    	  iq.setQueryFrom(rs.getString(6));
	    	  iq.setCreatedBy(rs.getString(7));
	    	  iq.setCreatedByDisplayName(rs.getString(8));
	    	  iq.setCreatedDate(rs.getTimestamp(9));
	    	  iq.setIsDispatched(rs.getBoolean(10));
	    	  iq.setDispatchMode(rs.getString(11));
	    	  iq.setDispatchedOn(rs.getTimestamp(12));
	    	  iq.setParentQueryRefId(rs.getString(13));
	    	            
	            return iq;  
	          }  
	      });  
	      }
	  
	  //Can use directly a RowMapper implementation class without an object creation
	  public List<InvoiceQueryTreeRow> findInvoiceQueriesByDocumentRefIdForTree(String documentRefId){
	    return jdbcTemplate.query(SQLQueries.GET_INVOICE_QUERIES_BY_DOCUMENTREFID,new String[] {documentRefId} ,new RowMapper<InvoiceQueryTreeRow>(){  
	      @Override  
	      public InvoiceQueryTreeRow mapRow(ResultSet rs, int rownumber) throws  SQLException    { 
	    	  InvoiceQueryTreeRow iqtr=new InvoiceQueryTreeRow();  
	    	  iqtr.setId(rs.getInt(1));
	    	  iqtr.setQueryRefId(rs.getString(2));
	    	  iqtr.setDocumentRefId(rs.getString(3));
	    	  iqtr.setQueryType(rs.getString(4));
	    	  iqtr.setQueryText(rs.getString(5));
	    	  iqtr.setQueryFrom(rs.getString(6));
	    	  iqtr.setCreatedBy(rs.getString(7));
	    	  iqtr.setCreatedByDisplayName(rs.getString(8));
	    	  iqtr.setCreatedDate(rs.getTimestamp(9));
	    	  iqtr.setIsDispatched(rs.getBoolean(10));
	    	  iqtr.setDispatchMode(rs.getString(11));
	    	  iqtr.setDispatchedOn(rs.getTimestamp(12));
	    	  iqtr.setParentQueryRefId(rs.getString(13));
	    	  iqtr.setSubject(rs.getString(14));        
	            return iqtr;  
	          }  
	      });  
	      }
	  
	  //Can use directly a RowMapper implementation class without an object creation
	  public  List<Map<String, InvoiceQuery>> findInvoiceQueriesByDocumentRefId1(String documentRefId){
	    return jdbcTemplate.query(SQLQueries.GET_INVOICE_QUERIES_BY_DOCUMENTREFID,new String[] {documentRefId} ,new RowMapper<Map<String,InvoiceQuery>>(){  
	      @Override  
	      public Map<String, InvoiceQuery> mapRow(ResultSet rs, int rownumber) throws  SQLException    { 
	    	  Map<String, InvoiceQuery> map = new HashMap<>(1);
	    	  InvoiceQuery iq=new InvoiceQuery();  
	    	  iq.setId(rs.getInt(1));
	    	  iq.setQueryRefId(rs.getString(2));
	    	  iq.setDocumentRefId(rs.getString(3));
	    	  iq.setQueryType(rs.getString(4));
	    	  iq.setQueryText(rs.getString(5));
	    	  iq.setQueryFrom(rs.getString(6));
	    	  iq.setCreatedBy(rs.getString(7));
	    	  iq.setCreatedByDisplayName(rs.getString(8));
	    	  iq.setCreatedDate(rs.getTimestamp(9));
	    	  iq.setIsDispatched(rs.getBoolean(10));
	    	  iq.setDispatchMode(rs.getString(11));
	    	  iq.setDispatchedOn(rs.getTimestamp(12));
	    	  iq.setParentQueryRefId(rs.getString(13));
	    	  map.put("data", iq);
	    	            
	            return map;  
	          }  
	      });  
	      }
	
}




