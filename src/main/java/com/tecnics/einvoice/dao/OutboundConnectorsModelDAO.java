package com.tecnics.einvoice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.model.OutboundConnectorsModel;

@Component
public class OutboundConnectorsModelDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//SELECT 1 connector_id, 2 partner_id, 3 description, 4 connector_tag, 5 is_active, 6 created_on, 7 created_by, 8 invoiceupload_deliverymode, 9 invoicequery_deliverymode, 10 invoicestatusupdate_deliverymode, 11 autopost_on_verification FROM einvoicing.outbound_connectors where partnerid=? ";
	
	
	 //Can use directly a RowMapper implementation class without an object creation
	  public List<OutboundConnectorsModel> fetchConnectors(String partner_id){
	    return jdbcTemplate.query(SQLQueries.GET_OUTBOUND_CONNECTORS_BY_PARTNERID,new String[] {partner_id} ,new RowMapper<OutboundConnectorsModel>(){  
	      @Override  
	      public OutboundConnectorsModel mapRow(ResultSet rs, int rownumber) throws  SQLException    { 
	    	  OutboundConnectorsModel ocm=new OutboundConnectorsModel();
	    	  ocm.setConnectorId(rs.getInt(1));
	    	  ocm.setPartnerId(rs.getString(2));
	    	  ocm.setDescription(rs.getString(3));
	    	  ocm.setConnectorTag(rs.getString(4));
	    	  ocm.setIsActive(rs.getBoolean(5));
	    	  ocm.setCreatedOn(rs.getTimestamp(6));
	    	  ocm.setCreatedBy(rs.getString(7));
	    	  ocm.setInvoiceuploadDeliverymode(rs.getString(8));
	    	  ocm.setInvoicequeryDeliverymode(rs.getString(9));
	    	  ocm.setInvoicestatusupdateDeliverymode(rs.getString(10));
	    	    	            
	            return ocm;  
	          }  
	      });  
	      }
  
	  
	  public OutboundConnectorsModel fetchConnectorByConnectorId(Integer connector_id) {

	  return jdbcTemplate.query(SQLQueries.GET_OUTBOUND_CONNECTOR_BY_CONNECTORID, new Integer[]{connector_id}, rs -> {

          if(rs.next()){
        	  OutboundConnectorsModel ocm=new OutboundConnectorsModel();
	    	  ocm.setConnectorId(rs.getInt(1));
	    	  ocm.setPartnerId(rs.getString(2));
	    	  ocm.setDescription(rs.getString(3));
	    	  ocm.setConnectorTag(rs.getString(4));
	    	  ocm.setIsActive(rs.getBoolean(5));
	    	  ocm.setCreatedOn(rs.getTimestamp(6));
	    	  ocm.setCreatedBy(rs.getString(7));
	    	  ocm.setInvoiceuploadDeliverymode(rs.getString(8));
	    	  ocm.setInvoicequeryDeliverymode(rs.getString(9));
	    	  ocm.setInvoicestatusupdateDeliverymode(rs.getString(10));	    	    	            
	           return ocm;  
          }
          else {
              return null;
          }

  });
	  
	
}
}
