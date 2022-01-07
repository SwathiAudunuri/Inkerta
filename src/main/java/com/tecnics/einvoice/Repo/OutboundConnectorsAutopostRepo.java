package com.tecnics.einvoice.Repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.OutboundConnectorsAutopost;



public interface OutboundConnectorsAutopostRepo extends CrudRepository<OutboundConnectorsAutopost, Integer>{
	
	OutboundConnectorsAutopost findByConnectorId(Integer connectorId);


}



