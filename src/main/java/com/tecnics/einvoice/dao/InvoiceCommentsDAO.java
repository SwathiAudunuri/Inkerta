package com.tecnics.einvoice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.model.InvoiceNotesModel;

//not being used..can be deleted later on
@Component
public class InvoiceCommentsDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// not being used..can be deleted later on
	
//	"select 1 ic.comment_id , 2 ic.partner_id  , 3 ic.document_ref_id , 4 ic.comments, 5 ic.created_by, 6 as createdbydisplayname, 7 ic.created_on"
//	+" from einvoicing.invoice_comments ic "
//	+ "inner join einvoicing.user_management um on ic.created_by = um.user_id  "
//	+ "where ic.document_ref_id = ? and ic.partner_id = ? order by ic.created_on desc";
	
	/*  public List<InvoiceCommentsModel> fetchComments(String document_refId, String partner_id){
		    return jdbcTemplate.query(SQLQueries.FETCH_INV_COMMENTS,new String[] {document_refId,partner_id} ,new RowMapper<InvoiceCommentsModel>(){  
		      @Override  
		      public InvoiceCommentsModel mapRow(ResultSet rs, int rownumber) throws  SQLException    { 
		    	  InvoiceCommentsModel icm=new InvoiceCommentsModel();
		    	  icm.setCommentId(rs.getInt(1));
		    	  icm.setPartnerId(rs.getString(2));
		    	  icm.setDocumentRefId(rs.getString(3));
		    	  icm.setComments(rs.getString(4));
		    	  icm.setCreatedBy(rs.getString(5));
		    	  icm.setCreatedByDisplayName(rs.getString(6));
		    	  icm.setCreatedOn(rs.getTimestamp(7));	    	 
		    	    	            
		            return icm;  
		          }  
		      });  
		      }
		      */
	  

}
